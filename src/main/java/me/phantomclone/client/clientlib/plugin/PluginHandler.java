package me.phantomclone.client.clientlib.plugin;

import me.phantomclone.client.clientlib.ClientLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class PluginHandler {

    private final ClientLib client;

    private String pluginsFolder;

    private LinkedList<PluginDescription> loadedPlugins;

    public PluginHandler(ClientLib client, String pluginsFolder) {
        this.client = client;
        this.pluginsFolder = pluginsFolder;
        this.loadedPlugins = new LinkedList<>();
    }

    public void loadPlugins() {
        File folder = new File(pluginsFolder);
        if (!folder.exists())
            folder.mkdirs();
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".jar")) {
                try {
                    PluginDescription description = loadPlugin(file);
                    if (description != null) {
                        loadedPlugins.add(description);
                    }
                } catch (PluginLoadException e) {
                    Logger.getLogger(getClass().getName()).info(e.getMessage());
                }
            }
        }
    }

    public PluginDescription loadPlugin(File file) throws PluginLoadException {
        PluginDescription description;
        try {
            JarFile jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("plugin.yml");
            if (entry == null) {
                throw new PluginLoadException("Plugin.yml not found - " + file.getName());
            }
            description = new PluginDescription();
            InputStream inputStream = jar.getInputStream(entry);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("name: ")) {
                    description.setName(line.replace("name: ", ""));
                } else if (line.contains("author: ")) {
                    description.setAuthor(line.replace("author: ", ""));
                }else if (line.contains("main: ")) {
                    description.setMain(line.replace("main: ", ""));
                } else if (line.contains("version: ")) {
                    description.setVersion(line.replace("version: ", ""));
                }
            }
            if (reader != null)
                reader.close();
            if (inputStream != null)
                inputStream.close();
            if (description.getName() == null) {
                throw new PluginLoadException("Description error name: PluginName (" + file.getName() + ")");
            }
            if (description.getAuthor() == null) {
                throw new PluginLoadException("Description error author: PluginAuthor (" + file.getName() + ")");
            }
            if (description.getMain() == null) {
                throw new PluginLoadException("Description error main: PluginMain (" + file.getName() + ")");
            }
            if (description.getVersion() == null) {
                throw new PluginLoadException("Description error version: PluginVersion (" + file.getName() + ")");
            }
            description.setFile(file);

            // TEST LOADER
            Enumeration<JarEntry> e = jar.entries();
            URLClassLoader cl = URLClassLoader.newInstance(new URL[] {description.getFile().toURI().toURL()});
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if(je.isDirectory() || !je.getName().endsWith(".class")){
                    continue;
                }
                // -6 because of .class
                String className = je.getName().substring(0,je.getName().length()-6);
                className = className.replace('/', '.');
                Class c = cl.loadClass(className);
            }
            //
            jar.close();
            //CLOSE JAR

            //URLClassLoader classLoader = new PluginClassLoader(new URL[]{description.getFile().toURI().toURL()});
            //Class<?> main = classLoader.loadClass(description.getMain());
            Class<?> main = cl.loadClass(description.getMain());
            if (!Plugin.class.isAssignableFrom(main)) {
                throw new PluginLoadException("Main extends not from Plugin (" + file.getName() + ")");
            }
            Plugin plugin = (Plugin) main.getDeclaredConstructor().newInstance();
            description.setPlugin(plugin);
            plugin.setClient(client);
            plugin.load();
            if (cl != null)
                cl.close();
        } catch (Exception e) {
            if (e.getClass() == PluginLoadException.class) {
                throw new PluginLoadException(e.getMessage());
            }
            e.printStackTrace();
            throw new PluginLoadException("Error unable to load Plugin (" + file.getName() + ")");
        }
        return description;
    }

    public LinkedList<PluginDescription> getLoadedPlugins() {
        return loadedPlugins;
    }

    public void enablePlugins() {
        this.loadedPlugins.stream().filter(d -> !d.getPlugin().isEnabled()).forEach(pluginDescription -> {
            pluginDescription.getPlugin().setEnabled(true);
            pluginDescription.getPlugin().enable();
        });
    }

    public boolean enablePlugin(String pluginName) {
        PluginDescription plugin = getPluginDescription(pluginName);
        if (plugin == null || plugin.getPlugin().isEnabled())
            return false;
        plugin.getPlugin().setEnabled(true);
        plugin.getPlugin().enable();
        return true;
    }

    public void disablePlugins() {
        this.loadedPlugins.stream().filter(d -> d.getPlugin().isEnabled()).forEach(pluginDescription -> {
            pluginDescription.getPlugin().setEnabled(false);
            this.client.getEventHandler().unregisterListener(pluginDescription.getPlugin());
            this.client.getModuleHandler().unregisterModule(pluginDescription.getPlugin());
            pluginDescription.getPlugin().disable();
        });
    }

    public boolean disablePlugin(String pluginName) {
        PluginDescription plugin = getPluginDescription(pluginName);
        if (plugin == null || !plugin.getPlugin().isEnabled())
            return false;
        plugin.getPlugin().setEnabled(false);
        plugin.getPlugin().disable();
        this.client.getEventHandler().unregisterListener(plugin.getPlugin());
        this.client.getModuleHandler().unregisterModule(plugin.getPlugin());
        return true;
    }

    public boolean loadPlugin(String pluginName) {
        if (getPluginDescription(pluginName) != null)
            return false;
        File folder = new File(this.pluginsFolder);
        if (!folder.exists())
            folder.mkdirs();
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.getName().equalsIgnoreCase(pluginName + ".jar")) {
                try {
                    PluginDescription description = loadPlugin(file);
                    if (description != null) {
                        this.loadedPlugins.add(description);
                        return true;
                    }
                } catch (PluginLoadException e) {
                    Logger.getLogger(getClass().getName()).info(e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    public boolean unloadPlugin(String pluginName) {
        PluginDescription plugin = getPluginDescription(pluginName);
        if (plugin == null)
            return false;
        if (plugin.getPlugin().isEnabled()) {
            disablePlugin(pluginName);
        }
        this.loadedPlugins.remove(plugin);
        plugin.setPlugin(null);
        return true;
    }

    public  void unloadPlugins() {
        disablePlugins();
        this.loadedPlugins.clear();
    }

    public PluginDescription getPluginDescription(String pluginName) {
        return this.loadedPlugins.stream().filter(d -> d.getName().equalsIgnoreCase(pluginName)).findFirst().orElse(null);
    }

}

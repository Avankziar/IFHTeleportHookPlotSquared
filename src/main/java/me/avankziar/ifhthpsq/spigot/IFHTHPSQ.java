package main.java.me.avankziar.ifhthpsq.spigot;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.ifh.spigot.teleport.Teleport;
import main.java.me.avankziar.ifhthpsq.spigot.database.YamlHandler;
import main.java.me.avankziar.ifhthpsq.spigot.database.YamlManager;

public class IFHTHPSQ extends JavaPlugin
{
	public static Logger log;
	private static IFHTHPSQ plugin;
	public String pluginName = "InterfaceHubTeleportHookPlotSquared";
	private YamlHandler yamlHandler;
	private YamlManager yamlManager;
	
	private Teleport teleportConsumer;
	
	public void onEnable()
	{
		plugin = this;
		log = getLogger();
		
		//https://patorjk.com/software/taag/#p=display&f=ANSI%20Regular&t=IFHTHSQ
		log.info(" ██ ███████ ██   ██ ████████ ██   ██ ██████  ███████  ██████   | API-Version: "+plugin.getDescription().getAPIVersion());
		log.info(" ██ ██      ██   ██    ██    ██   ██ ██   ██ ██      ██    ██  | Author: "+plugin.getDescription().getAuthors().toString());
		log.info(" ██ █████   ███████    ██    ███████ ██████  ███████ ██    ██  | Plugin Website: "+plugin.getDescription().getWebsite());
		log.info(" ██ ██      ██   ██    ██    ██   ██ ██           ██ ██ ▄▄ ██  | Depend Plugins: "+plugin.getDescription().getDepend().toString());
		log.info(" ██ ██      ██   ██    ██    ██   ██ ██      ███████  ██████   | SoftDepend Plugins: "+plugin.getDescription().getSoftDepend().toString());
		log.info("                                                         ▀▀    | LoadBefore: "+plugin.getDescription().getLoadBefore().toString());
		
		yamlHandler = new YamlHandler(this);
	
		setupTeleport();
	}
	
	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		log.info(pluginName + " is disabled!");
	}

	public static IFHTHPSQ getPlugin()
	{
		return plugin;
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public YamlManager getYamlManager()
	{
		return yamlManager;
	}

	public void setYamlManager(YamlManager yamlManager)
	{
		this.yamlManager = yamlManager;
	}
	
	private void setupTeleport() 
	{
        if(Bukkit.getPluginManager().getPlugin("InterfaceHub") == null) 
        {
            return;
        }
        new BukkitRunnable()
        {
        	int i = 0;
			@Override
			public void run()
			{
				try
				{
					if(i == 20)
				    {
						cancel();
						return;
				    }
				    RegisteredServiceProvider<main.java.me.avankziar.ifh.spigot.teleport.Teleport> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 main.java.me.avankziar.ifh.spigot.teleport.Teleport.class);
				    if(rsp == null) 
				    {
				    	//Check up to 20 seconds after the start, to connect with the provider
				    	i++;
				        return;
				    }
				    teleportConsumer = rsp.getProvider();
				    log.info(pluginName + " detected InterfaceHub >>> BonusMalus.class is consumed!");
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}
			}
        }.runTaskTimer(plugin, 20L, 20*2);
	}
	
	public Teleport getTeleport()
	{
		return teleportConsumer;
	}
}
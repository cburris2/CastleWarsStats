package edu.unca.cburris.bukkit.castlewarsstats;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

import javax.persistence.PersistenceException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;





/*
 * This is the main class of the sample plug-in
 */
public class CastleWarsStats extends JavaPlugin {
	/*
	 * This is called when your plug-in is enabled
	 */
	
	
	
	@Override
	public void onEnable() {
		
		PluginDescriptionFile pdfFile = this.getDescription();
		//PluginManager pm = getServer().getPluginManager();
		
		setupDatabase();
		
		CommandExecutor e = new CWSCommandExecutor(this);
		
		getCommand("stat").setExecutor(e);
		System.out.println(pdfFile.getName() + " version" + pdfFile.getVersion() + " is enabled.");
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		
		(new File(pluginFolder)).mkdirs();
		
		
		

		

		// set the command executor for sample
		//this.getCommand("ban").setExecutor(new BanExecutor(this));
		this.getServer().getPluginManager().registerEvents(new CWSListener(this), this);
	}

	private void setupDatabase() {
		try{
			getDatabase().find(Stats.class).findRowCount();
		}catch (PersistenceException pe){
			System.out.println("Initializing database for " + getDescription().getName() + ".");
			installDDL();
		}
		
	}
	

	/*
	 * This is called when your plug-in shuts down
	 */
	@Override
	public void onDisable() {
		saveDefaultConfig(); 
		
		//log.info("FirstPlugin has been disabled")
	}
	@Override
	public List<Class<?>> getDatabaseClasses(){
			List<Class<?>> glist = new ArrayList<Class<?>>();
			glist.add(Stats.class);
			return glist;
	}

}

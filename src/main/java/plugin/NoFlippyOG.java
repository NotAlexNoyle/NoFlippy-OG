package plugin;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

// Extending this class is standard bukkit boilerplate for any plugin, or else the server software won't load the classes.
public class NoFlippyOG extends JavaPlugin {

	private static NoFlippyOG plugin;
	private static StateFlag FlippyFlag;

	// What to do when the plugin is run by the server.
	@Override
	public void onEnable() {

		// Initialize the plugin object to be passed around to other classes.
		plugin = this;

		// Register the Event Listener class.
		this.getServer().getPluginManager().registerEvents(new Listeners(), this);

	}

	public void onLoad(){

		// Add the WorldGuard flag for an area where NoFlippy is active.
		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
		try {

			// Create a flag with the name "my-custom-flag", defaulting to true.
			StateFlag flag = new StateFlag("can-flippy", true);

			// Register the new flag with WorldGuard.
			registry.register(flag);

			// Only set the field if there was no error.
			FlippyFlag = flag;

		}
		catch (FlagConflictException e) {

			// Some other plugin registered a flag by the same name already.
			// You can use the existing flag, but this may cause conflicts - be sure to check type.
			Flag<?> existing = registry.get("can-flippy");
			if (existing instanceof StateFlag) {

				FlippyFlag = (StateFlag) existing;

			}

		}

	}

	// Runs plugin asynchronously so multiple players can use it at once efficiently.
	public BukkitTask runTaskAsynchronously(final Runnable run) {

		// Schedule processes.
		return this.getServer().getScheduler().runTaskAsynchronously(this, run);

	}

	// Class constructor.
	public static NoFlippyOG getPlugin() {

		// Pass instance of main to other classes.
		return plugin;

	}

	// Share FlippyFlag with Listeners.
	public static StateFlag getFlippyFlag() {

		return FlippyFlag;

	}

}

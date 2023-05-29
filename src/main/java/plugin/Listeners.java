// This is free and unencumbered software released into the public domain.
// Author: NotAlexNoyle (admin@true-og.net)

package plugin;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

public class Listeners implements Listener {

	// Declare instance of class as static so multiple players can use it.
	private static Listeners instance;

	// Return instance of class as static so multiple players can use it.
	public static Listeners getInstance() {

		// Pass back Listeners to main.
		return instance;

	}

	// Listen for a player's game mode changing.
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {

		// Get the nature of the interaction and store it.
		Action action = event.getAction();
		// Get the block that was interacted with and store it.
		Block blockClicked = event.getClickedBlock();
		// Get the player who interacted and store them.
		Player player = event.getPlayer();

		// If the interaction was a right click, do this...
		if(action.isRightClick()) {

			// If the interaction was with a trap door, do this...
			if(blockClicked.getType().toString().contains("TRAPDOOR")); {

				// If the player does not have permission to flip trap doors, do this...
				if(! player.hasPermission("noflippy.bypass")) {

					// get the WorldGuard player
					LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
					RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
					com.sk89q.worldedit.util.Location worldGuardPlayerLocation = localPlayer.getLocation();

					RegionQuery query = container.createQuery();
					ApplicableRegionSet set = query.getApplicableRegions(worldGuardPlayerLocation);

						// if the Flippy Flag is set to DENY, cancel the event
					if (!set.testState(localPlayer, NoFlippyOG.getFlippyFlag())){

						// Cancel the trapdoor flip.
						event.setCancelled(true);

					}

				}

			}

		}

	}

}
package main.java.me.avankziar.ifhthsq.spigot.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.eventbus.Subscribe;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.events.PlayerTeleportToPlotEvent;
import com.plotsquared.core.events.Result;
import com.plotsquared.core.events.TeleportCause;

import main.java.me.avankziar.ifhthsq.spigot.IFHTHSQ;

public class HomeTeleportListener
{
	public HomeTeleportListener() 
	{
	    PlotAPI api = new PlotAPI();
	    api.registerListener(this);
	}
	
	@Subscribe
	public void onPlayerHomeTeleport(PlayerTeleportToPlotEvent event)
	{
		if(event.getEventResult() == Result.DENY)
		{
			return;
		}
		if(event.getCause() == TeleportCause.COMMAND_HOME)
		{
			if(IFHTHSQ.getPlugin().getYamlHandler().getConfig().get(event.getPlot().getWorldName()) != null)
			{
				Player player = Bukkit.getPlayer(event.getPlotPlayer().getUUID());
				if(player == null)
				{
					return;
				}
				String server = IFHTHSQ.getPlugin().getYamlHandler().getConfig().getString(event.getPlot().getWorldName());
				String world = event.getPlot().getWorldName();
				int x = event.getPlot().getPosition().getX();
				int y = event.getPlot().getPosition().getY();
				int z = event.getPlot().getPosition().getZ();
				float yaw = event.getPlot().getPosition().getYaw();
				float pitch = event.getPlot().getPosition().getPitch();
				event.setEventResult(Result.DENY);
				IFHTHSQ.getPlugin().getTeleport().teleport(player, server, world, x, y, z, yaw, pitch);
			}
		}
	}
}
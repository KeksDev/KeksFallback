package tk.keksdev.fallback;

import dev.waterdog.event.defaults.PlayerDisconnectEvent;
import dev.waterdog.player.ProxiedPlayer;
import dev.waterdog.plugin.Plugin;
import dev.waterdog.utils.Configuration;
import dev.waterdog.network.ServerInfo;

public class KeksFallback extends Plugin {

    private String fallback;

    public void onEnable() {
        saveResource("config.yml");
        Configuration cfg = getConfig();
        fallback = cfg.getString("fallback_server");
        getProxy().getEventManager().subscribe(PlayerDisconnectEvent.class, this::onKick);
    }

    public void onKick(PlayerDisconnectEvent event) {
        event.setCancelled();
        ServerInfo server = getProxy().getServerInfo(fallback);
        ProxiedPlayer player = event.getPlayer();
        if (server == null) {
        	player.sendMessage("No fallback server selected");
        } else {
	        player.connect(server);
		}
    }
}

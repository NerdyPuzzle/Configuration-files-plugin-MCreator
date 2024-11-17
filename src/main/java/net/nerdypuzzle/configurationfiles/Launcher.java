package net.nerdypuzzle.configurationfiles;

import net.mcreator.blockly.data.BlocklyLoader;
import net.mcreator.ui.blockly.BlocklyEditorType;
import net.nerdypuzzle.configurationfiles.element.types.PluginElementTypes;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher extends JavaPlugin {

	private static final Logger LOG = LogManager.getLogger("Configuration Files");
	public static final BlocklyEditorType CONFIG_EDITOR = new BlocklyEditorType("config", "cfg", "config_start");

	public Launcher(Plugin plugin) {
		super(plugin);

		addListener(PreGeneratorsLoadingEvent.class, e -> {
			PluginElementTypes.load();
			BlocklyLoader.INSTANCE.addBlockLoader(CONFIG_EDITOR);
		});

		LOG.info("Config plugin was loaded");

	}

}
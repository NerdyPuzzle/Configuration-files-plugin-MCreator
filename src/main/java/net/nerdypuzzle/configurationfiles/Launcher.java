package net.nerdypuzzle.configurationfiles;

import net.mcreator.blockly.IBlockGenerator;
import net.mcreator.blockly.InternalBlocksLoader;
import net.mcreator.blockly.data.BlocklyLoader;
import net.mcreator.ui.blockly.BlocklyEditorType;
import net.nerdypuzzle.configurationfiles.element.types.PluginElementTypes;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class Launcher extends JavaPlugin {

	private static final Logger LOG = LogManager.getLogger("Configuration Files");
	public static final BlocklyEditorType CONFIG_EDITOR = new BlocklyEditorType("config", "cfg", "config_start");

	public Launcher(Plugin plugin) {
		super(plugin);

		addListener(PreGeneratorsLoadingEvent.class, e -> {
            try {
                Field blocksField = InternalBlocksLoader.class.getDeclaredField("internalBlocks");
                blocksField.setAccessible(true);
                ((Map<BlocklyEditorType, List<IBlockGenerator>>)blocksField.get(null)).put(CONFIG_EDITOR, List.of());
            } catch (Exception ignored) {}
            BlocklyLoader.INSTANCE.registerBlockLoader(CONFIG_EDITOR);
            PluginElementTypes.load();
		});

		LOG.info("Config plugin was loaded");

	}

}
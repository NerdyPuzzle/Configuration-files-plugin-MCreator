package net.nerdypuzzle.configurationfiles.element.types;

import net.mcreator.element.ModElementType;
import net.nerdypuzzle.configurationfiles.ui.modgui.ConfigGUI;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> CONFIG;

    public static void load(){
        CONFIG = register(
                new ModElementType<>("config", (Character) null, ConfigGUI::new, Config.class)
        );
    }
}

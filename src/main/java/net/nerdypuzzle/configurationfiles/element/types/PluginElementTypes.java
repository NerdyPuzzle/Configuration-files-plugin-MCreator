package net.nerdypuzzle.configurationfiles.element.types;

import net.mcreator.element.BaseType;
import net.mcreator.element.ModElementType;
import net.nerdypuzzle.configurationfiles.ui.modgui.ConfigGUI;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> CONFIG;

    public static void load(){
        CONFIG = register(
                new ModElementType<>("config", (Character) null, BaseType.OTHER, ConfigGUI::new, Config.class)
        );
    }
}

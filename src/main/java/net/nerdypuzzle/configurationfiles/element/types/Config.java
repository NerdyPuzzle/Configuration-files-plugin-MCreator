package net.nerdypuzzle.configurationfiles.element.types;

import net.mcreator.element.NamespacedGeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.workspace.elements.ModElement;

import java.util.List;

public class Config extends NamespacedGeneratableElement {
	public List<Config.Pool> pools;
	public String file;
	public int configType;
	public Config(ModElement element) {
		super(element);
	}

	public static class Pool {
		public String category;
		public List<Config.Pool.Entry> entries;

		public Pool() {
		}

		public static class Entry {
			public MItemBlock item;
			public int silkTouchMode;
			public MItemBlock block;
			public int logicField;
			public double numberField;
			public String textDefault;
			public String comment;
			public String vardisplay;
			public String varname;
			public Boolean enablecomment;
			public Entry() {
			}
		}
	}
}
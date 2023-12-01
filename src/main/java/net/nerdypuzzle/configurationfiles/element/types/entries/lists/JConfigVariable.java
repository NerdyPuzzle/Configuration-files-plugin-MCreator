package net.nerdypuzzle.configurationfiles.element.types.entries.lists;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.minecraft.JEntriesList;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.component.VTextField;
import net.nerdypuzzle.configurationfiles.element.types.Config;
import net.nerdypuzzle.configurationfiles.element.types.entries.JVariableEntry;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JConfigVariable extends JEntriesList {

    private final VTextField category = new VTextField(13);
    private final List<JVariableEntry> entryList = new ArrayList();
    private final JPanel entries = new JPanel(new GridLayout(0, 1, 5, 5));

    public JConfigVariable(MCreator mcreator, IHelpContext gui, JPanel parent, List<JConfigVariable> pollList) {
        super(mcreator, new BorderLayout(), gui);
        this.setOpaque(false);
        JComponent container = PanelUtils.expandHorizontally(this);
        parent.add(container);
        pollList.add(this);
        this.setBackground(((Color)UIManager.get("MCreatorLAF.DARK_ACCENT")).brighter());
        JPanel topbar = new JPanel(new FlowLayout(0));
        topbar.setOpaque(false);
        topbar.add(L10N.label("elementgui.config.categoryname", new Object[0]));
        topbar.add(this.category);
        topbar.add(Box.createHorizontalGlue());
        JButton add = new JButton(UIRES.get("16px.add.gif"));
        add.setText(L10N.t("elementgui.config.new_variable", new Object[0]));
        JButton remove = new JButton(UIRES.get("16px.clear"));
        remove.setText(L10N.t("elementgui.config.remove_category", new Object[0]));
        remove.addActionListener((e) -> {
            pollList.remove(this);
            parent.remove(container);
            parent.revalidate();
            parent.repaint();
        });
        JComponent component = PanelUtils.centerAndEastElement(topbar, PanelUtils.join(2, new Component[]{add, remove}));
        component.setOpaque(true);
        component.setBackground(((Color)UIManager.get("MCreatorLAF.DARK_ACCENT")).brighter());
        this.add("North", component);
        this.entries.setOpaque(false);
        add.addActionListener((e) -> {
            JVariableEntry entry = new JVariableEntry(mcreator, this.entries, this.entryList);
            this.registerEntryUI(entry);
        });
        this.add("Center", this.entries);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((Color)UIManager.get("MCreatorLAF.BRIGHT_COLOR"), 1), L10N.t("elementgui.config.configcategory", new Object[0]), 0, 0, this.getFont().deriveFont(12.0F), (Color)UIManager.get("MCreatorLAF.BRIGHT_COLOR")));
        parent.revalidate();
        parent.repaint();
    }

    public void reloadDataLists() {
        this.entryList.forEach(JVariableEntry::reloadDataLists);
    }

    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult(new IValidable[]{this.category});
    }

    public Config.Pool getPool() {
        Config.Pool pool = new Config.Pool();
        pool.category = this.category.getText();
        pool.entries = this.entryList.stream().map(JVariableEntry::getEntry).filter(Objects::nonNull).toList();
        return pool.entries.isEmpty() ? null : pool;
    }

    public void setPool(Config.Pool pool) {
        this.category.setText(pool.category);
        if (pool.entries != null) {
            pool.entries.forEach((e) -> {
                JVariableEntry entry = new JVariableEntry(this.mcreator, this.entries, this.entryList);
                this.registerEntryUI(entry);
                entry.setEntry(e);
            });
        }

    }
}

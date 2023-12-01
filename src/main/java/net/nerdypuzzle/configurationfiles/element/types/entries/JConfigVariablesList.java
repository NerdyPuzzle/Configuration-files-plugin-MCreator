package net.nerdypuzzle.configurationfiles.element.types.entries;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.JEntriesList;
import net.nerdypuzzle.configurationfiles.element.types.Config;
import net.nerdypuzzle.configurationfiles.element.types.entries.lists.JConfigVariable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JConfigVariablesList extends JEntriesList {
    private final List<JConfigVariable> poolList = new ArrayList();
    private final JPanel pools = new JPanel(new GridLayout(0, 1, 5, 5));

    public JConfigVariablesList(MCreator mcreator, IHelpContext gui) {
        super(mcreator, new BorderLayout(), gui);
        this.setOpaque(false);
        this.pools.setOpaque(false);
        JToolBar bar = new JToolBar();
        bar.setFloatable(false);
        this.add.setText(L10N.t("elementgui.config.new_category", new Object[0]));
        this.add.addActionListener((e) -> {
            JConfigVariable pool = new JConfigVariable(mcreator, gui, this.pools, this.poolList);
            this.registerEntryUI(pool);
        });
        bar.add(this.add);
        this.add("North", bar);
        JScrollPane sp = new JScrollPane(PanelUtils.pullElementUp(this.pools)) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D)g.create();
                g2d.setColor((Color)UIManager.get("MCreatorLAF.LIGHT_ACCENT"));
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.45F));
                g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.getVerticalScrollBar().setUnitIncrement(11);
        sp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add("Center", sp);
    }

    public void reloadDataLists() {
        this.poolList.forEach(JConfigVariable::reloadDataLists);
    }

    public List<Config.Pool> getPools() {
        return this.poolList.stream().map(JConfigVariable::getPool).filter(Objects::nonNull).toList();
    }

    public void setPools(List<Config.Pool> lootTablePools) {
        lootTablePools.forEach((e) -> {
            JConfigVariable pool = new JConfigVariable(this.mcreator, this.gui, this.pools, this.poolList);
            this.registerEntryUI(pool);
            pool.setPool(e);
        });
    }
}

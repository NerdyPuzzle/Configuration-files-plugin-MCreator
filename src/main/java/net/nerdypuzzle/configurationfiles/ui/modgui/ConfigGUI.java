package net.nerdypuzzle.configurationfiles.ui.modgui;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.workspace.elements.ModElement;
import net.nerdypuzzle.configurationfiles.element.types.Config;
import net.mcreator.ui.modgui.ModElementGUI;
import net.nerdypuzzle.configurationfiles.element.types.entries.JConfigVariablesList;

import javax.swing.*;
import java.awt.*;

public class ConfigGUI extends ModElementGUI<Config> {
    private final VTextField file = new VTextField(13);
    private final JComboBox<String> configType = new JComboBox(new String[]{"COMMON", "SERVER", "CLIENT"});
    private JConfigVariablesList lootTablePools;
    private final ValidationGroup pages = new ValidationGroup();

    public ConfigGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        this.initGUI();
        super.finalizeGUI(false);
    }

    protected void initGUI() {
        JPanel pane3 = new JPanel(new BorderLayout());
        pane3.setOpaque(false);

        this.file.setValidator(new TextFieldValidator(this.file, L10N.t("elementgui.config.notempty", new Object[0])));
        this.file.enableRealtimeValidation();

        JPanel northPanel = new JPanel(new GridLayout(2, 2, 10, 2));
        northPanel.setOpaque(false);
        northPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("config/name"), L10N.label("elementgui.config.name", new Object[0])));
        northPanel.add(this.file);
        northPanel.add(HelpUtils.wrapWithHelpButton(this.withEntry("config/type"), L10N.label("elementgui.config.type", new Object[0])));
        northPanel.add(this.configType);
        this.lootTablePools = new JConfigVariablesList(this.mcreator, this);
        pane3.add(PanelUtils.northAndCenterElement(PanelUtils.join(0, new Component[]{northPanel}), this.lootTablePools));
        this.addPage(pane3);


    }

    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult(new IValidable[]{this.file});
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        this.lootTablePools.reloadDataLists();
    }


    public void openInEditingMode(Config config) {
        this.lootTablePools.setPools(config.pools);
        this.file.setText(config.file);
        this.configType.setSelectedIndex(config.configType);
    }

    public Config getElementFromGUI() {
        Config config = new Config(this.modElement);
        config.file = this.file.getText();
        config.configType = this.configType.getSelectedIndex();
        config.pools = this.lootTablePools.getPools();

        return config;
    }
}
package net.nerdypuzzle.configurationfiles.element.types.entries;

import net.mcreator.element.parts.EntityEntry;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JEmptyBox;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.RegistryNameValidator;
import net.mcreator.ui.validation.validators.TextFieldValidator;
import net.mcreator.workspace.Workspace;
import net.nerdypuzzle.configurationfiles.element.types.Config;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JVariableEntry extends JPanel {
    private final MCItemHolder item;
    private final MCItemHolder block;
    private final JSpinner numberField = new JSpinner(new SpinnerNumberModel(0, -9999000, 9999000, 0.1));
    private final JComboBox<String> silkTouchMode = new JComboBox(new String[]{"Logic", "Number", "Text", "Block Registry Name", "Item Registry Name", "Entity Registry Name"});
    private final JComboBox<String> logicField = new JComboBox(new String[]{"True", "False"});
    private final JCheckBox enablecomment = L10N.checkbox("elementgui.config.enable_comment", new Object[0]);
    private final VTextField textDefault = new VTextField(7);
    private final VTextField comment = new VTextField(20);
    private final VTextField varname = new VTextField(12);
    private final VTextField vardisplay = new VTextField(12);
    private final Workspace workspace;
    private final JComboBox<String> entityType = new SearchableComboBox();
    private final JCheckBox onlyComment = L10N.checkbox("elementgui.config.comment_only", new Object[0]);

    public JVariableEntry(MCreator mcreator, JPanel parent, List<JVariableEntry> entryList) {
        this.setLayout(new BoxLayout(this, 3));
        this.setBackground(((Color)UIManager.get("MCreatorLAF.LIGHT_ACCENT")).darker());

        this.workspace = mcreator.getWorkspace();

        ElementUtil.loadAllEntities(this.workspace).forEach((e) -> {
            this.entityType.addItem(e.getName());
        });

        this.item = new MCItemHolder(mcreator, ElementUtil::loadBlocksAndItems);
        this.block = new MCItemHolder(mcreator, ElementUtil::loadBlocks);

        this.logicField.setEnabled(true);
        this.numberField.setEnabled(false);
        this.textDefault.setEnabled(false);
        this.block.setEnabled(false);
        this.item.setEnabled(false);
        this.comment.setEnabled(true);
        this.entityType.setEnabled(false);
        this.onlyComment.setEnabled(false);

        this.vardisplay.setValidator(new TextFieldValidator(this.vardisplay, L10N.t("elementgui.config.notempty", new Object[0])));
        this.vardisplay.enableRealtimeValidation();
        this.varname.setValidator((new RegistryNameValidator(this.varname, L10N.t("elementgui.config.regname", new Object[0]))).setAllowEmpty(false));
        this.varname.enableRealtimeValidation();


        this.silkTouchMode.addActionListener((e) -> {
                    switch (silkTouchMode.getSelectedItem().toString()) {
                        case "Logic":
                            this.logicField.setEnabled(true);
                            this.numberField.setEnabled(false);
                            this.textDefault.setEnabled(false);
                            this.block.setEnabled(false);
                            this.item.setEnabled(false);
                            this.entityType.setEnabled(false);
                            break;
                        case "Number":
                            this.numberField.setEnabled(true);
                            this.logicField.setEnabled(false);
                            this.textDefault.setEnabled(false);
                            this.block.setEnabled(false);
                            this.item.setEnabled(false);
                            this.entityType.setEnabled(false);
                            break;
                        case "Text":
                            this.textDefault.setEnabled(true);
                            this.numberField.setEnabled(false);
                            this.logicField.setEnabled(false);
                            this.block.setEnabled(false);
                            this.item.setEnabled(false);
                            this.entityType.setEnabled(false);
                            break;
                        case "Block Registry Name":
                            this.block.setEnabled(true);
                            this.numberField.setEnabled(false);
                            this.logicField.setEnabled(false);
                            this.textDefault.setEnabled(false);
                            this.item.setEnabled(false);
                            this.entityType.setEnabled(false);
                            break;
                        case "Item Registry Name":
                            this.item.setEnabled(true);
                            this.numberField.setEnabled(false);
                            this.logicField.setEnabled(false);
                            this.textDefault.setEnabled(false);
                            this.block.setEnabled(false);
                            this.entityType.setEnabled(false);
                            break;
                        case "Entity Registry Name":
                            this.entityType.setEnabled(false);
                            this.item.setEnabled(false);
                            this.numberField.setEnabled(false);
                            this.logicField.setEnabled(false);
                            this.textDefault.setEnabled(false);
                            this.block.setEnabled(false);
                            break;
                    }
                });

        JComponent container = PanelUtils.expandHorizontally(this);
        parent.add(container);
        entryList.add(this);
        JPanel line1 = new JPanel(new FlowLayout(0));
        line1.setOpaque(false);

        line1.add(L10N.label("elementgui.config.configuration_variable_name", new Object[0]));
        line1.add(this.varname);
        line1.add(L10N.label("elementgui.config.configuration_variables", new Object[0]));
        line1.add(this.silkTouchMode);
        line1.add(this.enablecomment);
        this.enablecomment.setOpaque(false);
        JButton remove = new JButton(UIRES.get("16px.clear"));
        remove.setText(L10N.t("elementgui.config.remove_variable", new Object[0]));

        remove.addActionListener((e) -> {
            entryList.remove(this);
            parent.remove(container);
            parent.revalidate();
            parent.repaint();
        });

        JPanel line2 = new JPanel(new FlowLayout(0));
        line2.setOpaque(false);
        line2.add(L10N.label("elementgui.config.comment_entry", new Object[0]));
        line2.add(this.comment);
        line2.add(L10N.label("elementgui.config.variable_display", new Object[0]));
        line2.add(this.vardisplay);
        this.add(PanelUtils.centerAndEastElement(line1, PanelUtils.join(new Component[]{remove})));
        this.add(line2);
        parent.revalidate();
        parent.repaint();

        JPanel line3 = new JPanel(new FlowLayout(0));
        line3.setOpaque(false);
        line3.add(L10N.label("elementgui.config.variable_value", new Object[0]));
        line3.add(new JEmptyBox(2, 0));
        line3.add(logicField);
        line3.add(new JEmptyBox(2, 0));
        line3.add(numberField);
        line3.add(new JEmptyBox(2, 0));
        line3.add(textDefault);
        line3.add(new JEmptyBox(2, 0));
        line3.add(this.block);
        line3.add(new JEmptyBox(2, 0));
        line3.add(this.item);
        line3.add(new JEmptyBox(2, 0));
        line3.add(this.entityType);
        line3.add(new JEmptyBox(2, 0));
        line3.add(onlyComment);

        this.add(PanelUtils.centerAndEastElement(line2, PanelUtils.join(new Component[]{remove})));
        this.add(line3);
        parent.revalidate();
        parent.repaint();

    }


    public void reloadDataLists() {
    }

    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult(new IValidable[]{this.vardisplay, this.varname});
    }
    public Config.Pool.Entry getEntry() {
            Config.Pool.Entry entry = new Config.Pool.Entry();

            entry.silkTouchMode = this.silkTouchMode.getSelectedIndex();
            entry.item = this.item.getBlock();
            entry.block = this.block.getBlock();
            entry.logicField = this.logicField.getSelectedIndex();
            entry.numberField = (Double) this.numberField.getValue();
            entry.textDefault = this.textDefault.getText();
            entry.comment = this.comment.getText();
            entry.vardisplay = this.vardisplay.getText();
            entry.varname = this.varname.getText();
            entry.enablecomment = this.enablecomment.isSelected();
            entry.onlyComment = this.onlyComment.isSelected();
            entry.entityType = this.entityType.getSelectedItem().toString();

            return entry;
    }
    public void setEntry(Config.Pool.Entry e) {
        this.item.setBlock(e.item);
        this.block.setBlock(e.block);
        this.numberField.setValue(e.numberField);
        this.textDefault.setText(e.textDefault);
        this.logicField.setSelectedIndex(e.logicField);
        this.silkTouchMode.setSelectedIndex(e.silkTouchMode);
        this.comment.setText(e.comment);
        this.vardisplay.setText(e.vardisplay);
        this.varname.setText(e.varname);
        this.enablecomment.setSelected(e.enablecomment);
        this.onlyComment.setSelected(e.onlyComment);
        this.entityType.setSelectedItem(e.entityType);
    }

    }

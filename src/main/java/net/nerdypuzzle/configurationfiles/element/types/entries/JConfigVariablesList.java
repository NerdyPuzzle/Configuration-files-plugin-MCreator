package net.nerdypuzzle.configurationfiles.element.types.entries;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JSingleEntriesList;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.nerdypuzzle.configurationfiles.element.types.Config;
import net.nerdypuzzle.configurationfiles.element.types.entries.lists.JConfigVariable;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class JConfigVariablesList extends JSingleEntriesList<JConfigVariable, Config.Pool> {
    public JConfigVariablesList(MCreator mcreator, IHelpContext gui) {
        super(mcreator, gui);
        this.setOpaque(false);
        this.entries.setLayout(new BoxLayout(this.entries, 3));
        this.add.setText(L10N.t("elementgui.config.new_category", new Object[0]));
        this.add.addActionListener((e) -> {
            JConfigVariable pool = new JConfigVariable(mcreator, gui, this.entries, this.entryList);
            this.registerEntryUI(pool);
        });
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void reloadDataLists() {
        this.entryList.forEach(JConfigVariable::reloadDataLists);
    }

    public List<Config.Pool> getEntries() {
        return this.entryList.stream().map(JConfigVariable::getPool).filter(Objects::nonNull).toList();
    }

    public void setEntries(List<Config.Pool> configCategories) {
        configCategories.forEach((e) -> {
            JConfigVariable entry = new JConfigVariable(this.mcreator, this.gui, this.entries, this.entryList);
            this.registerEntryUI(entry);
            entry.setPool(e);
        });
    }

    public AggregatedValidationResult getValidationResult() {
        AggregatedValidationResult validationResult = new AggregatedValidationResult();
        entryList.forEach(validationResult::addValidationElement);
        return validationResult;
    }

}

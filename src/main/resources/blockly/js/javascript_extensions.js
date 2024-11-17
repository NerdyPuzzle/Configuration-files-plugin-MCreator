Blockly.Blocks["config_start"] = {
    init: function () {
        this.appendDummyInput().appendField(javabridge.t("blockly.block.config_start"));
        this.setStyle("hat_blocks");
        this.setNextStatement(true);
        this.setColour(120);
        this.setTooltip(javabridge.t("blockly.block.config_start.tooltip"));
    }
}

class FieldRegistryName extends Blockly.FieldTextInput {
    /**
     * Validates that the input text is in the correct format:
     * lowercase, no spaces, underscores (_) as special characters, and numbers (0-9).
     * @param {string} newValue - The value to validate.
     * @return {string|null} The validated value or null if invalid.
     */
    doClassValidation_(newValue) {
        // Ensure newValue is a string and not undefined or null
        if (typeof newValue !== 'string') {
            return null;
        }

        // Check if the value matches the desired format
        if (!newValue.match(/^[a-z0-9_]+$/)) {
            return null;
        }

        return newValue;
    }
}

Blockly.fieldRegistry.register("registry_name", FieldRegistryName);

Blockly.Blocks['text_list_mutator_container'] = {
    init: function () {
        this.appendDummyInput().appendField(javabridge.t('blockly.block.text_list_mutator.container'));
        this.appendStatementInput('STACK');
        this.contextMenu = false;
        this.setColour('%{BKY_TEXTS_HUE}');
    }
};

Blockly.Blocks['text_list_mutator_input'] = {
    init: function () {
        this.appendDummyInput().appendField(javabridge.t('blockly.block.text_list_mutator.input'));
        this.setPreviousStatement(true);
        this.setNextStatement(true);
        this.contextMenu = false;
        this.fieldValues_ = [];
        this.setColour('%{BKY_TEXTS_HUE}');
    }
};

Blockly.Extensions.registerMutator('text_list_mutator', simpleRepeatingInputMixin(
        'text_list_mutator_container', 'text_list_mutator_input', 'entry',
        function(thisBlock, inputName, index) {
            thisBlock.appendDummyInput(inputName + index).setAlign(Blockly.Input.Align.RIGHT)
                .appendField(new Blockly.FieldTextInput(), 'entry' + index);
        }, false, ['entry'], true),
    undefined, ['text_list_mutator_input']);
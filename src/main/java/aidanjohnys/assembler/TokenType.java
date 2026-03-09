package aidanjohnys.assembler;

public enum TokenType {
    constant,
    hexConstant,
    charConstant,
    decimalConstant,
    hexAddress,
    argument,
    comment,
    variableInitialisation,
    variableInitialisationValue,
    identifier,
    assignment,
    instruction,
    label
}

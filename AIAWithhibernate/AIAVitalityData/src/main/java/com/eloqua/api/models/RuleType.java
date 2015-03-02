package com.eloqua.api.models;

public enum RuleType {
    always(1),
    ifNewIsNotNull(2),
    ifExistingIsNull(6),
    useFieldRule(17);
    
    /** the value of the choice */
    private int value;

    /**
     * constructor
     * @param value the value
     */
    private RuleType(int value) {
        this.value = value;
    }

    /**
     * getter of the value
     * @return int
     */
    public int getValue() {
        return value;
    }
    
}

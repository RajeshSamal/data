package com.eloqua.api.models;


public enum DataType{
    number(1),
    text(2),
    largeText(3),
    date(5),
    numeric(6);
    
       
    /** the value of the choice */
    private int value;

    /**
     * constructor
     * @param value the value
     */
    private DataType(int value) {
        this.value = value;
    }

    /**
     * getter of the value
     * @return int
     */
    public int getValue() {
        return value;
    }
    
    
    /**
     * Exemple of how to use this class.
     * @param args args
     */
   /* public static void main(String[] args) {
        DataType enum1 = DataType.text;
        System.out.println(enum1.name());
        System.out.println("DataType.text ::: "+DataType.text);
        
        System.out.println("enum1==>" + String.valueOf(enum1));
        Enum enum2GotByValue = Enum.getByValue(enum1.getValue());
        System.out.println("enum2GotByValue==>" + String.valueOf(enum2GotByValue));
        Enum enum3Unknown = Enum.getByValue(4);
        System.out.println("enum3Unknown==>" + String.valueOf(enum3Unknown));
    }*/


}

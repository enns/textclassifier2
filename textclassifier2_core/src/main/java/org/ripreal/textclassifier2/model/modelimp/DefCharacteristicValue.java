package org.ripreal.textclassifier2.model.modelimp;

import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;

class DefCharacteristicValue implements CharacteristicValue {

    private int id;

    private int orderNumber;

    private String value;

    private Characteristic characteristic;

    public DefCharacteristicValue() {
    }

    public DefCharacteristicValue(int orderNumber, String value) {
        this.orderNumber = orderNumber;
        this.value = value;
    }

    public DefCharacteristicValue(String value) {
        this(0, value);
    }

    @Override
    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    @Override
    public int getOrderNumber() {
        return orderNumber;
    }

    @Override
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof DefCharacteristicValue) && (this.value.equals(((DefCharacteristicValue) o).getValue())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
package org.ripreal.textclassifier2.model;

public interface CharacteristicValue {
    void setCharacteristic(Characteristic characteristic);

    int getOrderNumber();

    void setOrderNumber(int orderNumber);

    int getId();

    void setId(int id);

    String getValue();
}

package org.ripreal.textclassifier2.model;

public interface CharacteristicValuePair {
    Characteristic getKey();

    CharacteristicValue getValue();

    void setKey(Characteristic key);

    void setValue(CharacteristicValue value);
}

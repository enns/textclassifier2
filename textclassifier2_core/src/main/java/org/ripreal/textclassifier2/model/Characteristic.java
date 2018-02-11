package org.ripreal.textclassifier2.model;

import java.util.Set;

public interface Characteristic {

    String getName();

    Set<CharacteristicValue> getPossibleValues();

    void setPossibleValues(Set<CharacteristicValue> possibleValues);

    void addPossibleValue(CharacteristicValue value);
}
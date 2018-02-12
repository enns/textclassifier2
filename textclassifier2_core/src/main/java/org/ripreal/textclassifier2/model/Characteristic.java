package org.ripreal.textclassifier2.model;

import java.util.Set;

public interface Characteristic {

    String getName();

    Set<CharacteristicValue> getPossibleValues();

    void addPossibleValue(CharacteristicValue value);
}
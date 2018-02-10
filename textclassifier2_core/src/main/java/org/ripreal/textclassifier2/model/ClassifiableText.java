package org.ripreal.textclassifier2.model;

import java.util.Map;

public interface ClassifiableText {
    String getText();

    Map<Characteristic, CharacteristicValue> getCharacteristics();

    CharacteristicValue getCharacteristicValue(String characteristicName);
}

package org.ripreal.textclassifier2.model;

import java.util.Map;
import java.util.Set;

public interface ClassifiableText {

    String getId();

    String getText();

    Set<CharacteristicValuePair> getCharacteristics();

    CharacteristicValue getCharacteristicValue(String characteristicName);
}

package org.ripreal.textclassifier2.model;

import lombok.NonNull;

import java.util.Map;
import java.util.Set;

public interface ClassifiableText {

    String getId();

    String getText();

    void setText(String text);

    Set<CharacteristicValue> getCharacteristics();

    void setCharacteristics(Set<CharacteristicValue> characteristicValue);

    CharacteristicValue getCharacteristicValue(String characteristicName);
}

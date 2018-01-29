package org.ripreal.textclassifier2.model;

import java.util.Map;

public class ClassifiableText {

    private int id;

    private String text;

    private Map<Characteristic, CharacteristicValue> characteristics;

    public ClassifiableText() {
    }

    public ClassifiableText(String text, Map<Characteristic, CharacteristicValue> characteristics) {
        this.text = text;
        this.characteristics = characteristics;
    }

    public ClassifiableText(String text) {
        this(text, null);
    }

    public String getText() {
        return text;
    }

    public Map<Characteristic, CharacteristicValue> getCharacteristics() {
        return characteristics;
    }

    public CharacteristicValue getCharacteristicValue(String characteristicName) {
        return characteristics.get(new Characteristic(characteristicName));
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof ClassifiableText) && this.text.equals(((ClassifiableText) o).getText()) && this.characteristics.equals(((ClassifiableText) o).getCharacteristics());
    }
}
package org.ripreal.textclassifier2.model.modelimp;

import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.util.Map;

class DefClassifiableText implements ClassifiableText {

    private int id;

    private String text;

    private Map<Characteristic, CharacteristicValue> characteristics;

    public DefClassifiableText() {
    }

    public DefClassifiableText(String text, Map<Characteristic, CharacteristicValue> characteristics) {
        this.text = text;
        this.characteristics = characteristics;
    }

    public DefClassifiableText(String text) {
        this(text, null);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Map<Characteristic, CharacteristicValue> getCharacteristics() {
        return characteristics;
    }

    @Override
    public CharacteristicValue getCharacteristicValue(String characteristicName) {
        return characteristics.get(new DefCharacteristic(characteristicName));
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof DefClassifiableText) && this.text.equals(((DefClassifiableText) o).getText()) && this.characteristics.equals(((DefClassifiableText) o).getCharacteristics());
    }
}
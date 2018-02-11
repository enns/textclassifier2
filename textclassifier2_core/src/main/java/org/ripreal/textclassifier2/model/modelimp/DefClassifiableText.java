package org.ripreal.textclassifier2.model.modelimp;

import lombok.Data;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.util.Map;
@Data
class DefClassifiableText implements ClassifiableText {

    private final int id;

    private final String text;

    private final Map<Characteristic, CharacteristicValue> characteristics;

    @Override
    public CharacteristicValue getCharacteristicValue(String characteristicName) {
        return characteristics.get(new DefCharacteristic(characteristicName));
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof DefClassifiableText) && this.text.equals(((DefClassifiableText) o).getText()) && this.characteristics.equals(((DefClassifiableText) o).getCharacteristics());
    }

    @Override
    public int hashCode() {
        return this.text.hashCode();
    }
}
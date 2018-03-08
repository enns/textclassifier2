package org.ripreal.textclassifier2.model.modelimp;

import lombok.Data;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.CharacteristicValuePair;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.util.Map;
import java.util.Set;

@Data
public class DefClassifiableText implements ClassifiableText {

    private final String id;

    private final String text;

    private final Set<CharacteristicValuePair> characteristics;

    @Override
    public CharacteristicValue getCharacteristicValue(String characteristicName) {
        //todo: check and make appropriate handler for missing charactericivValue via Optional
        return characteristics.stream()
            .filter((pair) -> pair.getKey().equals(new DefCharacteristic(characteristicName)))
            .map(CharacteristicValuePair::getValue)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("characteristic value not exists!"));
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
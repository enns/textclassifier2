package org.ripreal.textclassifier2.model.modelimp;

import lombok.Data;
import lombok.NonNull;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import java.util.Set;

@Data
public class DefClassifiableText implements ClassifiableText {

    private final String id;

    @NonNull
    private String text;

    @NonNull
    private Set<CharacteristicValue> characteristics;

    @Override
    public CharacteristicValue getCharacteristicValue(String characteristicName) {
        //todo: check and make appropriate handler for missing charactericivValue via Optional
        return characteristics.stream()
            .filter(value -> value.getCharacteristic().equals(new DefCharacteristic(characteristicName)))
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
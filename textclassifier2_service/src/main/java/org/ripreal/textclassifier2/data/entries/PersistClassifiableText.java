package org.ripreal.textclassifier2.data.entries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.mapper.DeserializableField;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class PersistClassifiableText implements ClassifiableText {

    @Id
    @JsonIgnore
    private String id;
    @NonNull
    @DeserializableField
    private String text;
    @NonNull
    @DeserializableField
    Set<CharacteristicValue> characteristics;

    @Override
    public boolean equals(Object o) {
        return (
                o instanceof PersistClassifiableText)
                && this.text.equals(((PersistClassifiableText) o).getText())
                && this.characteristics.equals(((PersistClassifiableText) o).getCharacteristics());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public CharacteristicValue getCharacteristicValue(String characteristicName) {
        return characteristics.stream()
            .filter(value -> value.getCharacteristic().equals(new PersistCharacteristic(characteristicName)))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("characteristic value not exists!"));
    }

}

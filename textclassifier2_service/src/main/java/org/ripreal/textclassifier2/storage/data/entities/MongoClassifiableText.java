package org.ripreal.textclassifier2.storage.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.storage.data.mapper.DeserializableField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class MongoClassifiableText {

    @Id
    @JsonIgnore
    private String id;
    @NonNull
    @DeserializableField
    private String text;

    @NonNull
    @DeserializableField
    Set<MongoCharacteristicValue> characteristics;

    @Override
    public boolean equals(Object o) {
        return (
                o instanceof MongoClassifiableText)
                && this.text.equals(((MongoClassifiableText) o).getText())
                && this.characteristics.equals(((MongoClassifiableText) o).getCharacteristics());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public MongoCharacteristicValue getCharacteristicValue(String characteristicName) {
        return characteristics.stream()
            .filter(value -> value.getCharacteristic().equals(new MongoCharacteristic(characteristicName)))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("MongoCharacteristic value not exists!"));
    }

}

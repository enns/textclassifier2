package org.ripreal.textclassifier2.storage.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.storage.data.mapper.DeserializableField;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class MongoCharacteristicValue implements CharacteristicValue {

    @Id
    @JsonIgnore
    private String id;
    @NonNull
    @DeserializableField
    private String value;
    @NonNull
    @DeserializableField
    private int orderNumber;
    @DBRef
    @NonNull
    @DeserializableField
    private Characteristic characteristic;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof MongoCharacteristicValue)
                && (this.value.equals(((MongoCharacteristicValue) o).getValue()))
                && (this.characteristic.equals(((MongoCharacteristicValue) o).getCharacteristic())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

}


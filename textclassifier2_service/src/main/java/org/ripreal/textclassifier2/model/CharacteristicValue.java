package org.ripreal.textclassifier2.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class CharacteristicValue {

    @Id
    String id;
    @NonNull
    private String value;
    @NonNull
    private int orderNumber;
    @DBRef
    @NonNull
    private Characteristic characteristic;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof CharacteristicValue)
                && (this.value.equals(((CharacteristicValue) o).getValue()))
                && (this.characteristic.equals(((CharacteristicValue) o).getCharacteristic())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}


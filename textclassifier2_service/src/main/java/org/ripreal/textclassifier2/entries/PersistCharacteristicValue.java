package org.ripreal.textclassifier2.entries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class PersistCharacteristicValue implements CharacteristicValue {

    @Id
    String id;
    @NonNull
    private String value;
    @NonNull
    private int orderNumber;
    @DBRef
    @NonNull
    @JsonIgnore
    private Characteristic characteristic;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof PersistCharacteristicValue)
                && (this.value.equals(((PersistCharacteristicValue) o).getValue()))
                && (this.characteristic.equals(((PersistCharacteristicValue) o).getCharacteristic())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public void setCharacteristic(Characteristic characteristic) {

    }
}


package org.ripreal.textclassifier2.data.entries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.mapper.DeserializableField;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class PersistCharacteristic implements Characteristic {

    @Id
    @NonNull
    @DeserializableField
    private String name;
    @JsonIgnore
    @Transient
    private Set<CharacteristicValue> possibleValues;

    @Override
    @JsonIgnore
    @Transient
    public Set<CharacteristicValue> getPossibleValues() {
        return possibleValues;
    }

    @Override
    public void addPossibleValue(CharacteristicValue value) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof PersistCharacteristic) && (this.name.equals(((PersistCharacteristic) o).getName())));
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

}

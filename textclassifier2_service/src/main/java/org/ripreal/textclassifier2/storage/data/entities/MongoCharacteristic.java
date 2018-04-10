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
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class MongoCharacteristic implements Characteristic {

    @Id
    @NonNull
    @DeserializableField
    private String name;
    @JsonIgnore
    @Transient
    private Set<CharacteristicValue> possibleValues = new HashSet<>();

    @Override
    @JsonIgnore
    @Transient
    public Set<CharacteristicValue> getPossibleValues() {
        return possibleValues;
    }

    @Override
    @JsonIgnore
    @Transient
    public void setPossibleValues(Set<CharacteristicValue> possibleValues) {
        this.possibleValues = possibleValues;
    }

    @Override
    public void addPossibleValue(CharacteristicValue value) {
        // to fulfill collection you perform direct query to the base
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof MongoCharacteristic) && (this.name.equals(((MongoCharacteristic) o).getName())));
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

}

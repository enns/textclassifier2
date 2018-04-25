package org.ripreal.textclassifier2.storage.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.storage.data.mapper.DeserializableField;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class MongoCharacteristic {

    @Id
    @NonNull
    @DeserializableField
    private String name;
    @Transient
    @JsonIgnore
    private Set<MongoCharacteristicValue> possibleValues = new HashSet<>();

    @Transient
    @JsonIgnore
    public Set<MongoCharacteristicValue> getPossibleValues() {
        return possibleValues;
    }

    @Transient
    @JsonIgnore
    public void setPossibleValues(Set<MongoCharacteristicValue> possibleValues) {
        this.possibleValues = possibleValues;
    }

    public void addPossibleValue(MongoCharacteristicValue value) {
       possibleValues.add(value);
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof MongoCharacteristic) && (this.name.equals(((MongoCharacteristic) o).getName())));
    }

    @Override
    public String toString() {
        return String.format("%s : %s", name, possibleValues);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

}

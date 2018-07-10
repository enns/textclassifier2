package org.ripreal.textclassifier2.storage.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.ripreal.textclassifier2.storage.data.mapper.DeserializableField;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
public class MongoCharacteristic {

    @Id
    private String name;
    @Transient
    @JsonIgnore
    private Set<MongoCharacteristicValue> possibleValues = new HashSet<>();

    public MongoCharacteristic(String name, Set<MongoCharacteristicValue> possibleValues) {
        this.name = name;
        this.possibleValues = possibleValues;
    }

    public MongoCharacteristic() {
    }

    public MongoCharacteristic(String name) {
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

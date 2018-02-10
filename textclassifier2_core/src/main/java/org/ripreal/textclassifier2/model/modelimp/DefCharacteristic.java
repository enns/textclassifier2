package org.ripreal.textclassifier2.model.modelimp;

import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;

import java.util.LinkedHashSet;
import java.util.Set;

class DefCharacteristic implements Characteristic {

    private int id;

    private String name;

    private Set<CharacteristicValue> possibleValues;

    private DefCharacteristic(int id, String name, Set<CharacteristicValue> possibleValues) {
        this.id = id;
        this.name = name;
        this.possibleValues = possibleValues;
    }

    public DefCharacteristic(int id, String name) {
        this(id, name, new LinkedHashSet<>());
    }

    public DefCharacteristic(String name, Set<CharacteristicValue> possibleValues) {
        this(0, name, possibleValues);
    }

    public DefCharacteristic(String name) {
        this(0, name, new LinkedHashSet<>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Set<CharacteristicValue> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(Set<CharacteristicValue> possibleValues) {
        this.possibleValues = possibleValues;
    }

    public void addPossibleValue(CharacteristicValue value) {
        possibleValues.add(value);
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof Characteristic) && (this.name.equals(((Characteristic) o).getName())));
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}

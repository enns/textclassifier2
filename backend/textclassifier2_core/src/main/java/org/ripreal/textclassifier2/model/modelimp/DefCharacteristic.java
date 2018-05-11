package org.ripreal.textclassifier2.model.modelimp;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;

import java.util.HashSet;
import java.util.Set;

@Data
public class DefCharacteristic implements Characteristic {

    @NonNull
    private String name;

    private Set<CharacteristicValue> possibleValues = new HashSet<>();

    @Override
    public void setPossibleValues(Set<CharacteristicValue> charVals) {
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

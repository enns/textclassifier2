package org.ripreal.textclassifier2.model.modelimp;

import lombok.Data;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;

import javax.validation.constraints.NotNull;

@Data
class DefCharacteristicValue implements CharacteristicValue {

    private int id;

    private int orderNumber;

    private final String value;

    private @NotNull Characteristic characteristic;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof DefCharacteristicValue) && (this.value.equals(((DefCharacteristicValue) o).getValue())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
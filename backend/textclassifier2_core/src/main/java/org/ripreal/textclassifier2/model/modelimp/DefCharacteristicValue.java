package org.ripreal.textclassifier2.model.modelimp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefCharacteristicValue implements CharacteristicValue {

    @NonNull
    private String id;

    @NonNull
    private String value;
    // It is used to decode classifier value from vector when classify method() is invoked
    private int orderNumber;
    @NonNull
    private Characteristic characteristic;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof DefCharacteristicValue) && (this.value.equals(((DefCharacteristicValue) o).getValue())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return String.format("value: %s, orderNumber %s",  value, orderNumber);
    }

}
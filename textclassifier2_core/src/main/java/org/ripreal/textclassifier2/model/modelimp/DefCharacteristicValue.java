package org.ripreal.textclassifier2.model.modelimp;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;

@Data
@RequiredArgsConstructor
class DefCharacteristicValue implements CharacteristicValue {

    private int id;
    @NonNull
    private String value;
    // It is used to decode classifier value from vector when classify method() is invoked
    @NonNull
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
        return value;
    }
}
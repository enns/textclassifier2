package org.ripreal.textclassifier2.entries;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document
@RequiredArgsConstructor
public class PersistCharacteristic implements Characteristic {

    @Id
    @NonNull
    private String name;
    //@DBRef @NonNull private Set<CharacteristicValue> possibleValues = new HashSet<>();

    public PersistCharacteristic() {}

    public Set<CharacteristicValue> getPossibleValues() {
        return null;
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

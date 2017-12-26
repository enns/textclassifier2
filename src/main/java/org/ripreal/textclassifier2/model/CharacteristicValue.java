package org.ripreal.textclassifier2.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class CharacteristicValue {

    @Id private String id;
    @NonNull private String value;
    @NonNull private int orderNumber;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof CharacteristicValue) && (this.value.equals(((CharacteristicValue) o).getValue())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}


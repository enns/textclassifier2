package org.ripreal.textclassifier2.entries;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.CharacteristicValuePair;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PersistCharactValuePair implements CharacteristicValuePair {
    @DBRef
    @NonNull
    Characteristic key;
    @DBRef
    @NonNull
    CharacteristicValue value;
}

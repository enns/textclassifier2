package org.ripreal.textclassifier2.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CharactValuePair {
    @DBRef
    @NonNull
    Characteristic key;
    @DBRef
    @NonNull
    CharacteristicValue val;
}

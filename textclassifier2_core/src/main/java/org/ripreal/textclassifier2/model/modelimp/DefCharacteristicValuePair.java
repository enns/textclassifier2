package org.ripreal.textclassifier2.model.modelimp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.CharacteristicValuePair;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DefCharacteristicValuePair implements CharacteristicValuePair {
    @NonNull Characteristic key;
    @NonNull CharacteristicValue value;
}

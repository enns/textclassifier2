package org.ripreal.textclassifier2.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class ClassifiableText {

    @Id private String id;
    @NonNull private String text;
    @DBRef Map<Characteristic, CharacteristicValue> characteristics = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        return (
            o instanceof ClassifiableText)
            && this.text.equals(((ClassifiableText) o).getText())
            && this.characteristics.equals(((ClassifiableText) o).getCharacteristics());
    }

}

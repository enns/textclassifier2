package org.ripreal.textclassifier2.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class ClassifiableText {

    @Id
    private String id;
    @NonNull
    private String text;
    @NonNull
    Set<CharactValuePair> characteristics = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        return (
                o instanceof ClassifiableText)
                && this.text.equals(((ClassifiableText) o).getText())
                && this.characteristics.equals(((ClassifiableText) o).getCharacteristics());
    }

}

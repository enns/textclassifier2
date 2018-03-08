package org.ripreal.textclassifier2.entries;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class PersistVocabularyWord implements VocabularyWord {

    @Id
    private String id;
    @NonNull
    private String value;
    @NonNull
    private String ngram;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof PersistVocabularyWord) && (this.value.equals(((PersistVocabularyWord) o).getValue())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}

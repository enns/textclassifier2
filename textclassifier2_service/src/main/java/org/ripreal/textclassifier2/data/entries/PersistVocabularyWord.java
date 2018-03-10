package org.ripreal.textclassifier2.data.entries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.data.mapper.DeserializableField;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class PersistVocabularyWord implements VocabularyWord {

    @Id
    @JsonIgnore
    private String id;
    @NonNull
    @DeserializableField
    private String value;
    @NonNull
    @DeserializableField
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

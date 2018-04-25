package org.ripreal.textclassifier2.storage.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.storage.data.mapper.DeserializableField;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class MongoVocabularyWord {

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
        return ((o instanceof MongoVocabularyWord)
                && (this.value.equals(((MongoVocabularyWord) o).getValue()))
                && (this.ngram.equals(((MongoVocabularyWord) o).getNgram())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value, this.ngram);
    }
}

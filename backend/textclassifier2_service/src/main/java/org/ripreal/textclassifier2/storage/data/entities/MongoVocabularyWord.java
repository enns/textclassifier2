package org.ripreal.textclassifier2.storage.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.ripreal.textclassifier2.storage.data.mapper.DeserializableField;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class MongoVocabularyWord {

    @Id
    @JsonIgnore
    private String id;
    @DeserializableField
    private String value;
    @DeserializableField
    private String ngram;

    public MongoVocabularyWord() {
    }

    public MongoVocabularyWord(String value, String ngram) {
        this.value = value;
        this.ngram = ngram;
    }

    public MongoVocabularyWord(String id, String value, String ngram) {
        this.id = id;
        this.value = value;
        this.ngram = ngram;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNgram() {
        return ngram;
    }

    public void setNgram(String ngram) {
        this.ngram = ngram;
    }
}

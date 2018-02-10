package org.ripreal.textclassifier2.model.modelimp;


import org.ripreal.textclassifier2.model.VocabularyWord;

public class DefVocabularyWord implements VocabularyWord {

    private int id;

    private String value;

    public DefVocabularyWord() {
    }

    public DefVocabularyWord(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public DefVocabularyWord(String value) {
        this(0, value);
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof DefVocabularyWord) && (this.value.equals(((DefVocabularyWord) o).getValue())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
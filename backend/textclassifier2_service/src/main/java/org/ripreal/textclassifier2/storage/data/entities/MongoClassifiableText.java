package org.ripreal.textclassifier2.storage.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.ripreal.textclassifier2.storage.data.mapper.DeserializableField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class MongoClassifiableText {

    @Id
    @JsonIgnore
    private String id;
    @DeserializableField
    private String text;
    @DeserializableField
    Set<MongoCharacteristicValue> characteristics;

    public MongoClassifiableText(String id, Set<MongoCharacteristicValue> characteristics) {
        this.id = id;
        this.characteristics = characteristics;
    }

    public MongoClassifiableText(String id, String text, Set<MongoCharacteristicValue> characteristics) {
        this.id = id;
        this.text = text;
        this.characteristics = characteristics;
    }

    public MongoClassifiableText() {
    }

    @Override
    public boolean equals(Object o) {
        return (
                o instanceof MongoClassifiableText)
                && this.text.equals(((MongoClassifiableText) o).getText())
                && this.characteristics.equals(((MongoClassifiableText) o).getCharacteristics());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public MongoCharacteristicValue getCharacteristicValue(String characteristicName) {
        return characteristics.stream()
            .filter(value -> value.getCharacteristic().equals(new MongoCharacteristic(characteristicName)))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("MongoCharacteristic value not exists!"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<MongoCharacteristicValue> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<MongoCharacteristicValue> characteristics) {
        this.characteristics = characteristics;
    }
}

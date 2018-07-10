package org.ripreal.textclassifier2.storage.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.ripreal.textclassifier2.storage.data.mapper.DeserializableField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MongoCharacteristicValue {

    @Id
    @JsonIgnore
    private String id;
    @DeserializableField
    private String value;
    @DeserializableField
    private int orderNumber;
    @DBRef
    @DeserializableField
    private MongoCharacteristic characteristic;

    public MongoCharacteristicValue(String value, int orderNumber, MongoCharacteristic characteristic) {
        this.value = value;
        this.orderNumber = orderNumber;
        this.characteristic = characteristic;
    }

    public MongoCharacteristicValue(String id, String value, int orderNumber, MongoCharacteristic characteristic) {
        this.id = id;
        this.value = value;
        this.orderNumber = orderNumber;
        this.characteristic = characteristic;
    }

    public MongoCharacteristicValue() {
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof MongoCharacteristicValue)
                && (this.value.equals(((MongoCharacteristicValue) o).getValue()))
                && (this.characteristic.equals(((MongoCharacteristicValue) o).getCharacteristic())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
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

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public MongoCharacteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(MongoCharacteristic characteristic) {
        this.characteristic = characteristic;
    }
}


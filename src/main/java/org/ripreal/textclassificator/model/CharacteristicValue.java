package org.ripreal.textclassificator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class CharacteristicValue {

    @Id
    private ObjectId id;
    // @NonNull
  //  private int orderNumber;

    @NonNull
    private String value;

  //  @NonNull
 //   private Characteristic characteristic;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof CharacteristicValue) && (this.value.equals(((CharacteristicValue) o).getValue())));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}


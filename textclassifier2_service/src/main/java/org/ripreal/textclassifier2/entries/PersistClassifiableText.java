package org.ripreal.textclassifier2.entries;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.CharacteristicValuePair;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class PersistClassifiableText implements ClassifiableText {

    @Id
    private String id;
    @NonNull
    private String text;
    @NonNull
    //@Field("characteristics")
    //@JsonProperty("characteristics")
    Set<CharacteristicValuePair> characteristics;

    @Override
    public boolean equals(Object o) {
        return (
                o instanceof PersistClassifiableText)
                && this.text.equals(((PersistClassifiableText) o).getText())
                && this.characteristics.equals(((PersistClassifiableText) o).getCharacteristics());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public PersistCharacteristicValue getCharacteristicValue(String characteristicName) {
        throw new RuntimeException("not implemented");
    }

  //  @Transient
  //  @JsonIgnore
    public Set<CharacteristicValuePair> getCharacteristics() {

        /*return characteristics.stream().collect(
            HashMap::new,
            (map, entry) -> map.put(entry.getKey(), entry.getVal()),
            HashMap::putAll
        );
        */
        return characteristics;
    }

}

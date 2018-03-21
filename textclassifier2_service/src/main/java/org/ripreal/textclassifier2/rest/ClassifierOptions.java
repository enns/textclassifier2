package org.ripreal.textclassifier2.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassifierOptions {
    public String classifierType;
    public String characteristicName;
    public NGramStrategy.NGRAM_TYPES ngramType;
}

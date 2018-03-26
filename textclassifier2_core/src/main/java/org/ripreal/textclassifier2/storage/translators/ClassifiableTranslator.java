package org.ripreal.textclassifier2.storage.translators;

import org.ripreal.textclassifier2.model.*;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import java.util.List;
import java.util.Set;

public interface ClassifiableTranslator {

    // PROPERTIES

    ClassifiableFactory getCharacteristicFactory();

    // CLIENT SECTION

    List<ClassifiableText> toClassifiableTexts();

    Set<Characteristic> toCharacteristics();

    List<VocabularyWord> toVocabulary(NGramStrategy ngram);

    void reset();

}

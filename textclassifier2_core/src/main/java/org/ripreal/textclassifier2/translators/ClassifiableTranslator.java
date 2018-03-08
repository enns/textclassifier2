package org.ripreal.textclassifier2.translators;

import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.model.*;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.ngram.VocabularyBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface ClassifiableTranslator {

    // PROPERTIES

    ClassifiableFactory getCharacteristicFactory();

    // CLIENT SECTION

    List<ClassifiableText> toClassifiableTexts();

    Set<Characteristic> toCharacteristics();

    List<VocabularyWord> toVocabulary(NGramStrategy ngram);

    void reset();

    // OBSERVER

    void subscribe(ClassifierAction action);

    void dispatch(String text);

}

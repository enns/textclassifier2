package org.ripreal.textclassifier2.textreaders;

import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.ngram.VocabularyBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface ClassifiableReader {

    // PROPERTIES

    ClassifiableFactory getCharacteristicFactory();

    // CLIENT SECTION

    List<ClassifiableText> toClassifiableTexts();

    default Set<Characteristic> toCharacteristics() {
        return toClassifiableTexts().stream()
                .flatMap(text -> text.getCharacteristics().keySet().stream())
                .distinct()
                .collect(Collectors.toSet());
    }

    default List<VocabularyWord> toVocabulary(NGramStrategy ngram) {
        return new VocabularyBuilder(ngram).getVocabulary(toClassifiableTexts(), getCharacteristicFactory());
    }

    void reset();

    // OBSERVER

    void subscribe(ClassifierAction action);

    void dispatch(String text);

}

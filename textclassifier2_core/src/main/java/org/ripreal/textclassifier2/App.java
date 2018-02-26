package org.ripreal.textclassifier2;

import org.ripreal.textclassifier2.classifier.Classifier;
import org.ripreal.textclassifier2.classifier.ClassifierBuilder;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.textreaders.ClassifiableReader;
import org.ripreal.textclassifier2.textreaders.ClassifiableReaderBuilder;

import java.io.File;
import java.util.List;

public class App {

    private final static Config CONFIG = new Config("./config/config.ini");

    public static void main(String... args) {

        Classifier classifier = ClassifierBuilder
                .fromReader((builder) -> builder.newExcelFileReader(new File(CONFIG.getTestDataPath()), 1), new DefClassifiableFactory())
                .subscribe((action, msg) -> System.out.println(String.format("%s: %s", action, msg)))
                .addNeroClassifierUnit("Отдел", NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_BIGRAM))
                .addNeroClassifierUnit("Тип", NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM))
                .build();

        ClassifiableReader reader = ClassifiableReaderBuilder.builder(new DefClassifiableFactory()).newExcelFileReader(new File(CONFIG.getTestDataPath()), 1);
        ClassifiableText text = reader.toClassifiableTexts().get(0);
        List<CharacteristicValue> charact = classifier.classify(text);
        System.out.println(String.format("Classified text %s", text.getText()));
        System.out.println(String.format("As %s", charact));

    }

    static {
        if (!CONFIG.isLoaded()) {
            System.out.println("Config file is not found or it is empty.");
            System.exit(1);
        }
    }
}

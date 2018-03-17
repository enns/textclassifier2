package org.ripreal.textclassifier2;

import org.ripreal.textclassifier2.classifier.Classifier;
import org.ripreal.textclassifier2.classifier.ClassifierBuilder;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.translators.ClassifiableTranslator;
import org.ripreal.textclassifier2.translators.ExcelFileTranslator;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

public class ClassifierApp {

    private final static String CONFIG_PATH = "./config/config.ini";
    private final static Config CONFIG = new Config(CONFIG_PATH);

    public static void main(String... args) {

        Classifier classifier = ClassifierBuilder
                .fromReader(new ExcelFileTranslator(new File(CONFIG.getTestDataPath()), 1, new DefClassifiableFactory()))
                .subscribe((action, msg) -> System.out.println(String.format("%s: %s", action, msg)))
                .addNeroClassifierUnit("Отдел", NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_BIGRAM))
                .addNeroClassifierUnit("Тип", NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM))
                .build();

        ClassifiableTranslator reader = new ExcelFileTranslator(new File(CONFIG.getTestDataPath()), 1, new DefClassifiableFactory());
        ClassifiableText text = reader.toClassifiableTexts().get(0);
        List<CharacteristicValue> charact = classifier.classify(text);
        System.out.println(String.format("Classified text %s", text.getText()));
        System.out.println(String.format("As %s", charact));

    }

    static {
        if (!CONFIG.isLoaded()) {
            System.out.println(String.format(
                "Config file on %s is not found or it is empty.", CONFIG_PATH));
            System.exit(1);
        }
    }
}

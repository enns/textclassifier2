package org.ripreal.textclassifier2;

import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.classifier.Classifier;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import java.io.File;

public class App {

    private final static Config CONFIG = new Config("./config/config.ini");

    public static void main(String... args) {
        Classifier
            .fromReader((builder) -> builder.newExcelFileReader(new File(CONFIG.getTestDataPath()), 1))
            .subscribe((action, msg) -> System.out.println(String.format("%s: %s", action, msg)))
            .addNeroClassifierUnit(
                Characteristic.byName("Отдел"),
                NGramStrategy.getNGramStrategy(NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM))
            .build();
    }

    static {
        if (!CONFIG.isLoaded()) {
            System.out.println("Config file is not found or it is empty.");
            System.exit(1);
        }
    }
}

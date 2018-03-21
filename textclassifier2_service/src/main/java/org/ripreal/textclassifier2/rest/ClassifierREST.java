package org.ripreal.textclassifier2.rest;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.classifier.Classifier;
import org.ripreal.textclassifier2.classifier.ClassifierBuilder;
import org.ripreal.textclassifier2.classifier.ClassifierUnit;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.translators.FromMongoToClassifiableTranslator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("classifier")
public class ClassifierREST {

    private ClassifierBuilder builder;

    private final FromMongoToClassifiableTranslator translator;

    @PostMapping(value = "init",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void initClassifier(@RequestParam("options") ClassifierOptions options) {
        builder = ClassifierBuilder.fromReader(translator).addNeroClassifierUnit(
            options.getCharacteristicName(), NGramStrategy.getNGramStrategy(options.getNgramType())
        );
    }

}


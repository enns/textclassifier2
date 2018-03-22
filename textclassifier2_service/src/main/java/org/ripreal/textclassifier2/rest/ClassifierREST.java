package org.ripreal.textclassifier2.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.classifier.ClassifierBuilder;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.rest.exceptions.IncorrectClassifierType;
import org.ripreal.textclassifier2.translators.FromMongoToClassifiableTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("classifier")
public class ClassifierREST {

    private final ClassifierBuilder builder;

    @Autowired
    public ClassifierREST(FromMongoToClassifiableTranslator translator) {
        this.builder = ClassifierBuilder.fromReader(translator);
    }

    @PostMapping(value = "init",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void initClassifier(@RequestBody List<ClassifierOptions> options) {
        options.forEach(parameters -> {
            if (parameters.getClassifierType().equals("neural"))
                builder.addNeroClassifierUnit(parameters.getCharacteristicName(),
                        NGramStrategy.getNGramStrategy(parameters.getNgramType()));
            else
                throw new IncorrectClassifierType();
        });
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassifierOptions {
        public String classifierType;
        public String characteristicName;
        public NGramStrategy.NGRAM_TYPES ngramType;
    }
}


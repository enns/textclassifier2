package org.ripreal.textclassifier2.storage.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ripreal.textclassifier2.classifier.ClassifierBuilder;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.controller.exceptions.IncorrectClassifierType;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.storage.testdata.MongoTestDataReader;
import org.ripreal.textclassifier2.testdata.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("classifier")
public class ClassifierREST {

    private final ClassifierBuilder builder;

    @Autowired
    public ClassifierREST(ClassifiableFactory factory, ClassifiableService service) {
        this.builder = ClassifierBuilder.fromReader(new MongoTestDataReader(service), factory);
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


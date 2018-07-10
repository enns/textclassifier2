package org.ripreal.textclassifier2.storage.web.rest;

import org.ripreal.textclassifier2.classifier.ClassifierBuilder;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.storage.testdata.ClassifiableMapper;
import org.ripreal.textclassifier2.storage.testdata.MongoTestDataReader;
import org.ripreal.textclassifier2.storage.web.rest.exceptions.IncorrectClassifierType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("classifier")
public class ClassifierREST {

    private final ClassifierBuilder builder;

    @Autowired
    public ClassifierREST(ClassifiableFactory factory, ClassifiableService service, ClassifiableMapper textMapper) {
        this.builder = ClassifierBuilder.fromReader(new MongoTestDataReader(service, textMapper), factory);
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

    public static class ClassifierOptions {
        public String classifierType;
        public String characteristicName;
        public NGramStrategy.NGRAM_TYPES ngramType;

        @java.beans.ConstructorProperties({"classifierType", "characteristicName", "ngramType"})
        public ClassifierOptions(String classifierType, String characteristicName, NGramStrategy.NGRAM_TYPES ngramType) {
            this.classifierType = classifierType;
            this.characteristicName = characteristicName;
            this.ngramType = ngramType;
        }

        public ClassifierOptions() {}

        public String getClassifierType() {return this.classifierType;}

        public String getCharacteristicName() {return this.characteristicName;}

        public NGramStrategy.NGRAM_TYPES getNgramType() {return this.ngramType;}

        public void setClassifierType(String classifierType) {this.classifierType = classifierType; }

        public void setCharacteristicName(String characteristicName) {this.characteristicName = characteristicName; }

        public void setNgramType(NGramStrategy.NGRAM_TYPES ngramType) {this.ngramType = ngramType; }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof ClassifierOptions)) return false;
            final ClassifierOptions other = (ClassifierOptions) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$classifierType = this.classifierType;
            final Object other$classifierType = other.classifierType;
            if (this$classifierType == null ? other$classifierType != null : !this$classifierType.equals(other$classifierType))
                return false;
            final Object this$characteristicName = this.characteristicName;
            final Object other$characteristicName = other.characteristicName;
            if (this$characteristicName == null ? other$characteristicName != null : !this$characteristicName.equals(other$characteristicName))
                return false;
            final Object this$ngramType = this.ngramType;
            final Object other$ngramType = other.ngramType;
            if (this$ngramType == null ? other$ngramType != null : !this$ngramType.equals(other$ngramType))
                return false;
            return true;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $classifierType = this.classifierType;
            result = result * PRIME + ($classifierType == null ? 43 : $classifierType.hashCode());
            final Object $characteristicName = this.characteristicName;
            result = result * PRIME + ($characteristicName == null ? 43 : $characteristicName.hashCode());
            final Object $ngramType = this.ngramType;
            result = result * PRIME + ($ngramType == null ? 43 : $ngramType.hashCode());
            return result;
        }

        protected boolean canEqual(Object other) {return other instanceof ClassifierOptions;}

        public String toString() {return "ClassifierREST.ClassifierOptions(classifierType=" + this.classifierType + ", characteristicName=" + this.characteristicName + ", ngramType=" + this.ngramType + ")";}
    }
}


package org.ripreal.textclassifier2.classifier;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.actions.ClassifierEventsDispatcher;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

// COMPOSITE
@RequiredArgsConstructor
public final class Classifier extends ClassifierEventsDispatcher {

    private final List<ClassifierUnit> classifierUnits;

    public List<CharacteristicValue> classify(@NonNull ClassifiableText classifiableText) {
        List<CharacteristicValue> values = new ArrayList<>();
        classifierUnits.forEach(unit -> values.add(unit.classify(classifiableText)));
        return values;
    }

    public void saveClassifiers(@NonNull File file) {
        for (ClassifierUnit classifier : classifierUnits) {
            classifier.saveTrainedClassifier(file);
        }
    }

    public void saveClassifiers(@NonNull OutputStream stream) {
        for (ClassifierUnit classifier : classifierUnits) {
            classifier.saveTrainedClassifier(stream);
        }
    }

    private void checkClassifiersAccuracy(@NonNull List<ClassifierUnit> units, @NonNull List<ClassifiableText> textForTesting) {

        for (ClassifierUnit unit : units) {
            Characteristic characteristic = unit.getCharacteristic();
            int correctlyClassified = 0;

            for (ClassifiableText classifiableText : textForTesting) {
                CharacteristicValue idealValue = classifiableText.getCharacteristicValue(characteristic.getName());
                CharacteristicValue classifiedValue = unit.classify(classifiableText);

                if (classifiedValue.getValue().equals(idealValue.getValue())) {
                    correctlyClassified++;
                }
            }
            double accuracy = ((double) correctlyClassified / textForTesting.size()) * 100;
            dispatch(String.format("Accuracy of Classifier for '" + characteristic.getName() + "' characteristic: %.2f%%", accuracy));
        }
    }

}

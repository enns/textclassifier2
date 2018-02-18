package org.ripreal.textclassifier2.textreaders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.actions.ClassifierAction;
import org.ripreal.textclassifier2.actions.ClassifierEventsDispatcher;
import org.ripreal.textclassifier2.model.CharacteristicFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
public class DefaultClassifiableReader extends ClassifierEventsDispatcher implements ClassifiableReader {

    private List<ClassifiableText> classifiableText;
    @Getter
    private final CharacteristicFactory characteristicFactory;

    @Override
    public List<ClassifiableText> toClassifiableTexts() {
        return classifiableText;
    }

    @Override
    public void reset() {
        classifiableText = null;
    }

}

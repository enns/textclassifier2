package org.ripreal.textclassifier2.textreaders;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.actions.ClassifierEventsDispatcher;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.util.List;

@RequiredArgsConstructor
public class ClassifiableTextReader extends ClassifierEventsDispatcher implements ClassifiableReader {

    private final List<ClassifiableText> texts;

    @Override
    public List<ClassifiableText> toClassifiableTexts() {
        return texts;
    }
}

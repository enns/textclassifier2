package org.ripreal.textclassifier2.textreaders;

import org.ripreal.textclassifier2.actions.ClassifierEventsDispatcher;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.util.List;

public interface ClassifiableReader extends ClassifierEventsDispatcher{
    List<ClassifiableText> toClassifiableTexts();
}
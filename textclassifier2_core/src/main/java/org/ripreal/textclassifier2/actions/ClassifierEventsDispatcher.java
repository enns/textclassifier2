package org.ripreal.textclassifier2.actions;

import org.ripreal.textclassifier2.actions.ClassifierAction;

public interface ClassifierEventsDispatcher {

    void subscribe(ClassifierAction action);

    void dispatch(String text);
}

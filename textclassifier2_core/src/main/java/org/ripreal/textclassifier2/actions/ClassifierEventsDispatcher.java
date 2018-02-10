package org.ripreal.textclassifier2.actions;

public interface ClassifierEventsDispatcher {

    void subscribe(ClassifierAction action);

    void dispatch(String text);
}

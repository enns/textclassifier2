package org.ripreal.textclassifier2.actions;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassifierEventsDispatcher {

    protected final List<ClassifierAction> listeners = new ArrayList<>();

    public void subscribe(ClassifierAction action) {
        listeners.add(action);
    }

    public void dispatch(String text) {
        listeners.forEach(action -> action.dispatch(ClassifierAction.EventTypes.NOT_SPECIFIED_EVENT, text));
    }
}

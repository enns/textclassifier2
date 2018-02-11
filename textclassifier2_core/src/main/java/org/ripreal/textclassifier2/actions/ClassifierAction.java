package org.ripreal.textclassifier2.actions;

@FunctionalInterface
public interface ClassifierAction {

    public enum EventTypes {NOT_SPECIFIED_EVENT, NERO_CLASSIFIER_EVENT, EXCEL_FILE_READER_EVENT, CLASSIFIER_EVENT}

    void dispatch(EventTypes source, String msg);
}

package org.ripreal.textclassifier2.actions;

public interface ClassifierAction {

    public enum EventTypes {NERO_CLASSIFIER_EVENT, EXCEL_FILE_READER_EVENT, CLASSIFIER_EVENT}

    void dispatch(EventTypes source, String msg);
}

package org.ripreal.textclassifier2.storage.testdata;

import org.ripreal.textclassifier2.testdata.TestDataReader;

import java.io.IOException;

public class MongoTestDataReader implements TestDataReader {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public ClassifiableData next() throws IOException {
        return null;
    }

    @Override
    public ClassifiableData readAll() throws IOException {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}

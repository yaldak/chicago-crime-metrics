package data;

import records.CrimeRecord;

import java.io.IOException;
import java.util.List;

public interface RecordReader {
    List<CrimeRecord> readCrimeRecords() throws IOException;
}

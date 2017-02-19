package output;

import java.io.IOException;

/**
 * Created by Lan.Jiang on 2/19/2017.
 */
public interface OutputHandler {

    void printTablesInfo() throws IOException;

    void printColumnsInfo() throws IOException;

    void printResultsInfo() throws IOException;
}

package output;

import java.io.IOException;

/**
 * Created by Lan.Jiang on 2/19/2017.
 */
public interface OutputHandler {

    abstract public void printTablesInfo() throws IOException;

    abstract public void printColumnsInfo() throws IOException;

    abstract public void printResultsInfo() throws IOException;
}

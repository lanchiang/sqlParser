package output;

import java.io.*;

/**
 * @author Lan Jiang
 * @since 18/02/2017
 */
abstract public class Output implements OutputHandler {

    final protected static String tablesTitle = "# TABLES";
    final protected static String columnsTitle = "# COLUMNS";
    final protected static String resultsTitle = "# RESULTS";

    protected String outputPath;

    protected BufferedWriter bufferedWriter;

    abstract public void writeToFile();

    protected void openBufferedWriter() throws IOException {
        if (outputPath==null)
            throw new NullPointerException("The output path can not be found!");
//        bufferedWriter = new BufferedWriter(new FileWriter(outputPath));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "utf-8"));
    }

    protected void closeBufferedWriter() throws IOException {
        bufferedWriter.close();
    }
}

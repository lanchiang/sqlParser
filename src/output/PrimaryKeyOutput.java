package output;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lan Jiang
 * @since 18/02/2017
 */
public class PrimaryKeyOutput extends Output {

    private List<String> columnNames;
    private Map<String, Integer> columnIndex;

    private String tableName;

    private List<String> primaryKeys;

    public PrimaryKeyOutput(List<String> columnNames,
                            String tableName,
                            List<String> primaryKeys,
                            String outputPath) {
        this.columnNames = columnNames;
        this.tableName = tableName;
        this.primaryKeys = primaryKeys;
        this.outputPath = outputPath;
    }

    @Override
    public void writeToFile() {
        try {
            openBufferedWriter();
            printTablesInfo();
            printColumnsInfo();
            printResultsInfo();
            closeBufferedWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printTablesInfo() throws IOException {
        bufferedWriter.write(tablesTitle);
        bufferedWriter.newLine();
        bufferedWriter.write(tableName+"\t"+1);
        bufferedWriter.newLine();
    }

    @Override
    public void printColumnsInfo() throws IOException {
        bufferedWriter.write(columnsTitle);
        bufferedWriter.newLine();
        columnIndex = new HashMap<>();
        int count = 1;
        for (String column : columnNames) {
            columnIndex.putIfAbsent(column, count);
            bufferedWriter.write("1.column"+count+"\t"+count);
            bufferedWriter.newLine();
            count++;
        }
    }

    @Override
    public void printResultsInfo() throws IOException {
        bufferedWriter.write(resultsTitle);
        bufferedWriter.newLine();
        String pkline = "";
        for (String column : primaryKeys) {
            pkline += columnIndex.get(column)+",";
        }
        bufferedWriter.write(pkline.substring(0,pkline.length()-1));
        bufferedWriter.newLine();
    }
}

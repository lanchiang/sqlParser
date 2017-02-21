package output;

import statement.AlterTableStatement;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lan.Jiang on 2/19/2017.
 */
public class ForeignKeyOutput extends Output {

    private Map<String, Map<String, Integer>> schema;

    private Map<String, Integer> tableIndex;

    private Map<String, Integer> columnIndex;

    private List<AlterTableStatement.ForeignKeyPair> foreignKeys;

    public ForeignKeyOutput(Map<String, Map<String, Integer>> schema,
                            List<AlterTableStatement.ForeignKeyPair> foreignKeys,
                            String outputPath) {
        this.schema = schema;
        this.foreignKeys = foreignKeys;
        this.outputPath = outputPath;
    }

    @Override
    public void printTablesInfo() throws IOException {
        bufferedWriter.write(tablesTitle);
        bufferedWriter.newLine();
        tableIndex = new HashMap<>();
        int count = 1;
        for (String tableName : schema.keySet()) {
            tableIndex.putIfAbsent(tableName, count);
            bufferedWriter.write(tableName+"\t"+count++);
            bufferedWriter.newLine();
        }
    }

    @Override
    public void printColumnsInfo() throws IOException {
        bufferedWriter.write(columnsTitle);
        bufferedWriter.newLine();
        int count = 1;
        columnIndex = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : schema.entrySet()) {
            String tableName = entry.getKey();
            int countWithinTable = 1;
            for (String column : entry.getValue().keySet()) {
                bufferedWriter.write(tableIndex.get(tableName)+".column"+countWithinTable+"\t"+count);
                bufferedWriter.newLine();

                columnIndex.put(tableName+"."+column, count);
                countWithinTable++;
                count++;

            }
        }
    }

    @Override
    public void printResultsInfo() throws IOException {
        bufferedWriter.write(resultsTitle);
        bufferedWriter.newLine();
        for (AlterTableStatement.ForeignKeyPair foreignKeyPair : foreignKeys) {
            String depColumn = foreignKeyPair.getDependentColumn();
            String refColumn = foreignKeyPair.getReferencedColumn();

            bufferedWriter.write(getIndexOfColumn(depColumn)+"[="+getIndexOfColumn(refColumn));
            bufferedWriter.newLine();
        }
    }

    public int getIndexOfColumn(String tableColumn) {
        return columnIndex.get(tableColumn);
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
}

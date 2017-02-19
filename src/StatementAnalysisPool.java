import statement.AlterTableStatement;
import statement.CreateTableStatement;
import statement.Statement;
import statement.StatementRecognizer;

import java.io.*;
import java.util.*;

/**
 * Created by Lan.Jiang on 2/19/2017.
 */
public class StatementAnalysisPool {

    private String inputFilePath;

    private String inputLines = "";

    private List<CreateTableStatement> createTableStatementList;

    private List<AlterTableStatement> alterTableStatementList;

    /**
     * Stores the schema including the table name, column names and their indexes
     */
    private Map<String, Map<String, Integer>> schema;

    public StatementAnalysisPool(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    private void loadFile() {
        if (inputFilePath == null) {
            throw new NullPointerException("Input file path is not appointed!");
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                inputLines += line;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processFile() {
        loadFile();
        String[] statements = inputLines.split(";");
        Arrays.stream(statements).forEach(statement -> {
            processOneStatement(statement);
        });
    }

    private void processOneStatement(String statement) {
        StatementRecognizer statementRecognizer = new StatementRecognizer();
        Statement sta = statementRecognizer.recognizeTheStatementFunction(statement);
        if (sta instanceof CreateTableStatement) {
            CreateTableStatement createTableStatement = ((CreateTableStatement) sta);
            createTableStatement.analyzeStatement();
            if (createTableStatementList == null) {
                createTableStatementList = new LinkedList<>();
            }
            addTableInfoToSchema(createTableStatement);
            createTableStatementList.add(createTableStatement);
        } else if (sta instanceof AlterTableStatement) {
            AlterTableStatement alterTableStatement = ((AlterTableStatement) sta);
            alterTableStatement.analyzeStatement();
            if (alterTableStatementList == null) {
                alterTableStatementList = new LinkedList<>();
            }
            alterTableStatementList.add(alterTableStatement);
        }
    }

    private void addTableInfoToSchema(CreateTableStatement createTableStatement) {
        String tableName = createTableStatement.getTableName();
        if (schema == null) {
            schema = new HashMap<>();
        }
        if (!schema.containsKey(tableName)) {
            schema.put(tableName, new HashMap<>());
        }
        int count = 1;
        for (String columnName : createTableStatement.getColumnNames()) {
            schema.get(tableName).putIfAbsent(columnName, count++);
        }
    }

    public Map<String, Map<String, Integer>> getSchema() {
        return schema;
    }

    public List<CreateTableStatement> getCreateTableStatementList() {
        return createTableStatementList;
    }

    public List<AlterTableStatement> getAlterTableStatementList() {
        return alterTableStatementList;
    }
}

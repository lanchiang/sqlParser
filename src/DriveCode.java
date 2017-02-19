import output.ForeignKeyOutput;
import output.PrimaryKeyOutput;
import statement.AlterTableStatement;
import statement.CreateTableStatement;
import statement.Statement;
import statement.StatementRecognizer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lan Jiang
 * @date 26/01/2017
 */
public class DriveCode {
    public static void main(String[] args) throws IOException {

//        String inputFilePath = "createImdb-withFKs.sql";
        String inputFilePath = "DB2_tables.sql";
        StatementAnalysisPool statementAnalysisPool = new StatementAnalysisPool(inputFilePath);
        statementAnalysisPool.processFile();

        List<CreateTableStatement> createTableStatementList = statementAnalysisPool.getCreateTableStatementList();
        for (CreateTableStatement createTableStatement : createTableStatementList) {
            String tableName = createTableStatement.getTableName();
            PrimaryKeyOutput primaryKeyOutput = new PrimaryKeyOutput(createTableStatement.getColumnNames(),
                    tableName, createTableStatement.getPrimaryKey(), tableName+"_primaryKey.csv");
            primaryKeyOutput.writeToFile();
        }
        List<AlterTableStatement> alterTableStatementList = statementAnalysisPool.getAlterTableStatementList();
        List<AlterTableStatement.ForeignKeyPair> foreignKeyPairs = alterTableStatementList.stream()
                .map(AlterTableStatement::getForeignKeyPair).collect(Collectors.toList());
        for (AlterTableStatement alterTableStatement : alterTableStatementList) {
            ForeignKeyOutput foreignKeyOutput = new ForeignKeyOutput(statementAnalysisPool.getSchema(),
                    foreignKeyPairs, inputFilePath+"_foreignKey.csv");
            foreignKeyOutput.writeToFile();
        }
    }
}

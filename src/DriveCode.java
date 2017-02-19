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

//        String statement = "CREATE TABLE IMDB.CINEMATOGRAPHERS ( \n" +
//                "       ID INTEGER NOT NULL\n" +
//                "     , NAME VARCHAR(255)\n" +
//                "     , MOVIE VARCHAR(255)\n" +
//                "     , PRIMARY KEY (ID)\n" +
//                ");";

        String inputFilePath = "createImdb-withFKs.sql";
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
//        SqlFileReceiver srf = new SqlFileReceiver();
////        File[] files = new File("/Users/Fuga/Documents/HPI/musicbrainz-server/createTable").listFiles();
////        for (File file : files) {
////            srf.receiveFile(file);
////            SqlFileParser sfp = new SqlFileParser();
////            sfp.parserFile(srf);
////        }
//
////        files = new File("/Users/Fuga/Documents/HPI/musicbrainz-server/createFK").listFiles();
////        for (File file : files) {
////            srf.receiveFile(file);
////            SqlFileParser sfp = new SqlFileParser();
////            sfp.parserFile(srf);
////        }
//
//        BufferedReader br = new BufferedReader(new FileReader("table_columns.txt"));
//        String line;
//        Map<String, Map<String, String>> table_columns = new HashMap<>();
//        while ((line=br.readLine())!=null) {
//            String[] info = line.split("\t");
//            table_columns.putIfAbsent(info[0], new HashMap<>());
//            for (String str : info[1].substring(1, info[1].length()-1).split(",")) {
//                String[] split = str.split(":");
//                table_columns.get(info[0]).putIfAbsent(split[0],split[1]);
//            }
//        }
//        br.close();
//        br = new BufferedReader(new FileReader("foreignKeyConstraints.txt"));
//        BufferedWriter bw = new BufferedWriter(new FileWriter("FKC.txt"));
//        Map<String, Set<String>> fk = new HashMap<>();
//        while ((line=br.readLine())!=null) {
//            String[] info = line.split("\t");
//            String ref = info[0];
//            String dep = info[1];
//            String refTable = ref.split(":")[0];
//            String refCol = ref.split(":")[1];
//            String depTable = dep.split(":")[0];
//            String depCol = dep.split(":")[1];
//            String fkRef = "["+refTable+"."+table_columns.get(refTable).get(refCol)+"]";
//            fk.putIfAbsent(fkRef, new HashSet<>());
//            fk.get(fkRef).add("[" +depTable+"."+table_columns.get(depTable).get(depCol)+"]");
//        }
//        for (String ref : fk.keySet()) {
//            String dependency = "";
//            for (String dep : fk.get(ref)) {
//                dependency += dep+",";
//            }
//            dependency = dependency.substring(0, dependency.length()-1);
//            bw.write(ref+" c "+dependency);
//            bw.newLine();
//        }
//        bw.close();
//        br.close();
    }
}

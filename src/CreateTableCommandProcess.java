import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lan Jiang
 * @date 24/01/2017
 */
public class CreateTableCommandProcess extends CommandProcess {

    private Map<String, Map<String, String>> table_columns;

    public CreateTableCommandProcess() {
    }

    private static CreateTableCommandProcess _instance = null;

    public static CreateTableCommandProcess getInstance() {
        if (_instance==null) {
            _instance = new CreateTableCommandProcess();
        }
        return _instance;
    }

    @Override
    public void commandParse(String piece) {
        if (table_columns==null) {
            table_columns = new HashMap<>();
        }

        // extract the created table name
        Pattern pattern = Pattern.compile("CREATE TABLE .*\\s");
        Matcher matcher = pattern.matcher(piece);
        String tableName = "";
        while (matcher.find()) {
            tableName = matcher.group().split(" ")[2];
        }
        if (tableName.equals(""))
            return;
        if (search_path!=null) {
            tableName = search_path+"."+tableName;
        }
        table_columns.putIfAbsent(tableName, new HashMap<>());

        // extract the main body of the create table piece
        pattern = Pattern.compile("\\(.*\\)");
        matcher = pattern.matcher(piece);
        String innerInfo = "";
        while (matcher.find()) {
            innerInfo = matcher.group().trim();
            innerInfo = innerInfo.substring(1, innerInfo.length()-1);
        }

        // load all the columns of the table
        int count = 1;
        for (String column : innerInfo.split(",")) {
            column = column.trim();
            if (!LineJudge.isStartCheck(column)&&!LineJudge.isStartConstraint(column)) {
                column = column.replaceAll(" +", " ");
                table_columns.get(tableName).putIfAbsent(column.split(" ")[0], "column"+count++);
            }
        }
    }

    @Override
    public void writeToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("table_columns.txt", true));
            for (String tableName : table_columns.keySet()) {
                String columns = "{";
                for (String columnName : table_columns.get(tableName).keySet()) {
                    columns += columnName+":"+table_columns.get(tableName).get(columnName)+",";
                }
                columns = columns.substring(0,columns.length()-1)+"}";
                bw.write(tableName+"\t"+columns);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String line = "abc 13(2)";
        for (Byte b : line.getBytes()) {
            System.out.println((char)b.byteValue());
            if ((char)b.byteValue()=='(') {
                System.out.println("+++++++++++++");
            }
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lan Jiang
 * @date 24/01/2017
 */
public class CreateTableCommandProcess extends CommandProcess {

    public Map<String, Map<String, String>> getTable_columns() {
        return table_columns;
    }

    private Map<String, Map<String, String>> table_columns;

    @Override
    public void commandParse(String piece) {
        if (table_columns==null) {
            table_columns = new HashMap<>();
        }
        Pattern pattern = Pattern.compile("CREATE TABLE .*\\s");
        Matcher matcher = pattern.matcher(piece);
        String tableName = "";
        while (matcher.find()) {
            tableName = matcher.group().split(" ")[2];
        }
        if (tableName.equals(""))
            return;
        table_columns.putIfAbsent(tableName, new HashMap<>());
        pattern = Pattern.compile("\\(.*\\)");
        matcher = pattern.matcher(piece);
        String innerInfo = "";
        while (matcher.find()) {
            innerInfo = matcher.group().trim();
            innerInfo = innerInfo.substring(1, innerInfo.length()-1);
        }

        int count = 0;
        for (String column : innerInfo.split(",")) {
            column = column.trim();
            if (!LineJudge.isStartCheck(column)&&!LineJudge.isStartConstraint(column)) {
                column = column.replaceAll(" +", " ");
                table_columns.get(tableName).putIfAbsent(column.split(" ")[0], "column"+count++);
            }
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

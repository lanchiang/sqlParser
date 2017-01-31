import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lan Jiang
 * @date 24/01/2017
 */
public class SqlFileParser {

    private SqlFileReceiver sqlFileReceiver;

    public void parserFile(SqlFileReceiver sqlFileReceiver) {
        this.sqlFileReceiver = sqlFileReceiver;

        if (sqlFileReceiver == null) return;

        BufferedReader sqlFileReader = sqlFileReceiver.openFile();
        if (sqlFileReader == null) return;

        String longsql = sqlFileReceiver.reconstructFile(sqlFileReader);
        SqlFileParser.sqlParse(longsql);
    }

    public static void sqlParse(String longsql) {
        String[] splittedByColon = longsql.split(";");

        String search_path = null;

//        CommandProcess cp = new CreateTableCommandProcess();
        CommandProcess cp = new AlterTableCommandProcess();
        for (String command : splittedByColon) {
            command = command.trim();

            if (LineJudge.isStartWithSetSearchPath(command)) {
                Pattern pattern = Pattern.compile("SET search_path = '(.*?)'");
                Matcher matcher = pattern.matcher(command);
                if (matcher.find()) {
                    search_path = matcher.group(1);
                }
            }

            if (LineJudge.isStartCreateTable(command)||LineJudge.isStartAlterTable(command)) {
                cp.setSearch_path(search_path);

                cp.commandParse(command);
            }
//            else if (LineJudge.isStartAlterTable(command)) {
//                cp.commandParse(command);
//            }
        }
        if (cp!=null) cp.writeToFile();
        return;
    }
}
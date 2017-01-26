import java.io.BufferedReader;

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

        CreateTableCommandProcess ctcp = null;
        AlterTableCommandProcess atcp = null;
        for (String command : splittedByColon) {
            if (LineJudge.isStartCreateTable(command)) {
                if (ctcp == null) {
                    ctcp = new CreateTableCommandProcess();
                }
                ctcp.commandParse(command);
            }
            else if (LineJudge.isStartAlterTable(command)) {
                if (atcp == null) {
                    atcp = new AlterTableCommandProcess();
                }
                atcp.commandParse(command);
            }
        }
        if (ctcp!=null) ctcp.writeToFile();
        if (atcp!=null) atcp.writeToFile();
        return;
    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lan Jiang
 * @date 24/01/2017
 */
public class SqlFileParser {

    private SqlFileReceiver sqlFileReceiver;

    public SqlFileParser() {
    }

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

        CommandProcess cp = null;
        for (String command : splittedByColon) {
            if (LineJudge.isStartCreateTable(command)) {
                if (cp == null) {
                    cp = new CreateTableCommandProcess();
                }
                cp.commandParse(command);
            }
            else if (LineJudge.isStartAlterTable(command)) {
                if (cp == null) {
                    cp = new AlterTableCommandProcess();
                }
                cp.commandParse(command);
            }
        }
        return;
    }

    public static void main(String[] args) {

        SqlFileReceiver srf = new SqlFileReceiver();
        srf.receiveFile(new File("/Users/Fuga/Documents/HPI/musicbrainz-server/admin/sql/CreateTables.sql"));

        SqlFileParser sfp = new SqlFileParser();
        sfp.parserFile(srf);

        srf.receiveFile(new File("/Users/Fuga/Documents/HPI/musicbrainz-server/admin/sql/CreateFKConstraints.sql"));
    }
}

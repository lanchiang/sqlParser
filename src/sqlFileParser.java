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
        CommandProcess.sqlParse(longsql);
    }

    public static void main(String[] args) {

//        List<String> matchList = new ArrayList<String>();
//        Pattern regex = Pattern.compile("\\((.*)\\)");
//        Matcher regexMatcher = regex.matcher("create table zeit (id INTEGER, CHECK (name != ''))");
//
//        while (regexMatcher.find()) {//Finds Matching Pattern in String
//            matchList.add(regexMatcher.group(0));//Fetching Group from String
//            matchList.add(regexMatcher.group(1));//Fetching Group from String
//        }
//
//        for(String str:matchList) {
//            System.out.println(str);
//        }

        SqlFileReceiver srf = new SqlFileReceiver();
        srf.receiveFile(new File("/Users/Fuga/Documents/HPI/musicbrainz-server/admin/sql/CreateTables.sql"));

        SqlFileParser sfp = new SqlFileParser();
        sfp.parserFile(srf);
    }
}

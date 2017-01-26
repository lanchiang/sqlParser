import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lan Jiang
 * @date 25/01/2017
 */
public class AlterTableCommandProcess extends CommandProcess {

    @Override
    public void commandParse(String piece) {
        String[] info = piece.trim().split(" ");
        String tableName = info[2]; // first "alter", second "table"

        // just for altering foreign key
        Pattern pattern = Pattern.compile("FOREIGN KEY \\(.*?\\)");
        Matcher matcher = pattern.matcher(piece);
        String fk = "";
        while (matcher.find()) {
            fk = matcher.group(0);
        }
        pattern = Pattern.compile("REFERENCES .*\\(.*?\\)");
        matcher = pattern.matcher(piece);
        String ref = "";
        while (matcher.find()) {
            ref = matcher.group();
        }
        fk = fk.substring(fk.indexOf("(")+1, fk.indexOf(")"));
        ref = ref.split(" ")[1];
        String refTableName = ref.substring(0, ref.indexOf("("));
        String refCol = ref.substring(ref.indexOf("(")+1, ref.indexOf(")"));
        return;
    }
}
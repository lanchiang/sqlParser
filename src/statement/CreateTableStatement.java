package statement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lan Jiang
 * @since 17/02/2017
 */
public class CreateTableStatement extends Statement implements StatementAnalysis {

    final private static Pattern createTableContentPattern = Pattern.compile("CREATE TABLE (.*?)\\s\\((.*)\\)");

    final private static Pattern primaryKeyPattern = Pattern.compile("PRIMARY KEY \\((.*?)\\)");

    private List<String> columnNames;

    public CreateTableStatement(String statement) {
        super(statement);
    }

    @Override
    public void analyzeStatement() {
        Matcher matcher = createTableContentPattern.matcher(statement);
        while (matcher.find()) {
            tableName = matcher.group(1);
            content = matcher.group(2);
        }
        if (content == null) {
            System.out.println("Fail to get the content of the statement!");
            return;
        }
        content = content.trim().toUpperCase();

        // extract primary keys
        matcher = primaryKeyPattern.matcher(content);
        String primaryKeys = null;
        while (matcher.find()) {
            primaryKeys = matcher.group(1);
        }
        if (primaryKeys == null) {
            System.out.println("Accessing primary keys fails.");
            return;
        }
        primaryKey = new ArrayList<>();
        for (String pk : primaryKeys.split(",")) {
            primaryKey.add(pk);
        }

        // extract column names
        matchColumnNames();
    }

    private void matchColumnNames() {
        columnNames = new ArrayList<>();
        Matcher matcher;
        for (String part : content.split(",")) {
            matcher = primaryKeyPattern.matcher(part);
            // if match, it means this part do not indicate a column.
            if (matcher.find()) {
                continue;
            }
            columnNames.add(part.split("\\s")[0]);
        }
    }
}

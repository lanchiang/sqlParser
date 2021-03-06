package statement;

import java.util.regex.Pattern;

/**
 * @author Lan Jiang
 * @since 18/02/2017
 */
public class StatementRecognizer {

    final private static Pattern createTablePattern = Pattern.compile("CREATE TABLE .*");

    final private static Pattern alterTablePattern = Pattern.compile("ALTER TABLE .*");

    final private static Pattern dropTablePattern = Pattern.compile("DROP TABLE .*");

    public Statement recognizeTheStatementFunction(String statement) {
        if (createTablePattern.matcher(statement).find()) {
            return new CreateTableStatement(statement);
        } else if (alterTablePattern.matcher(statement).find()) {
            return new AlterTableStatement(statement);
        } else if (dropTablePattern.matcher(statement).find()) {
            return null;
        }
        return null;
    }
}

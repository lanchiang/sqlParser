package statement;

import java.util.regex.Pattern;

/**
 * @author Lan Jiang
 * @since 18/02/2017
 */
public class StatementRecognizer {

    final private static Pattern createTablePattern = Pattern.compile("CREATE TABLE .*");

    public Statement recognizeTheStatementFunction(String statement) {
        if (createTablePattern.matcher(statement).find()) {
            return new CreateTableStatement(statement);
        }
        return null;
    }
}

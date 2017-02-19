package statement;

import java.util.List;

/**
 * This class is the base class for all the different statement classes.
 * @author Lan Jiang
 * @since 17/02/2017
 */
abstract public class Statement {

    protected String statement;

    protected String tableName;

    protected String content;

    protected List<String> primaryKey;

    public Statement(String statement) {
        this.statement = statement;

        trimStatement();
    }

    /**
     * remove all the \n from the statement.
     */
    public void trimStatement() {
        statement = statement.replace("\n", "");
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getPrimaryKey() {
        return primaryKey;
    }
}

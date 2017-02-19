package statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lan.Jiang on 2/19/2017.
 */
public class AlterTableStatement extends Statement implements StatementAnalysis {

    final private static Pattern alterTablePattern = Pattern.compile("ALTER TABLE (.*?)\\s(.*)");

    final private static Pattern addConstraintPattern = Pattern.compile("ADD CONSTRAINT (.*?)\\s\\((.*)\\)");

    final private static Pattern foreignKeyPattern = Pattern.compile("FOREIGN KEY \\((.*?)\\)");

    final private static Pattern referencedTableColumnPattern = Pattern.compile("REFERENCES (.*?)\\s\\((.*?)\\)");

    final private static Pattern referencedTableColumnNoRefColumnPattern = Pattern.compile("REFERENCES (.*)");

    private ForeignKeyPair foreignKeyPair;

    public AlterTableStatement(String statement) {
        super(statement);
    }

    @Override
    public void analyzeStatement() {
        Matcher matcher = alterTablePattern.matcher(statement);
        while (matcher.find()) {
            tableName = matcher.group(1);
            content = matcher.group(2);
        }
        if (content == null) {
            System.out.println("Fail to get the content of the statement!");
        }
        content = content.trim().toUpperCase();

        Matcher foreignKeyMatcher = foreignKeyPattern.matcher(content);
        Matcher referencedMatcher = referencedTableColumnPattern.matcher(content);
        Matcher referencedNoRefColumnMatcher = referencedTableColumnNoRefColumnPattern.matcher(content);
        boolean foreignMatcher = foreignKeyPattern.matcher(content).find();
        boolean referenceMatcher = referencedTableColumnPattern.matcher(content).find();
        // both match, which means the statement aims to add a foreign key.
        if (foreignKeyMatcher.find()) {
            String foreignKey = foreignKeyMatcher.group(1);
            String referencedTable = null;
            String referencedColumn = null;
            if (referencedMatcher.find()) {
            referencedTable = referencedMatcher.group(1);
            referencedColumn = referencedMatcher.group(2);
            } else if (referencedNoRefColumnMatcher.find()) {
                referencedTable = referencedNoRefColumnMatcher.group(1);
                String referencedCollumn = foreignKey;
            }
            foreignKeyPair = new ForeignKeyPair(tableName+"."+foreignKey,
                    referencedTable+"."+referencedColumn);
        }
    }

    public ForeignKeyPair getForeignKeyPair() {
        return foreignKeyPair;
    }

    public class ForeignKeyPair {
        String dependentColumn;
        String referencedColumn;

        public ForeignKeyPair(String dependentColumn, String referencedColumn) {
            this.dependentColumn = dependentColumn;
            this.referencedColumn = referencedColumn;
        }

        public String getDependentColumn() {
            return dependentColumn;
        }

        public String getReferencedColumn() {
            return referencedColumn;
        }
    }
}

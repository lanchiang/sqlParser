package statement;

/**
 * This interface defines the function to analyze a single sql statement. Any class implements the interface can logically
 * process the statement analysis.
 * @author Lan Jiang
 * @since 17/02/2017
 */
public interface StatementAnalysis {

    void analyzeStatement();
}

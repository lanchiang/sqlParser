package test.output;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import output.PrimaryKeyOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * PrimaryKeyOutput Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 19, 2017</pre>
 */
public class PrimaryKeyOutputTest {

    private PrimaryKeyOutput primaryKeyOutput;

    @Before
    public void before() throws Exception {
        List<String> columnsName = new ArrayList<>();
        columnsName.add("A");
        columnsName.add("B");
        columnsName.add("C");
        columnsName.add("D");
        columnsName.add("E");
        String tableName = "customer.csv";
        List<String> primaryKeys = new ArrayList<>();
        primaryKeys.add("A");
        primaryKeys.add("C");
        primaryKeys.add("D");
        primaryKeyOutput = new PrimaryKeyOutput(columnsName, tableName, primaryKeys, "primaryKeys.csv");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: writeToFile()
     */
    @Test
    public void testWriteToFile() throws Exception {
        primaryKeyOutput.writeToFile();
    }
} 

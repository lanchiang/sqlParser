import java.io.BufferedReader;
import java.util.Map;
import java.util.Set;

/**
 * @author Lan Jiang
 * @date 24/01/2017
 */
abstract public class CommandProcess {

    public static void sqlParse(String longsql) {
        String[] splittedByColon = longsql.split(";");

        CommandProcess cp = null;
        for (String command : splittedByColon) {
            if (LineJudge.isStartCreateTable(command)) {
                if (cp == null) {
                    cp = new CreateTableCommandProcess();
                }
                String[] spilttedByComma = command.split(",");
                cp.commandParse(spilttedByComma);
            }
        }
        return;
    }

    abstract public void commandParse(String[] spilttedByComma);
}
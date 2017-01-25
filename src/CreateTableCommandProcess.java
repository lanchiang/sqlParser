import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Lan Jiang
 * @date 24/01/2017
 */
public class CreateTableCommandProcess extends CommandProcess {

    private Map<String, Set<String>> table_columns;

    @Override
    public void commandParse(String[] spilttedByComma) {
        if (table_columns==null) {
            table_columns = new HashMap<>();
        }
        String[] info = spilttedByComma[0].trim().split(" ");
        String tableName = info[2]; // first "CREATE", second "TABLE"
        table_columns.putIfAbsent(tableName, new HashSet<>());

        int count = 0;
        for (String piece : spilttedByComma) {
            piece = piece.trim();
            if (!LineJudge.isStartCheck(piece)&&!LineJudge.isStartConstraint(piece)) {
                table_columns.get(tableName).add("column"+count++);
            }
        }
    }

    private void matchBrackets(BufferedReader bufferedReader, String firstLine) {
        char c;
        Stack<Character> brackets = new Stack<>();
        for (Byte b : firstLine.getBytes()) {
            c = (char)b.byteValue();
            matchBrackets(brackets, c);
        }
        if (brackets.size()==0) return;
        String line;
        try {
            while ((line=bufferedReader.readLine())!=null) {
                for (Byte b : line.getBytes()) {
                    c = (char)b.byteValue();
                    matchBrackets(brackets, c);
                }
                if (brackets.size()==0) return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void matchBrackets(Stack<Character> brackets, char c) {
        if (c=='(') {
            brackets.push(c);
        }
        if (c==')') {
            brackets.pop();
        }
    }

    public static void main(String[] args) {
        String line = "abc 13(2)";
        for (Byte b : line.getBytes()) {
            System.out.println((char)b.byteValue());
            if ((char)b.byteValue()=='(') {
                System.out.println("+++++++++++++");
            }
        }
    }
}

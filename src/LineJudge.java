/**
 * @author Lan Jiang
 * @date 24/01/2017
 */
public class LineJudge {

    public static boolean isStartCreateTable(String line) {
        return line.toUpperCase().contains("CREATE TABLE")?true:false;
    }

    public static boolean isEndCreateTable(String line) {
        return line.equals(");")?true:false;
    }

    public static boolean isStartCheck(String line) {
        line = line.trim();
        return line.split(" ")[0].toUpperCase().equals("CHECK")?true:false;
//        return line.toUpperCase().contains("CHECK")?true:false;
    }

    public static boolean isStartConstraint(String line) {
        return line.split(" ")[0].toUpperCase().equals("CONSTRAINT")?true:false;
//        return line.toUpperCase().contains("CONSTRAINT")?true:false;
    }

    public static boolean containAnnotation(String line) {
        return line.contains("--")?true:false;
    }
}

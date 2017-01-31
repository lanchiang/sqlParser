/**
 * @author Lan Jiang
 * @date 24/01/2017
 */
public class LineJudge {

    public static boolean isStartCreateTable(String line) {
        return line.toUpperCase().contains("CREATE TABLE")?true:false;
    }

    public static boolean isStartCheck(String line) {
        line = line.trim();
        return line.split(" ")[0].toUpperCase().equals("CHECK")?true:false;
    }

    public static boolean isStartConstraint(String line) {
        line = line.trim();
        return line.split(" ")[0].toUpperCase().equals("CONSTRAINT")?true:false;
    }

    public static boolean containAnnotation(String line) {
        return line.contains("--")?true:false;
    }

    public static boolean isStartAlterTable(String line) {
        return line.toUpperCase().contains("ALTER TABLE")?true:false;
    }

    public static boolean isStartWithSet(String line) {
        return line.toLowerCase().startsWith("\\set")?true:false;
    }

    public static boolean isStartWithSetSearchPath(String line) {
        return line.toLowerCase().startsWith("set search_path")?true:false;
    }
}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lan Jiang
 * @date 25/01/2017
 */
public class AlterTableCommandProcess extends CommandProcess {

    List<ForeignKeyConstraint> foreignKeyConstraints;

    public List<ForeignKeyConstraint> getForeignKeyConstraints() {
        return foreignKeyConstraints;
    }

    @Override
    public void commandParse(String piece) {
        if (foreignKeyConstraints == null) {
            foreignKeyConstraints = new ArrayList<>();
        }

        String[] info = piece.trim().split(" ");
        String tableName = info[2]; // first "alter", second "table"
        if (search_path!=null) {
            tableName = search_path+"."+tableName;
        }

        // just for altering foreign key
        Pattern pattern = Pattern.compile("FOREIGN KEY \\(.*?\\)");
        Matcher matcher = pattern.matcher(piece);
        String fk = "";
        while (matcher.find()) {
            fk = matcher.group(0);
        }
        if (fk == "") return;
        pattern = Pattern.compile("REFERENCES .*\\(.*?\\)");
        matcher = pattern.matcher(piece);
        String ref = "";
        while (matcher.find()) {
            ref = matcher.group();
        }
        if (ref == "") return;
        fk = fk.substring(fk.indexOf("(")+1, fk.indexOf(")"));
        ref = ref.split(" ")[1];
        String refTableName = ref.substring(0, ref.indexOf("("));
        if (refTableName.startsWith("musicbrainz")) {
            refTableName = refTableName.substring(refTableName.indexOf(".")+1,refTableName.length());
        }
        String refCol = ref.substring(ref.indexOf("(")+1, ref.indexOf(")"));
        foreignKeyConstraints.add(new ForeignKeyConstraint(tableName,fk,refTableName,refCol));
        return;
    }

    @Override
    public void writeToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("foreignKeyConstraints.txt", true));
            for (ForeignKeyConstraint fkc : foreignKeyConstraints) {
                bw.write(fkc.getRefTable()+":"+fkc.getRefColumn()+"\t"+fkc.getDepTable()+":"+fkc.getDepColumn());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ForeignKeyConstraint {
        String refTable;

        String refColumn;

        String depTable;

        String depColumn;

        public ForeignKeyConstraint(String refTable, String refColumn, String depTable, String depColumn) {
            this.refTable = refTable;
            this.refColumn = refColumn;
            this.depTable = depTable;
            this.depColumn = depColumn;
        }

        public String getRefTable() {
            return refTable;
        }

        public String getRefColumn() {
            return refColumn;
        }

        public String getDepTable() {
            return depTable;
        }

        public String getDepColumn() {
            return depColumn;
        }
    }
}
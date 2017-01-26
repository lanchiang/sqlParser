import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Lan Jiang
 * @date 26/01/2017
 */
public class DriveCode {
    public static void main(String[] args) throws IOException {

        SqlFileReceiver srf = new SqlFileReceiver();
        srf.receiveFile(new File("/Users/Fuga/Documents/HPI/musicbrainz-server/admin/sql/CreateTables.sql"));

        SqlFileParser sfp = new SqlFileParser();
        sfp.parserFile(srf);

        srf.receiveFile(new File("/Users/Fuga/Documents/HPI/musicbrainz-server/admin/sql/CreateFKConstraints.sql"));
        sfp.parserFile(srf);

        BufferedReader br = new BufferedReader(new FileReader("table_columns.txt"));
        String line;
        Map<String, Map<String, String>> table_columns = new HashMap<>();
        while ((line=br.readLine())!=null) {
            String[] info = line.split("\t");
            table_columns.putIfAbsent(info[0], new HashMap<>());
            for (String str : info[1].substring(1, info[1].length()-1).split(",")) {
                String[] split = str.split(":");
                table_columns.get(info[0]).putIfAbsent(split[0],split[1]);
            }
        }
        br.close();
        br = new BufferedReader(new FileReader("foreignKeyConstraints.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("FKC.txt"));
        Map<String, Set<String>> fk = new HashMap<>();
        while ((line=br.readLine())!=null) {
            String[] info = line.split("\t");
            String ref = info[0];
            String dep = info[1];
            String refTable = ref.split(":")[0];
            String refCol = ref.split(":")[1];
            String depTable = dep.split(":")[0];
            String depCol = dep.split(":")[1];
            String fkRef = "["+refTable+"."+table_columns.get(refTable).get(refCol)+"]";
            fk.putIfAbsent(fkRef, new HashSet<>());
            fk.get(fkRef).add("[" +depTable+"."+table_columns.get(depTable).get(depCol)+"]");
        }
        for (String ref : fk.keySet()) {
            String dependency = "";
            for (String dep : fk.get(ref)) {
                dependency += dep+",";
            }
            dependency = dependency.substring(0, dependency.length()-1);
            bw.write(ref+" c "+dependency);
            bw.newLine();
        }
        bw.close();
        br.close();
    }
}

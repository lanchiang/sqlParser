import java.io.*;

/**
 * @author Lan Jiang
 */
public class SqlFileReceiver {

    private File sqlFile;

    public SqlFileReceiver() {

    }

    public void receiveFile(File file) {
        this.sqlFile = file;

        if (!sqlFile.exists()) {
            return;
        }
    }

    public BufferedReader openFile() {
        try {
            BufferedReader sqlFileReader = new BufferedReader(new FileReader(sqlFile));
            return sqlFileReader;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String reconstructFile(BufferedReader sqlFileReader) {
        String longsql = "";
        String line;
        try {
            while ((line=sqlFileReader.readLine())!=null) {
                line = trimWithOneSpace(line);
                if (line.contains("application")) {
                    int stop = 0;
                }
                if (LineJudge.containAnnotation(line)) {
                    line = removeAnnotation(line);
                }
                longsql += line;
            }
            sqlFileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return longsql;
    }

    private String removeAnnotation(String line) {
        int pos = line.indexOf("--");
        return line.substring(0, pos);
    }

    private String trimWithOneSpace(String line) {
        line = line.trim() + " ";
        return line;
    }
}

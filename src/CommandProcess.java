/**
 * @author Lan Jiang
 * @date 24/01/2017
 */
abstract public class CommandProcess {

    protected String search_path =null;

    public String getSearch_path() {
        return search_path;
    }

    public void setSearch_path(String search_path) {
        this.search_path = search_path;
    }


    abstract public void commandParse(String piece);

    abstract public void writeToFile();
}
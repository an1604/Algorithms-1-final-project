package test.Table;

import java.util.List;

public interface Table {
    /**The Table interface.
     * This interface holds essential methods that have given for 2 Table classes to generate their tables.
     **/
    List<String[]> getRows();

    public void printTable();
    public int[] calculateColumnWidths();

    void generate_table();

    void parseRowString(String input);


}

package test.Table;

import java.util.List;

public interface Table {
    List<String[]> getRows();

    public void printTable();
    public int[] calculateColumnWidths();

    void generate_table();

    void parseRowString(String input);

    String getData();
}

package test.Main;

import java.util.ArrayList;
import java.util.List;

public class TerminalTable {
    private String[][] data;
    private String[] headers;
    private List<String[]> rows;

    public TerminalTable(String[] columnNames, int num_rows) {
        this.headers = generateHeaders(columnNames);
        this.rows = new ArrayList<>();
    }

    public void printTable() {
        int[] columnWidths = calculateColumnWidths();

        // Print headers
        for (int i = 0; i < headers.length; i++) {
            System.out.format("%-" + columnWidths[i] + "s", (headers[i] != null) ? headers[i] : "");
            System.out.print(" | ");
        }
        System.out.println();

        // Print separator line
        for (int width : columnWidths) {
            for (int j = 0; j < width; j++) {
                System.out.print("-");
            }
            System.out.print("-+-");
        }
        System.out.println();

        // Print data
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                System.out.format("%-" + columnWidths[i] + "s", (row[i] != null) ? row[i] : "");
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

    private int[] calculateColumnWidths() {
        int numColumns = headers.length;
        int[] columnWidths = new int[numColumns];

        // Initialize with header lengths
        for (int i = 0; i < numColumns; i++) {
            columnWidths[i] = headers[i].length();
        }

        // Update with data lengths
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                try {
                    if (row[i].length() > columnWidths[i]) {
                        columnWidths[i] = row[i].length();
                    }
                } catch (NullPointerException e){e.printStackTrace();}
            }
        }

        return columnWidths;
    }

    public void generate_table(){
        this.data = generateTestData(rows.size(), headers.length);
    }

    private String[] generateHeaders(String[] columnNames) {
        String[] generatedHeaders = new String[columnNames.length + 1];
        generatedHeaders[0] = "Params";
        for (int i = 0; i < columnNames.length; i++) {
            generatedHeaders[i + 1] = columnNames[i];
        }
        return generatedHeaders;
    }

    private String[][] generateTestData(int num_rows, int num_columns) {
        String[][] testData = new String[num_rows * 2][num_columns + 1];
        for (int i = 0; i < num_rows; i++) {
            int rowIndex = i * 2;
            testData[rowIndex][0] = "\n(" +(i+1)+")"+ "\nRun Time";
            testData[rowIndex + 1][0] = "Vertices ";

            // Initialize the other columns with values
            for (int j = 1; j <= num_columns; j++) {
                if (j < rows.get(i).length) {
                    testData[rowIndex][j] = rows.get(i)[j - 1];
                    testData[rowIndex + 1][j] = rows.get(i)[j - 1];
                }
            }
        }
        return testData;
    }

    public void parseRowString(String rowString) {
        System.out.println(rowString);
        int j =0;
        String[] result = new String[headers.length-1];
        // Split the rowString into lines
        String[] lines = rowString.split("--------------------------");
        for(int i=0 ; i<lines.length-1 ;i++ ){
            String row[] = lines[i].split("\n");
            if(!row[1].contains("name"))
                result[i] = row[1] + "\n" + row[2];
            else
                result[i] = row[2] + "\n" + row[3];
        }
        rows.add(result);
    }


    public static void main(String[] args) {
        String[] names = {"BFS", "A*"};
        TerminalTable table = new TerminalTable(names, 5);
        table.printTable();
    }
}

package test.Main;

public class TerminalTable {
    private String[][] data;
    private String[] headers;

    public TerminalTable(String[] headers, String[][] data) {
        this.headers = headers;
        this.data = data;
    }

    public void printTable() {
        int[] columnWidths = calculateColumnWidths();

        // Print headers
        for (int i = 0; i < headers.length; i++) {
            System.out.format("%-" + columnWidths[i] + "s", headers[i]);
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
                System.out.format("%-" + columnWidths[i] + "s", row[i]);
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
                if (row[i].length() > columnWidths[i]) {
                    columnWidths[i] = row[i].length();
                }
            }
        }

        return columnWidths;
    }
}

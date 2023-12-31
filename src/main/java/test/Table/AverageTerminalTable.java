package test.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AverageTerminalTable implements Table {
    /**The AverageTerminalTable class.
     * This class creates and prints a specific table for the average task:
     * In this task we need to sample 50 different graphs (puzzle representations) in size 15 and 24 each,
     * and generate a table to show the average run time and developed vertices.
     **/
    private String[] headers;
    private StringBuilder data;

    private List<String[]> rows;

    private String initial_state;
    public AverageTerminalTable(String[] columnNames, int num_rows) {
        this.headers = new String[]{"Params: ", "Run time ", "Vertices"};
        this.rows = new ArrayList<>();
        this.data = new StringBuilder();
    }
    @Override
    public List<String[]> getRows() {
        return rows;
    }

    @Override
    public void printTable() {
        System.out.println("Initial state : \n " + this.initial_state);

        System.out.println("Averages : " );


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

        // Print rows
        for (String[] row : rows) {
            // Print Params column
            System.out.format("%-" + columnWidths[0] + "s", row[0]);
            System.out.print(" | ");

            // Print Run Time and Vertices
            System.out.format("%-" + columnWidths[1] + "s", row[1]);
            System.out.print(" | ");
            System.out.format("%-" + columnWidths[2] + "s", row[2].split(" ")[1]);
            System.out.print(" | ");

            System.out.println();
        }
    }

    @Override
    public int[] calculateColumnWidths() {
        int numColumns = headers.length;
        int[] columnWidths = new int[numColumns];

        // Initialize with header lengths
        for (int i = 0; i < numColumns; i++) {
            columnWidths[i] = headers[i].length();
        }

        // Update with row lengths
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                try {
                    if (row[i].length() > columnWidths[i]) {
                        columnWidths[i] = row[i].length();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }

        return columnWidths;
    }

    @Override
    public void generate_table(){
        filter_empty_strings_from_rows();
    }


    @Override
    public void parseRowString(String input) {
        // Define patterns for the start and end of the desired sections
        Pattern startPattern = Pattern.compile("Alg name :");
        Pattern endPattern = Pattern.compile(" Vertices Average: \\d+");

        Matcher matcher = startPattern.matcher(input);
        boolean foundStart = matcher.find();

        while (foundStart) {
            int startIdx = matcher.end();
            matcher.usePattern(endPattern);
            boolean foundEnd = matcher.find(startIdx);

            if (foundEnd) {
                int endIdx = matcher.end();
                String section = input.substring(startIdx, endIdx);
                String[] lineArray = section.split("\n");

                String verticesAveragePattern = "Vertices Average: (\\d+)";
                Pattern verticesPattern = Pattern.compile(verticesAveragePattern);
                Matcher verticesMatcher = verticesPattern.matcher(section);
                if (verticesMatcher.find()) {
                    String verticesAverage = verticesMatcher.group(1);
                    lineArray[lineArray.length - 1] += " " + verticesAverage;
                }

                rows.add(lineArray);
                matcher.usePattern(startPattern);
                foundStart = matcher.find(endIdx);
            } else {
                break;
            }
        }
    }


    private void filter_empty_strings_from_rows() {
        List<String[]> filteredRows = new ArrayList<>();
        for (String[] row : rows) {
            List<String> newRow = new ArrayList<>();
            for (String word : row) {
                if (!word.equals("") && !word.equals(" ") && !word.contains("-----") && !word.contains("Params")) {
                    newRow.add(word);
                }
            }
            filteredRows.add(newRow.toArray(new String[0]));
        }
        List<String[]> filter_rows_new = new ArrayList<>();
        for (int i=0 ; i<filteredRows.size();i++){
            List<String> newRow = new ArrayList<>();
            for (int j = 0; j < filteredRows.get(i).length; j++) {

                if(filteredRows.get(i)[j].contains("Initial")){
                    int k =1;
                    StringBuilder sb = new StringBuilder();
                    while (!filteredRows.get(i)[j+k].contains("Runtime")){
                        sb.append(filteredRows.get(i)[j+k] + "\n");
                        k++;
                    }
                    this.initial_state = sb.toString();
                    j+=k-1;
                }
                else if(filteredRows.get(i)[j].contains("Runtime")){
                    String[] arr= filteredRows.get(i)[j].split(":");
                    newRow.add(arr[1]);
                }
                else if(filteredRows.get(i)[j].contains("Vertices")){
                    String[] arr= filteredRows.get(i)[j].split(":");
                    newRow.add(arr[1]);
                }
                else
                    newRow.add(filteredRows.get(i)[j]);
            }
            filter_rows_new.add(newRow.toArray(new String[0]));
        }
        rows =filter_rows_new;
        //Generating the data string
        generate_data();
    }

    private void generate_data() {
        for(String[] row : rows){
            this.data.append( row[0]+"\n" );
            this.data.append( row[1]+"\n");
            this.data.append( row[2]+"\n");
            this.data.append("--------------");
        }
    }


    public static void main(String[] args) {
        String[] names = {"BFS", "A*"};
        AverageTerminalTable table = new AverageTerminalTable(names, 5);
        table.generate_table();
        table.printTable();
    }
}

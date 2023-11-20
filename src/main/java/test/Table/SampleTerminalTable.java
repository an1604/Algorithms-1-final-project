package test.Table;

import test.Main.RunTimeTest;
import test.Results.Results;
import test.agorithms.Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SampleTerminalTable implements Table {
    /**The SampleTerminalTable class.
     * This class creates and prints a specific table for the sample task:
     * We created a sample up to 10 puzzles, in each sample we need to sample the results and show them in the table.
     **/

    private List<String> headers;
    private List<String[]> rows;
    private Map<String,List<Results>> map; // Map for each algorithm with his results

    public SampleTerminalTable(List<RunTimeTest> samples) {
        this.map = new HashMap<>();
        this.headers = getHeaders(samples);
        this.rows = new ArrayList<>();
        // Generate the rows
        for (RunTimeTest row : samples) {
            String[] algs = row.toString().split("\n--------------------------\n ");
            for(String alg : algs) {
                parseRowString(alg);
            }
        }
    }

    // This method returns the headers from a single table
    private List<String> getHeaders(List<RunTimeTest> samples) {
        List<String> names = new ArrayList<>();
        names.add("Sample");
        for (Algorithms a : samples.get(0).getAlgorithms()) {
            names.add(a.Name());
            this.map.put(a.Name(),new ArrayList<>());
        }
        return names;
    }

    @Override
    public List<String[]> getRows() {
        return rows;
    }

    @Override
    public int[] calculateColumnWidths() {
        int numColumns = headers.size();
        int[] columnWidths = new int[numColumns];

        // Initialize with header lengths
        for (int i = 0; i < numColumns; i++) {
            columnWidths[i] = headers.get(i).length();
        }

        // Update with row lengths
        for (String[] rowData : rows) {
            for (int i = 0; i < numColumns; i++) {
                try {
                    if (rowData[i].length() > columnWidths[i]) {
                        columnWidths[i] = rowData[i].length();
                    }
                } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {}
            }
        }

        return columnWidths;
    }

    @Override
    public void generate_table() {
        // TODO: Implement if needed
    }

    @Override
    public void printTable() {
        int[] columnWidths = calculateColumnWidths();

        // Print headers
        for (int i = 0; i < headers.size(); i++) {
            System.out.format("%-" + (columnWidths[i] + 2) + "s", headers.get(i)); // Add 2 for additional space
            System.out.print(" | ");
        }
        System.out.println();

        // Print separator line
        for (int width : columnWidths) {
            for (int j = 0; j < width + 2; j++) { // Add 2 for additional space
                System.out.print("-");
            }
            System.out.print("-+-");
        }
        System.out.println();

        // Print rows
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            System.out.format("%-" + columnWidths[0] + "s", rowIndex);
            System.out.print(" | ");

            String[] rowData = get_row_from_map(rowIndex);
            //If he null we finish the printing
            if(rowData ==null)
                break;

            for (int i = 0; i < rowData.length; i++) {
                System.out.format("%-" + (columnWidths[i + 1] + 2) + "s", (rowData[i] != null) ? rowData[i] : ""); // Add 2 for additional space
                System.out.print(" | ");
            }

            System.out.println();

            // Print separator line
            for (int width : columnWidths) {
                for (int j = 0; j < width + 2; j++) { // Add 2 for additional space
                    System.out.print("-");
                }
                System.out.print("-+-");
            }
            System.out.println();
        }
    }


    private String[] get_row_from_map(int rowIndex) {
        String[] row = new String[this.map.keySet().size()];
        try {
            int i = 0;
            //From each key we get 1 element and append him to the row
            for (String key : map.keySet()) {
                //We want the results to be in the order of the headers
                row[i] = map.get(headers.get(i + 1)).get(rowIndex).getData();
                i++;
            }
        }catch (IndexOutOfBoundsException e){
            row=null;
        }
        return row;
    }

    @Override
    public void parseRowString(String input ) {
        // Define patterns for the start and end of the desired sections
        Pattern startPattern = Pattern.compile("Alg name : (.+)");
        Pattern initialNodePattern = Pattern.compile("Initial node:(.+?)Params:", Pattern.DOTALL);
        Pattern runtimePattern = Pattern.compile("Runtime: (\\d+ ms)");
        Pattern verticesPattern = Pattern.compile("vertices: (\\d+)");

        Matcher startMatcher = startPattern.matcher(input);
        Matcher initialNodeMatcher = initialNodePattern.matcher(input);

        if (startMatcher.find() && initialNodeMatcher.find()) {
            String algName = startMatcher.group(1).trim();


            List<String> rowData = new ArrayList<>();



            Matcher runtimeMatcher = runtimePattern.matcher(input);
            Matcher verticesMatcher = verticesPattern.matcher(input);

            if (runtimeMatcher.find()) {
                String runtime = runtimeMatcher.group(1).trim();
                rowData.add( runtime);
            }

            if (verticesMatcher.find()) {
                String vertices = verticesMatcher.group(1).trim();
                rowData.add( vertices + " Vertices");
            }
            map.get(algName).add(new Results(rowData.toString()));
            rows.add(rowData.toArray(new String[0]));
        }
    }

}

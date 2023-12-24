package test;

import org.apache.poi.xwpf.usermodel.*;
import test.Results.Test;
import test.agorithms.Algorithms;
import test.components.Node;
import test.main.RunTimeTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WordDoc {
    private final XWPFDocument document;
    private final FileOutputStream outStream;
    private final String doc_name;

    private String[] headers;

    public WordDoc(String doc_name) throws FileNotFoundException {
        this.doc_name = doc_name;
        this.headers = headers;
        this.document = new XWPFDocument();
        this.outStream = new FileOutputStream(doc_name);
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }


    public void create_table_for_little_sample(Test test) {
        print_start_of_document(test);

        // foreach runtime test, we create a Table for itself
        for (RunTimeTest runtime_test : test.getTests()) {
            addText("\nInitial node:");
            print_initial_node(runtime_test.getAlgorithms()[0].getStart_node());
            XWPFTable table = document.createTable();
            add_headers_row(table);
            add_data_rows(table, runtime_test);
            System.out.println("Table for " + runtime_test.toString() + "\n Successfully created!");
        }

    }

    private void print_start_of_document(Test test) {
        addText("Sample size:  " + test.getTests().size());
        addText("Puzzle size: " + test.getPuzzle_size());
        addText("Tables: ");
    }

    public void create_table_for_big_sample(Test test) {
        print_start_of_document(test);
        XWPFTable table = document.createTable();
        add_headers_row(table);

        int samples_counter = 1;
        for (RunTimeTest runTimeTest : test.getTests()) {
            XWPFTableRow data_row = table.createRow();
            data_row.getCell(0).setText(String.valueOf(samples_counter++));
            String initial_node = runTimeTest.getAlgorithms()[0].getStart_node().getState().getPuzzleRepresentation();
            data_row.getCell(1).setText(initial_node);
            add_single_row(table, runTimeTest, data_row);
        }
    }

    private void add_single_row(XWPFTable table, RunTimeTest runTimeTest, XWPFTableRow data_row) {
        int i = 2;
        for (Algorithms alg : runTimeTest.getAlgorithms()) {
            StringBuilder data = new StringBuilder();
            // Run time
            long run_time = runTimeTest.getRun_times().get(alg);
            data.append("Run time: ").append(String.valueOf(run_time)).append(" nanoseconds");
            // vertices
            int vertices = runTimeTest.getVertices().get(alg);
            data.append("\n\n Vertices: ").append(vertices);
            // amount of displacement
            int amount_of_displacement = runTimeTest.getAmount_of_displacement().get(alg);
            data.append("\n\nThe amount of displacements: ").append(amount_of_displacement);

            data_row.getCell(i).setText(data.toString());
            i++;
        }
    }

    public void add_data_rows(XWPFTable table, RunTimeTest runtime_test) {
        for (Algorithms alg : runtime_test.getAlgorithms()) {
            XWPFTableRow data_row = table.createRow();
            data_row.getCell(0).setText(alg.Name());
            // Run time
            long run_time = runtime_test.getRun_times().get(alg);
            data_row.getCell(1).setText(String.valueOf(run_time) + " nanoseconds");
            // vertices
            int vertices = runtime_test.getVertices().get(alg);
            data_row.getCell(2).setText(String.valueOf(vertices));
            // amount of displacement
            int amount_of_displacement = runtime_test.getAmount_of_displacement().get(alg);
            data_row.getCell(3).setText(String.valueOf(amount_of_displacement));
        }
    }

    private void print_initial_node(Node startNode) {
        String puzzle_tostring = startNode.getState().getPuzzleRepresentation();
        String[] values = puzzle_tostring.split("\n");
        for (String val : values) {
            addText(val);
        }
    }


    public void write_table_into_document() {
        try {
            document.write(outStream);
            outStream.close();
            System.out.println("File and Table successfully created!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void add_headers_row(XWPFTable table) {
        XWPFTableRow table_row = table.getRow(0);
        table_row.getCell(0).setText(headers[0]);
        for (int i = 1; i < headers.length; i++) {
            table_row.addNewTableCell().setText(headers[i]);
        }
    }

    private void addText(String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }

    private void addEmptyLine() {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.createRun().addBreak();
    }

    public String getDoc_name() {
        return doc_name;
    }
}

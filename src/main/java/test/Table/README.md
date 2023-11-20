# Table Package

## AverageTerminalTable Class

The `AverageTerminalTable` class creates and prints a specific table for the average task. In this task, 50 different graphs (puzzle representations) are sampled in sizes 15 and 24, and a table is generated to show the average runtime and developed vertices.

### Constructor

```java
public AverageTerminalTable(String[] columnNames, int num_rows)
```

1) `columnNames`: An array of column names.
2) `num_rows`: The number of rows.
## Methods
1) `getRows():` Returns the list of rows.
2) `printTable():` Prints the table.
3) `calculateColumnWidths():` Calculates the column widths.
4) `generate_table():` Generates the table.
5) `parseRowString(String input):` Parses the row string.

### SampleTerminalTable Class
The SampleTerminalTable class creates and prints a specific table for the sample task. In this task, up to 10 puzzles are sampled, and the results are shown in the table

## Methods
1) `getRows():` Returns the list of rows.
2) `printTable():` Prints the table.
3) `calculateColumnWidths():` Calculates the column widths.
4) `generate_table():` Generates the table.
5) `parseRowString(String input):` Parses the row string.

### Table Interface
The Table interface holds essential methods for generating tables.

## Methods
1) `getRows():` Returns the list of rows.
2) `printTable():` Prints the table.
3) `calculateColumnWidths():` Calculates the column widths.
4) `generate_table():` Generates the table.
5) `parseRowString(String input):` Parses the row string.

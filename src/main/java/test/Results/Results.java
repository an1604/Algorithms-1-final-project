package test.Results;

public class Results {

    /**The Results class.
     * This class represents a single cell in the table, for a specific algorithm.
     * data (String) - a String representation of the run time and the developed vertices for a specific sample.
     **/
    String data;


    public Results( String data ) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

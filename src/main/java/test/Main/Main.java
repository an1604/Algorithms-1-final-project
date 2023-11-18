package test.Main;

public class Main {
    public static void main(String[] args) {
        Test test = new Test();
        Thread thread = new Thread(() -> {
            test.Menu();
        });
        thread.start();
        try {
            thread.join(); // Wait for the thread to complete
            System.out.println("Done.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

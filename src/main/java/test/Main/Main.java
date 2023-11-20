package test.Main;

import test.Multi_Thread.Task;
import test.Results.Test;
import test.Results.Tests;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {




    public static void main(String[] args) {
        ExecutorService es =  Executors.newFixedThreadPool(3);

        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        Tests general_test=null;
        while (!stop) {
            System.out.println("Choose your choice : ");
            System.out.println("(1) Create a big sample (up to 50) and visualize the averages between the results");
            System.out.println("(2) Create a little sample and visualize the results.");
            System.out.println("(3) Getting information about a sample via id.");
            System.out.println("(4) Exit.");

           String choice = scanner.nextLine();

            //Getting information from the user
            System.out.println("What is the size of the puzzle? ");
            int puzzle_size = scanner.nextInt();
            System.out.println("What is the size of the sample? ");
            int sample_size = scanner.nextInt();
            System.out.println("How many steps from the final state?");
            int n = scanner.nextInt();

            System.out.println("Generating " + sample_size + " random graphs using 5000 steps from the final state, in size "
                    + (puzzle_size == 15 ? 4 : 5) + "X" + (puzzle_size == 15 ? 4 : 5) + "...");

            Tests test = new Test();
            // Check his choice
            switch (choice) {
                case "1":
                    if ((puzzle_size == 15 || puzzle_size == 24)) {
                        if (puzzle_size == 15)
                            puzzle_size = 4;
                        else
                            puzzle_size = 5;
                        Task task = new Task(test , puzzle_size,sample_size,n);
                        for (int i = 0; i < sample_size; i++) {
                            es.execute(task);
                        }
                        es.shutdown();
                    }
                    break;
                case "2":
                    if (puzzle_size == 15 || puzzle_size == 24 && (sample_size < 10)) {
                        if (puzzle_size == 15)
                            puzzle_size = 4;
                        else
                            puzzle_size = 5;

                        Task task = new Task(test , puzzle_size,sample_size,n);
                        for (int i = 0; i < sample_size; i++) {
                            es.execute(task);
                        }
                        es.shutdown();

                    }
                    break;
                case "3":
                  general_test.print_results();
                  break;
                case "4":
                    stop = true;
                    break;
            }
            try {
                es.awaitTermination(5, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(choice.equals("1")){
                test.get_samples_table();
            } else if (choice.equals("2")) {
                test.get_avg_and_visualize_results();
            }

            general_test=test;
        }
        System.out.println("Done");
    }
}

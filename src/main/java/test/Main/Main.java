package test.Main;

import test.Multi_Thread.Task;
import test.Results.Test;
import test.Results.Tests;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

//    public static void Menu(){
//            Scanner scanner = new Scanner(System.in);
//            boolean stop = false;
//            boolean first_iteration = false;
//            String choice = "";
//            while (!stop) {
//                System.out.println("Choose your choice : ");
//                System.out.println("(1) Create a little sample and visualize the results.");
//                System.out.println("(2) Create a big sample (up to 50) and visualize the averages between the results");
//                System.out.println("(3) Getting information about a sample via id.");
//                System.out.println("(4) Exit.");
//
//                choice = scanner.nextLine();
//
//                //Getting information from the user
//                System.out.println("What is the size of the puzzle? ");
//                int puzzle_size = scanner.nextInt();
//                System.out.println("What is the size of the sample? ");
//                int sample_size = scanner.nextInt();
//                System.out.println("How many steps from the final state?");
//                int n = scanner.nextInt();
//
//                System.out.println("Generating " + sample_size + " random graphs using 5000 steps from the final state, in size "
//                        +( puzzle_size==15? 4:5) + "X" + ( puzzle_size==15? 4:5) + "...");
//
//                // Check his choice
//                switch (choice){
//                    case "1":
//                        if((puzzle_size==15 || puzzle_size ==24) && (sample_size <10)){
//                            if(puzzle_size==15)
//                                puzzle_size=4;
//                            else
//                                puzzle_size=5;
//
//                            int finalPuzzle_size = puzzle_size;
//                            new Thread( ()->test.start(finalPuzzle_size, sample_size,n, )).start();
//                            try {
//                                Thread.currentThread().join();
//                                visualize_results();
//                            } catch (InterruptedException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                        break;
//                    case "2":
//                        test.start(puzzle_size, sample_size,n);
//                        es.shutdown();
//                        try {
//                            // Wait for all tasks to complete or until timeout
//                            if (es.awaitTermination(10, TimeUnit.MINUTES)) {
//                                System.out.println("Generation Done.");
//                                get_avg_and_visualize_results();
//                            } else {
//                                System.out.println("Timeout occurred while waiting for tasks to complete.");
//                            }
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                    case "3":
//                        print_results();
//                        break;
//                    case "4":
//                        stop = true;
//                        break;
//                }
//            }
//        }


    public static void main(String[] args) {
        ExecutorService es =  Executors.newFixedThreadPool(3);

        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        String choice = "";
        Tests general_test=null;
        while (!stop) {
            System.out.println("Choose your choice : ");
            System.out.println("(1) Create a little sample and visualize the results.");
            System.out.println("(2) Create a big sample (up to 50) and visualize the averages between the results");
            System.out.println("(3) Getting information about a sample via id.");
            System.out.println("(4) Exit.");

            choice = scanner.nextLine();

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
                    if ((puzzle_size == 15 || puzzle_size == 24) && (sample_size < 10)) {
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
                    if (puzzle_size == 15 || puzzle_size == 24) {
                        if (puzzle_size == 15)
                            puzzle_size = 4;
                        else
                            puzzle_size = 5;

                        Task task = new Task(test , puzzle_size,sample_size,n);
                        for (int i = 0; i < sample_size; i++) {
                            es.execute(task);
                        }

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
            System.out.println("Before the if statement");
            if(choice=="2"){
                test.get_avg_and_visualize_results();
            } else if (choice=="1") {
                test.visualize_results();
            }
            System.out.println("After the if statement");

            general_test=test;
        }
        System.out.println("Done");
    }
}

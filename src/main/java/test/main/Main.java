package test.main;

import test.Multi_Thread.MyExecutorService;
import test.Multi_Thread.Task;
import test.Results.Test;
import test.Results.Tests;
import test.components.Graph;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) {
        // We run the heavy tasks on multi-thread to save time
        MyExecutorService myExecutorService = new MyExecutorService(3);

        boolean stop = false;
        boolean is_executed = true;
        Tests current_test=null;
        String doc_name = "";
        while (!stop) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose your choice : ");
            System.out.println("(1) Create a big sample (up to 50) and visualize the averages between the results");
            System.out.println("(2) Create a little sample and visualize the results.");
            System.out.println("(3) Getting information about a sample via id.");
            System.out.println("(4) See the Graph implementation menu.");
            System.out.println("(5) Exit.");

            String choice = scanner.nextLine();
            int puzzle_size=0,sample_size=0,n=0;

            if(choice.equals("2") || choice.equals("1")) {
                System.out.println("Document name?");
                doc_name = scanner.nextLine();
                //Getting information from the user
                System.out.println("What is the size of the puzzle? ");
                puzzle_size = scanner.nextInt();
                System.out.println("What is the size of the sample? ");
                sample_size = scanner.nextInt();
                System.out.println("How many steps from the final state?");
                n = scanner.nextInt();
                System.out.println("Generating " + sample_size + " random graphs using " + n +
                        " steps from the final state, in size "
                        + (puzzle_size == 15 ? 4 : 5) + "X" + (puzzle_size == 15 ? 4 : 5) + "...");
            }


            // Check his choice
            switch (choice) {
                case "1":
                    myExecutorService.check_terminated();
                    current_test = start_test(puzzle_size,sample_size,myExecutorService,n,doc_name);
                    break;
                case "2":
                    myExecutorService.check_terminated();
                    if (sample_size >10)
                        is_executed = false;
                    else
                        current_test = start_test(puzzle_size,sample_size,myExecutorService,n,doc_name);
                    break;
                case "3":
                    assert current_test != null;
                    current_test.menu_for_showing_results();
                    is_executed = false;
                    break;
                case "4":
                    Graph.menu();
                    is_executed = false;
                    break;
                case "5":
                    stop = true;
                    is_executed = false;
                    break;
                default:
                    System.out.println("I don't understand, try again.");
                    is_executed = false;
                    break;
            }
            if(is_executed) {
                myExecutorService.await_termination();
            }
            else
                is_executed = true;


            if(choice.equals("1")){

                current_test.create_Word_doc_for_big_sample();
            } else if (choice.equals("2")) {
                assert current_test != null;
                String[] headers = {"Algorithm", "Run Time", "Developed Vertices", "The Amount Of Displacements"};
                current_test.create_Word_doc_for_little_sample(headers);
            }
        }
        myExecutorService.close();
        System.out.println("Done");
    }

    public static Tests start_test(int puzzle_size , int sample_size, MyExecutorService mes, int num_of_steps, String doc_name ){
        Tests test = null;
        if ((puzzle_size == 15 || puzzle_size == 24)) {
            if (puzzle_size == 15)
                puzzle_size = 4;
            else
                puzzle_size = 5;
            test = new Test(doc_name);
            Task task = new Task(test , puzzle_size,sample_size,num_of_steps);
            mes.execute_tasks(sample_size,task);
        }
        return test;
    }
}
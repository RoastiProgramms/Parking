package main.parkinglot;

import java.io.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args){

        CommandExecutor commandExecutor = CommandExecutor.getInstance();
        BufferedReader bufferedReader;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file Path :");
        String path = scanner.nextLine();
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrintWriter pw= null;
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter(file, true)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Input Commands");

        pw.println("Commands that were used:");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int count=0;
        while (true) {

            if (count==0)
            {
                System.out.println("Commands List: create_parking_lot");
            }
            else{
                System.out.println("Commands List:");
                System.out.println("park, leave, status, registration_numbers_for_cars_with_colour,\n" +
                        "slot_numbers_for_cars_with_colour, slot_number_for_registration_number,show_abonament_cars,show_noabonament_cars");
            }
            count++;
            String commandText = null;
            try {
                commandText = bufferedReader.readLine();
            } catch (IOException e) {

                e.printStackTrace();
            }
            pw.println(count+". "+commandText);
            if (commandText == null || "exit".equalsIgnoreCase(commandText)) {
                break;
            } else {
                // if execution is not success then exit as there can be dependencies with commands
                boolean executionSuccess = commandExecutor.execute(commandText);
                if(!executionSuccess) {
                    break;
                }
            }

        }
        pw.println();
        pw.close();
    }
}

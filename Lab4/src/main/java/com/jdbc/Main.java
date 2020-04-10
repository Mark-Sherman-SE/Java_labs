package com.jdbc;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter the number of products");
        Scanner scanner = new Scanner(System.in);
        int n;
        do {
            try {
                n = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
                continue;
            }
            break;
        } while (true);
        try(DatabaseConnection databaseConnection = DatabaseConnection.getInstance()) {
            DBProcessor dbProcessor = new DBProcessor(n, databaseConnection);
            boolean condition = true;
            while (condition) {
                System.out.println("Enter the command");
                String command = scanner.next();
                try {
                    switch (command) {
                        case "/add": {
                            String title = scanner.next();
                            int cost = scanner.nextInt();
                            dbProcessor.insert(title, cost);
                            break;
                        }
                        case "/delete": {
                            String title = scanner.next();
                            dbProcessor.deleteProduct(title);
                            break;
                        }
                        case "/show_all":
                            dbProcessor.showAll();
                            break;
                        case "/price": {
                            String title = scanner.next();
                            dbProcessor.getCost(title);
                            break;
                        }
                        case "/change_price": {
                            String title = scanner.next();
                            int cost = scanner.nextInt();
                            dbProcessor.updateCost(title, cost);
                            break;
                        }
                        case "/filter_by_price":
                            int min = scanner.nextInt();
                            int max = scanner.nextInt();
                            dbProcessor.showInterval(min, max);
                            break;
                        case "/finish":
                            System.out.println("Shut down");
                            condition = false;
                            break;
                        default:
                            System.out.println("Invalid command, try again");
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input of int");
                }
            }
        } catch (SQLException e) {
            System.out.println("Can\'t connect to database");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Problem with closing connection");
            e.printStackTrace();
        }
    }
}

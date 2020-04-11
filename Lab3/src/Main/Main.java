package Main;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of students");
        int numberOfStudents;

        do {
            try {
                numberOfStudents = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
                continue;
            }
            break;
        } while (true);

        BlockingDeque<Student> students = new LinkedBlockingDeque<Student>(10);
        Generator generator = new Generator(students, numberOfStudents);
        Robot math = new Robot(students, "HigherMath");
        Robot phys = new Robot(students, "Physics");
        Robot oop = new Robot(students, "OOP");

        ExecutorService service = Executors.newFixedThreadPool(4);
        service.execute(generator);
        service.execute(math);
        service.execute(phys);
        service.execute(oop);

        service.shutdown();
    }
}


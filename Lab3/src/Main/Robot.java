package Main;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Phaser;

public class Robot extends Thread {
    private static final Phaser PHASER = new Phaser(3);
    private final String subject;
    private Student student = null;
    public BlockingDeque<Student> students;

    public Robot(BlockingDeque<Student> students, String subject) {
        this.students = students;
        this.subject = subject;
        setName(subject);
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Robot " + subject);
                student = students.peek();
                if (student != null) {
                    if (student.getSubject().equals(subject)) {
                        student = students.take();
                        PHASER.arrive();
                        System.out.println(subject + " teacher started verifying");
                        while (student.getLabsCount() != 0) {
                            System.out.println("Robot " + subject + " is working, " + student.getLabsCount() + " left, student number " + student.getNumberOfStudent());
                            student.verifyLabs();
                        }
                        System.out.println(subject + " teacher finished verifying");
                    } else if (student.getSubject().equals("finish")) {
                        PHASER.arriveAndDeregister();
                        break;
                    }
                }
                PHASER.arriveAndAwaitAdvance();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

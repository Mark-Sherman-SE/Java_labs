package Main;

import java.util.concurrent.BlockingDeque;

public class Robot extends Thread {
    private final String subject;
    private Student student;
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
                student = students.peek();
                if (student != null) {
                    if (student.getSubject().equals(subject)) {
                        student = students.take();
                        System.out.println(subject + " teacher started verifying");
                        while (student.getLabsCount() != 0) {
                            System.out.println("Robot " + subject + " is working, " + student.getLabsCount() + " left, student number " + student.getNumberOfStudent());
                            student.verifyLabs();
                        }
                        System.out.println(subject + " teacher finished verifying");
                    } else if (student.getSubject().equals("finish")) {
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

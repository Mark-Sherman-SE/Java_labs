package Main;

public class Student {
    private final String subject;
    private int numberOfStudent;
    private int labsCount;
    private static int counter = 0;

    public Student (String subject, int labsCount) {
        this.subject = subject;
        this.labsCount = labsCount;
        ++counter;
        this.numberOfStudent = counter;
    }

    public String getSubject() {
        return subject;
    }

    public int getLabsCount() {
        return labsCount;
    }

    public int getNumberOfStudent() {
        return numberOfStudent;
    }

    public void verifyLabs() {
        labsCount -= 5;
    }
}

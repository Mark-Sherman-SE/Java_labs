package ru.spbstu.main.shapes;

/**
 * Представление о прямоугольнике.
 * <p>
 * Прямоугольник — четырехугольник, у которого все углы
 * прямые (равны 90 градусам).
 *
 * @see <a href="https://ru.wikipedia.org/wiki/%D0%9F%D1%80%D1%8F%D0%BC%D0%BE%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA">Прямоугольник</a>
 */
public class Rectangle implements Polygon {
    private Dot [] dots = new Dot[4];
    private float height, width;
    private int angle;

    public Rectangle(Dot firstDot, Dot secondDot, Dot thirdDot, Dot fourthDot) {
        if (getLength(firstDot, thirdDot) == getLength(secondDot, fourthDot)) {
            this.width = getLength(firstDot, secondDot);
            this.height = getLength(firstDot, fourthDot);
        } else {
            throw new RuntimeException("Invalid position of dots");
        }
        this.dots[0] = firstDot;
        this.dots[1] = secondDot;
        this.dots[2] = thirdDot;
        this.dots[3] = fourthDot;
        angle = calculateAngle(this.dots[0], this.dots[1]);
    }

    public Rectangle(Dot dot, float height, float width, int angleOfRotation) {
        this.width = width;
        this.height = height;
        this.angle = angleOfRotation;

        Dot tempFirstDot, tempSecondDot, tempThirdDot, tempFourthDot;
        tempFirstDot = new Dot(dot);
        tempSecondDot = new Dot(width + tempFirstDot.x, tempFirstDot.y);
        tempThirdDot = new Dot(width + tempFirstDot.x,height + tempFirstDot.y);
        tempFourthDot = new Dot(tempFirstDot.x, height + tempFirstDot.y);

        this.dots[0] = tempFirstDot;
        this.dots[1] = findNewCoordinates(this.dots[0], tempSecondDot, angleOfRotation);
        this.dots[2] = findNewCoordinates(this.dots[0], tempThirdDot, angleOfRotation);
        this.dots[3] = findNewCoordinates(this.dots[0], tempFourthDot, angleOfRotation);
    }

    private float getLength(Dot firstDot, Dot secondDot) {
        return (float) Math.sqrt(Math.pow(firstDot.x - secondDot.x, 2) + Math.pow(firstDot.y - secondDot.y, 2));
    }

    private int calculateAngle(Dot firstDot, Dot secondDot) {
        float cathetus = getLength(secondDot, new Dot(secondDot.x, firstDot.y));
        int angleOfRotation = 0;
        int primaryAngle = (int) (Math.asin(cathetus / width) * 180 / Math.PI);
        if ((firstDot.x < secondDot.x) && (firstDot.y < secondDot.y)) {
            angleOfRotation = primaryAngle;
        } else if ((firstDot.x > secondDot.x) && (firstDot.y < secondDot.y)) {
            angleOfRotation = 180 - primaryAngle;
        } else if ((firstDot.x > secondDot.x) && (firstDot.y > secondDot.y)) {
            angleOfRotation = 180 + primaryAngle;
        } else if ((firstDot.x < secondDot.x) && (firstDot.y > secondDot.y)) {
            angleOfRotation = 360 - primaryAngle;
        }
        return angleOfRotation;
    }

    private Dot findNewCoordinates(Dot fixedDot, Dot dot, int angleOfRotation) {
        double angleOfRotationInRadians = angleOfRotation * Math.PI / 180;
        float x = (float)((dot.x - fixedDot.x) * Math.cos(angleOfRotationInRadians) - (dot.y - fixedDot.y) * Math.sin(angleOfRotationInRadians) + fixedDot.x);
        float y = (float)((dot.x - fixedDot.x) * Math.sin(angleOfRotationInRadians) + (dot.y - fixedDot.y) * Math.cos(angleOfRotationInRadians) + fixedDot.y);
        return new Dot(x, y);
    }

    private void findNewCoordinates(int angleOfRotation) {
        double angleOfRotationInRadians = angleOfRotation * Math.PI / 180;
        for (int i = 1; i < dots.length; ++i) {
            dots[i] = findNewCoordinates(dots[0], dots[i], angleOfRotation);
        }
    }

    @Override
    public float getArea() {
        return height * width;
    }

    @Override
    public int getRotation() {
        return angle;
    }

    @Override
    public float getPerimeter() {
        return 2 * (height + width);
    }
    /*
     * TODO: Реализовать класс 'Rectangle'
     * 1. Используйте наследование.
     * 2. Реализуйте все абстрактные методы.
     */
}

package ru.spbstu.main.shapes;

/**
 * Представление о треугольнике.
 * <p>
 * Треуго́льник (в евклидовом пространстве) — геометрическая
 * фигура, образованная тремя отрезками, которые соединяют
 * три точки, не лежащие на одной прямой. Указанные три
 * точки называются вершинами треугольника, а отрезки —
 * сторонами треугольника. Часть плоскости, ограниченная
 * сторонами, называется внутренностью треугольника: нередко
 * треугольник рассматривается вместе со своей внутренностью
 * (например, для определения понятия площади).
 *
 * @see <a href="https://ru.wikipedia.org/wiki/%D0%A2%D1%80%D0%B5%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA">Треугольник</a>
 */
public class Triangle implements Polygon {
    private Dot [] dots = new Dot[3];
    private float [] sides = new float[3];
    private int angle;

    public Triangle(Dot firstDot, Dot secondDot, Dot thirdDot) {
        if (isTriangle(firstDot, secondDot, thirdDot)) {
            this.dots[0] = firstDot;
            this.dots[1] = secondDot;
            this.dots[2] = thirdDot;
            this.sides[0] = getLength(this.dots[0], this.dots[1]);
            this.sides[1] = getLength(this.dots[1], this.dots[2]);
            this.sides[2] = getLength(this.dots[2], this.dots[0]);
            this.angle = calculateAngle(this.dots[0], this.dots[1], this.sides[0]);
        } else {
            throw new RuntimeException("Invalid dots for triangle");
        }
    }
    private boolean isTriangle(Dot firstDot, Dot secondDot, Dot thirdDot) {
        return (!(getLength(firstDot, secondDot) + getLength(firstDot, thirdDot) <= getLength(secondDot, thirdDot))) &&
                (!(getLength(firstDot, secondDot) + getLength(secondDot, thirdDot) <= getLength(firstDot, thirdDot))) &&
                (!(getLength(secondDot, thirdDot) + getLength(firstDot, thirdDot) <= getLength(firstDot, secondDot)));
    }

    private float getLength(Dot firstDot, Dot secondDot) {
        return (float) Math.sqrt((Math.pow(firstDot.x - secondDot.x, 2) + Math.pow(firstDot.y - secondDot.y, 2)));
    }

    private int calculateAngle(Dot firstDot, Dot secondDot, float hypotenuse) {
        float cathetus = getLength(secondDot, new Dot(secondDot.x, firstDot.y));
        int angleOfRotation = 0;
        int primaryAngle = (int) (Math.asin(cathetus / hypotenuse) * 180 / Math.PI);
        if ((firstDot.x < secondDot.x) && (firstDot.y < secondDot.y)) {
            angleOfRotation = primaryAngle;
        } else if ((firstDot.x > secondDot.x) && (firstDot.y < secondDot.y)) {
            angleOfRotation  = 180 - primaryAngle;
        } else if ((firstDot.x > secondDot.x) && (firstDot.y > secondDot.y)) {
            angleOfRotation  = 180 + primaryAngle;
        } else if ((firstDot.x < secondDot.x) && (firstDot.y > secondDot.y)) {
            angleOfRotation  = 360 - primaryAngle;
        }
        return angleOfRotation;
    }

    @Override
    public float getArea() {
        float halfMeter = (sides[0] + sides[1] + sides[2]) / 2;
        return (float) Math.sqrt(halfMeter * (halfMeter - sides[0]) * (halfMeter - sides[1]) * (halfMeter - sides[2]));
    }

    @Override
    public int getRotation() {
        return angle;
    }

    @Override
    public float getPerimeter() {
        return sides[0] + sides[1] + sides[2];
    }

    /*
     * TODO: Реализовать класс 'Triangle'
     * 1. Используйте наследование.
     * 2. Реализуйте все абстрактные методы.
     */
}

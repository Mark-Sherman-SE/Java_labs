package ru.spbstu.main;

import ru.spbstu.main.shapes.*;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[10];
        shapes[0] = new Circle(new Dot(0.0f, 0.0f), 1.0f);
        shapes[1] = new Circle(new Dot(-2.3f, -6.0f), 10.7f);
        shapes[2] = new Circle(new Dot(23.7f, 25.8f), 9.8f);
        shapes[3] = new Triangle(new Dot(0.0f, 0.0f), new Dot(3.0f, 1.0f), new Dot(2.0f, 3.0f));
        shapes[4] = new Triangle(new Dot(10.5f, 8.2f), new Dot(7.5f, 7.1f), new Dot(10.8f, 5.9f));
        shapes[5] = new Triangle(new Dot(0.0f, 0.1f), new Dot(-2.1f, 4.5f), new Dot(-5.9f, 0.0f));
        shapes[6] = new Rectangle(new Dot(-3.5f, -5.6f), new Dot(2.3f, -5.6f), new Dot(2.3f, 8.9f), new Dot(-3.5f, 8.9f));
        shapes[7] = new Rectangle(new Dot(-3.5f, -5.6f), new Dot(2.3f, -5.6f), new Dot(2.3f, 8.9f), new Dot(-3.5f, 8.9f));
        shapes[8] = new Rectangle(new Dot(-3.5f, -5.6f), new Dot(2.3f, -5.6f), new Dot(2.3f, 8.9f), new Dot(-3.5f, 8.9f));
        shapes[9] = new Rectangle(new Dot(-300.5f, -5.6f), new Dot(2.3f, -5.6f), new Dot(2.3f, 8.9f), new Dot(-300.5f, 8.9f));
        Shape shapeWithMaxArea = findShapeWithMaxArea(shapes);

        for (Shape shape:shapes) {
            System.out.println(shape.getArea() + " " + shape.getClass().getSimpleName());
        }

        System.out.print("\n" + shapeWithMaxArea.getArea() + " " + shapeWithMaxArea.getClass().getSimpleName());
        /*
         * TODO: Выполнить действия над массивом 'shapes'
         *
         * 1. Проинициализировать переменную 'shapes' массивом
         *    содержащим 10 произвольных фигур. Массив должен
         *    содержать экземпляры классов Circle, Rectangle
         *    и Triangle.
         *
         * 2. Найти в массиве 'shapes' фигуру с максимальной
         *    площадью. Для поиска фигуры необходимо создать
         *    статический метод в текущем классе (Main).
         */
    }
    private static Shape findShapeWithMaxArea(Shape [] shapes) {
        if (shapes.length == 0) {
            throw new RuntimeException("Empty array of shapes");
        }
        float maxArea = 0.0f;
        Shape shapeWithMaxArea = null;
        for (Shape shape: shapes) {
            if (shape.getArea() > maxArea) {
                shapeWithMaxArea = shape;
                maxArea = shape.getArea();
            }
        }
        return shapeWithMaxArea;
    }
}

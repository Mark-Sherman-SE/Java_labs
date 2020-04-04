package ru.spbstu.main.shapes;

public class Dot implements Point {
    float x, y;

    public Dot() {
        this.x = 0;
        this.y = 0;
    }

    public Dot(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Dot(Dot dot) {
        this.x = dot.getX();
        this.y = dot.getY();
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}

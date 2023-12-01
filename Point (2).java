import java.util.ArrayList;
import java.util.List;

record Point(double x, double y) {

    public static Point add(Point P1, Point P2) {
        return new Point(P1.x + P2.x, P1.y + P2.y);
    }

    static Point subtract(Point P1, Point P2) {
        return new Point(P1.x - P2.x, P1.y - P2.y);
    }

    Point multiply(double n) {
        return new Point(x * n, y * n);
    }

    Point divide(double n) {
        return new Point(x / n, y / n);
    }

    static Point rotate(final double angle, Point A, Point Center) {
        double x = Center.x + (A.x - Center.x) * Math.cos(angle) - (A.y - Center.y) * Math.sin(angle);
        double y = Center.y + (A.x - Center.x) * Math.sin(angle) + (A.y - Center.y) * Math.cos(angle);
        return new Point(x, y);
    }

    double length() {
        return Math.sqrt(x * x + y * y);
    }

    static void dotProduct() {}

    static double distance(Point A, Point B) {
        return Math.sqrt(Math.pow((A.x - B.x), 2) + Math.pow((A.y - B.y), 2));
    }
}

abstract class Shape {
    abstract Point center();

    abstract double perimeter();

    abstract double area();

    abstract void translate(final Point newCenter);

    abstract void rotate(final double angle);

    abstract void scale(final double coefficient);
}

class Ellipse extends Shape {
    Point A;
    Point B;
    double Pr;

    public Ellipse(Point A, Point B, double Pr) {
        this.A = A;
        this.B = B;
        this.Pr = Pr;
    }

    public List<Point> focuses() {
        ArrayList<Point> res = new ArrayList<>();
        res.add(A);
        res.add(B);
        return res;
    }

    public double focalDistance() {
        return Point.distance(A, B) / 2;
    }

    public double majorSemiAxis() {
        return focalDistance() + Pr;
    }

    public double minorSemiAxis() {
        return Math.sqrt(Math.pow(majorSemiAxis(), 2) - Math.pow(focalDistance(), 2));
    }

    public double eccentricity() {
        return focalDistance() / majorSemiAxis();
    }

    public Point center() {
        return new Point((A.x() + B.x()) / 2, (A.y() + B.y()) / 2);
    }

    public double perimeter() {
        return 4 * ((Math.PI * majorSemiAxis() * minorSemiAxis() + Math.pow(majorSemiAxis() - minorSemiAxis(), 2)) / (majorSemiAxis() + minorSemiAxis()));
    }

    public double area() {
        return Math.PI * majorSemiAxis() * minorSemiAxis();
    }

    public void translate(final Point newCenter) {
        Point Vec = new Point((A.x() + B.x()) / 2, (A.y() + B.y()) / 2);
        A = Point.add(newCenter, Point.subtract(A, Vec));
        B = Point.add(newCenter, Point.subtract(B, Vec));
    }

    public void rotate(final double angle) {
        Point O = center();
        A = Point.rotate(angle, A, O);
        B = Point.rotate(angle, B, O);
    }

    public void scale(final double coefficient) {
        Point C = center();
        Point vec_A = Point.subtract(A, C);
        Point vec_B = Point.subtract(B, C);
        Point res_A = vec_A.multiply(coefficient);
        Point res_B = vec_B.multiply(coefficient);
        A = Point.add(C, res_A);
        B = Point.add(C, res_B);
        Pr *= Math.abs(coefficient);
    }

}

class Circle extends Ellipse {
    public Circle(Point O, double R) {
        super(O, O, R);
    }

    public double radius() {
        return Pr;
    }
}

class Rectangle extends Shape {
    Point A;
    Point B;
    double L;

    public Rectangle(Point A, Point B, double L) {
        this.A = A;
        this.B = B;
        this.L = L;
    }

    public List<Point> vertices() {
        ArrayList<Point> res = new ArrayList<>();
        Point norm = Point.subtract(A, B);
        Point vector = Point.rotate(Math.PI / 2, norm, new Point(0, 0));
        vector = vector.divide(Point.distance(A, B));
        vector = vector.multiply(L / 2);
        res.add(Point.add(A, vector));
        res.add(Point.add(B, vector));
        res.add(Point.subtract(B, vector));
        res.add(Point.subtract(A, vector));
        return res;
    }

    public double firstSide() {
        return Point.distance(A, B);
    }

    public double secondSide() {
        return L;
    }

    public double diagonal() {
        return Math.sqrt(Math.pow(L, 2) + (Math.pow(firstSide(), 2)));
    }

    public Point center() {
        return new Point((A.x() + B.x()) / 2, (A.y() + B.y()) / 2);
    }

    public double perimeter() {
        return 2 * (L + firstSide());
    }

    public double area() {
        return L * firstSide();
    }

    public void translate(final Point newCenter) {
        Point C = center();
        A = Point.add(A, Point.subtract(newCenter, C));
        B = Point.add(B, Point.subtract(newCenter, C));
    }

    public void rotate(final double angle) {
        Point O = center();
        A = Point.rotate(angle, A, O);
        B = Point.rotate(angle, B, O);
    }

    public void scale(final double coefficient) {
        Point C = center();
        Point vector_A = Point.subtract(A, C);
        Point vector_B = Point.subtract(B, C);
        vector_A = vector_A.multiply(coefficient);
        vector_B = vector_B.multiply(coefficient);
        A = Point.add(C, vector_A);
        B = Point.add(C, vector_B);
        L *= Math.abs(coefficient);
    }
}

class Square extends Rectangle {
    public Square(Point A, Point B) {
        super(A, B, Point.distance(A, B));
    }

    public double side() {
        return Point.distance(A, B);
    }

    public Circle circumscribedCircle() {
        Point O = center();
        double R = diagonal() / 2;
        return new Circle(O, R);
    }

    public Circle inscribedCircle() {
        Point O = center();
        double R = Point.distance(A, B) / 2;
        return new Circle(O, R);
    }
}

class Triangle extends Shape {
    Point A;
    Point B;
    Point C;

    public Triangle(Point A, Point B, Point C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public List<Point> vertices() {
        ArrayList<Point> res = new ArrayList<>();
        res.add(A);
        res.add(B);
        res.add(C);
        return res;
    }

    public Circle circumscribedCircle() {
        double t = (A.x() - B.x()) * (C.y() - A.y()) - (A.y() - B.y()) * (C.x() - A.x());
        double t_x = (A.y() - B.y()) * (C.x() * C.x() + C.y() * C.y()) + (B.y() - C.y()) * (A.x() * A.x() + A.y() * A.y()) + (C.y() - A.y()) * (B.x() * B.x() + B.y() * B.y());
        double t_y = (A.x() - B.x()) * (C.x() * C.x() + C.y() * C.y()) + (B.x() - C.x()) * (A.x() * A.x() + A.y() * A.y()) + (C.x() - A.x()) * (B.x() * B.x() + B.y() * B.y());
        Point O = new Point(-0.5 * t_x / t, 0.5 * t_y / t);
        double R = Math.sqrt(Math.pow(A.x() + 0.5 * t_x / t, 2) + Math.pow(A.y() - 0.5 * t_y / t, 2));
        return new Circle(O, R);
    }

    public Circle inscribedCircle() {
        double a = Point.distance(B, C);
        double b = Point.distance(A, C);
        double c = Point.distance(B, A);
        double p = (a + b + c) / 2;
        double R = Math.sqrt((p - a) * (p - b) * (p - c) / p);
        double x = (a * A.x() + b * B.x() + c * C.x()) / (2 * p);
        double y = (a * A.y() + b * B.y() + c * C.y()) / (2 * p);
        return new Circle(new Point(x, y), R);
    }

    public Point orthocenter() {
        double a_x = B.x() - A.x();
        double a_y = A.y() - B.y();
        double b_x = C.x() - B.x();
        double b_y = B.y() - C.y();
        double x = (-C.y() + A.y() + a_x * C.x() / a_y - b_x * A.x() / b_y) / (a_x / a_y - b_x / b_y);
        double y = a_x * (x - C.x()) / a_y + C.y();
        return new Point(x, y);
    }

    public Circle ninePointsCircle() {
        Point M1 = new Point((A.x() + B.x()) / 2, (A.y() + B.y()) / 2);
        Point M2 = new Point((C.x() + B.x()) / 2, (C.y() + B.y()) / 2);
        Point M3 = new Point((A.x() + C.x()) / 2, (A.y() + C.y()) / 2);
        Triangle E = new Triangle(M1, M2, M3);
        return E.circumscribedCircle();
    }

    public Point center() {
        return new Point((A.x() + B.x() + C.x()) / 3, (A.y() + B.y() + C.y()) / 3);
    }

    public double perimeter() {
        return Point.distance(A, B) + Point.distance(A, C) + Point.distance(C, B);
    }

    public double area() {
        double p = perimeter() / 2;
        return Math.sqrt(p * (p - Point.distance(A, B)) * (p - Point.distance(C, B)) * (p - Point.distance(A, C)));
    }

    public void translate(final Point newCenter) {
        Point O = center();
        A = Point.add(A, Point.subtract(newCenter, O));
        B = Point.add(B, Point.subtract(newCenter, O));
        C = Point.add(C, Point.subtract(newCenter, O));
    }

    public void rotate(final double angle) {
        Point O = center();
        A = Point.rotate(angle, A, O);
        B = Point.rotate(angle, B, O);
        C = Point.rotate(angle, C, O);
    }

    public void scale(final double coefficient) {
        Point O = center();
        Point vector_A = Point.subtract(A, O);
        Point vector_B = Point.subtract(B, O);
        Point vector_C = Point.subtract(C, O);
        vector_A = vector_A.multiply(coefficient);
        vector_B = vector_B.multiply(coefficient);
        vector_C = vector_C.multiply(coefficient);
        A = Point.add(O, vector_A);
        B = Point.add(O, vector_B);
        C = Point.add(O, vector_C);
    }
}
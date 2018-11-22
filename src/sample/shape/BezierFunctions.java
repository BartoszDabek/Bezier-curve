package sample.shape;

class BezierFunctions {

    static final Function[] cubic = new Function[]{
            u -> Math.pow(1 - u, 3),
            u -> 3 * u * Math.pow(1 - u, 2),
            u -> 3 * Math.pow(u, 2) * (1 - u),
            u -> Math.pow(u, 3)
    };


    static final Function[] quadratic = new Function[]{
            u -> Math.pow(1 - u, 2),
            u -> 2 * (1 - u) * u,
            u -> Math.pow(u, 2)
    };

}

interface Function {
    double of(double x);
}

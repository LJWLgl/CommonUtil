package io.github.ljwlgl.model;

/**
 * @author zq_gan
 * @since 2020/5/24
 **/

public class Tuple2<X, Y> {

    private X x;
    private Y y;

    public Tuple2() {
    }

    public Tuple2(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public void setX(X x) {
        this.x = x;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }
}

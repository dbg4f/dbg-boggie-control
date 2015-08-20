package dbg.misc.calc;

public class LeversPosition {



    private final CoupledTwainLeverPair pair;

    CartesianPoint pen;
    CartesianPoint rightLowerTop;
    CartesianPoint bothTop;
    CartesianPoint leftLowerTop;

    public LeversPosition(CoupledTwainLeverPair pair) {
        this.pair = pair;
    }

    public LeverAngles calcAngles(CartesianPoint pen) {

        this.pen = pen;

        rightLowerTop = new CircleIntersection(
                new Circle(pair.penToRightUpperBottomDistance, pen),
                new Circle(pair.leverRightLowerLength, pair.leverRightStart)).getRight();


        bothTop = new CircleIntersection(
                new Circle(pair.penToRightUpperTopDistance, pen),
                new Circle(pair.leverRightUpperLength, rightLowerTop)).getLeft();

        leftLowerTop = new CircleIntersection(
                new Circle(pair.leverLeftUpperLength, bothTop),
                new Circle(pair.leverLeftLowerLength, pair.leverLeftStart)).getLeft();

        return new LeverAngles(0, 0);
    }

    @Override
    public String toString() {
        return "LeversPosition{" +
                "pair=" + pair +
                ", rightLowerTop=" + rightLowerTop +
                ", bothTop=" + bothTop +
                '}';
    }


    public static String draw(CartesianPoint p1, CartesianPoint p2) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(String.format("ctx.moveTo(%s,%s);\n", 3*Math.round(p1.x) + 300, -Math.round(p1.y)*3 + 400));
        buffer.append(String.format("ctx.lineTo(%s,%s);\n", 3*Math.round(p2.x) + 300, -Math.round(p2.y)*3 + 400));
        buffer.append("ctx.stroke();\n");

        return buffer.toString();
    }

    public static void main(String[] args) {

        CoupledTwainLeverPair pair = new CoupledTwainLeverPair();
        LeversPosition pos = new LeversPosition(pair);

        pos.calcAngles(new CartesianPoint(70, 100));

        System.out.println("pos = " + pos);
        System.out.println("pen             = " + pos.pen);
        System.out.println("rightLowerTop   = " + pos.rightLowerTop);
        System.out.println("bothTop         = " + pos.bothTop);
        System.out.println("leftLowerTop    = " + pos.leftLowerTop);


        System.out.println(draw(pair.leverRightStart, pos.rightLowerTop));
        System.out.println(draw(pos.rightLowerTop,  pos.pen));
        System.out.println(draw(pos.bothTop,        pos.pen));
        System.out.println(draw(pos.bothTop,        pos.rightLowerTop));

        System.out.println(draw(pair.leverLeftStart,  pos.leftLowerTop));
        System.out.println(draw(pos.leftLowerTop,     pos.bothTop));

    }


}

package dbg.misc.calc;

import java.util.Collection;

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

        Section leftLever = new Section(pair.leverLeftStart, leftLowerTop);
        Section rightLever = new Section(pair.leverRightStart, rightLowerTop);
        return new LeverAngles(leftLever.angleOX(), rightLever.angleOX());
    }

    public CartesianPoint leverEnd(double angle, CartesianPoint startPos, double leverLength) {
        return new CartesianPoint(
                startPos.x + Math.cos(angle) * leverLength,
                startPos.y + Math.sin(angle) * leverLength);
    }

    public CartesianPoint calcPen(LeverAngles leverAngles) {
        leftLowerTop = leverEnd(leverAngles.left, pair.leverLeftStart, pair.leverLeftLowerLength);
        rightLowerTop = leverEnd(leverAngles.right, pair.leverRightStart, pair.leverRightLowerLength);

        bothTop = new CircleIntersection(
                new Circle(pair.leverLeftUpperLength, leftLowerTop),
                new Circle(pair.leverRightUpperLength, rightLowerTop)
        ).getUpper();

        pen = new CircleIntersection(
                new Circle(pair.penToRightUpperBottomDistance, rightLowerTop),
                new Circle(pair.penToRightUpperTopDistance, bothTop)
        ).getUpper();


        return pen;
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

    static final double[][] CALIBRATION = new double[][] {

                    {0.618285, 0.192164, 60, 90    } ,
                    {0.545176, 0.162065, 40, 80    } ,
                    {0.490636, 0.074242, 20, 90    } ,
                    {0.532677, 0.070789, 30, 100   } ,
                    {0.653506, 0.170992, 70, 100   } ,
                    {0.582077, 0.193898, 50, 80    } ,
                    {0.685275, 0.181731, 80, 100   } ,
                    {0.650554, 0.250124, 70, 80    } ,
                    {0.635523, 0.103211, 60, 110   } ,
                    {0.533102, 0.002244, 20, 110   } ,


    };

    public static void calibration() {

        CoupledTwainLeverPair pair = new CoupledTwainLeverPair();
        LeversPosition pos = new LeversPosition(pair);

        for (double[] c : CALIBRATION) {

            double x = c[2];
            double y = c[3];


            LeverAngles angles = pos.calcAngles(new CartesianPoint(x, y));
            double al = angles.left;
            double ar = angles.right;

            System.out.println(String.format("%s,%s,%s,%s,%s,%s",
                    String.valueOf(c[0]),
                    String.valueOf(c[1]),
                    String.valueOf(al),
                    String.valueOf(ar),
                    String.valueOf(x),
                    String.valueOf(y)
                    ));


        }


        System.out.println();
    }


    public static void restrictedArea() {

        CoupledTwainLeverPair pair = new CoupledTwainLeverPair();

        LeversPosition position = new LeversPosition(pair);

        for (Section section : pair.workingArea.getSections()) {
            dumpSection(position, section);
        }


    }

    private static void dumpSection(LeversPosition position, Section sideSection) {
        Collection<CartesianPoint> sidePoints = sideSection.split(10);

        for (CartesianPoint sidePoint : sidePoints) {

            LeverAngles leverAngles = position.calcAngles(sidePoint);

            //System.out.println(side + " = " + sidePoint + " " + leverAngles);
            System.out.println(leverAngles.left + " " + leverAngles.right);
        }
    }

    public static void main(String[] args) {



        //calibration();

        restrictedArea();

        if(true) {
            return;
        }


        CoupledTwainLeverPair pair = new CoupledTwainLeverPair();
        LeversPosition pos = new LeversPosition(pair);

        LeverAngles leverAngles = pos.calcAngles(new CartesianPoint(70, 100));


        System.out.println("pos = " + pos);
        System.out.println("pen             = " + pos.pen);
        System.out.println("rightLowerTop   = " + pos.rightLowerTop);
        System.out.println("bothTop         = " + pos.bothTop);
        System.out.println("leftLowerTop    = " + pos.leftLowerTop);
        System.out.println("leverAngles     = " + leverAngles);


        System.out.println(draw(pair.leverRightStart, pos.rightLowerTop));
        System.out.println(draw(pos.rightLowerTop,  pos.pen));
        System.out.println(draw(pos.bothTop,        pos.pen));
        System.out.println(draw(pos.bothTop,        pos.rightLowerTop));

        System.out.println(draw(pair.leverLeftStart,  pos.leftLowerTop));
        System.out.println(draw(pos.leftLowerTop,     pos.bothTop));
        pos = new LeversPosition(pair);

        pos.calcPen(leverAngles);

        System.out.println("pen             = " + pos.pen);
        System.out.println("rightLowerTop   = " + pos.rightLowerTop);
        System.out.println("bothTop         = " + pos.bothTop);
        System.out.println("leftLowerTop    = " + pos.leftLowerTop);



    }


}

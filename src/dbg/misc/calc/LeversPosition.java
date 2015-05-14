package dbg.misc.calc;

public class LeversPosition {



    private final CoupledTwainLeverPair pair;

    CartesianPoint rightLowerTop;

    public LeversPosition(CoupledTwainLeverPair pair) {
        this.pair = pair;
    }

    public LeverAngles calcAngles(CartesianPoint pen) {

        rightLowerTop = new CircleIntersection(
                new Circle(pair.penToRightUpperTopDistance, pen),
                new Circle(pair.leverRightLowerLength, pair.leverRightStart)).getRight();




        return new LeverAngles(0, 0);
    }




}

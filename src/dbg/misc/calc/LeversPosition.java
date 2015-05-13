package dbg.misc.calc;

public class LeversPosition {

    private final CoupledTwainLeverPair pair;

    CartesianPoint rightLowerEnd;

    public LeversPosition(CoupledTwainLeverPair pair) {
        this.pair = pair;
    }

    public LeverAngles calcAngles(CartesianPoint pen) {

        rightLowerEnd = new CircleIntersection(
                new Circle(pair.penToRightLowerEndDistance, pen),
                new Circle(pair.leverRightLowerLength, pair.leverRightStart)).getRight();



    }




}

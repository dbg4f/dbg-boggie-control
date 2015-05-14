package dbg.misc.calc;



public class CoupledTwainLeverPair {

    public final CartesianPoint leverLeftStart = new CartesianPoint(0, 100/2-12);
    public final CartesianPoint leverRightStart = new CartesianPoint(0, 100/2+12);
    public final Rectangle workingArea = new Rectangle(0, 75, 100, 125);


    public final double leverLeftLowerLength = 50;
    public final double leverRightLowerLength = 50;
    public final double leverLeftUpperLength = 50;
    public final double leverRightUpperLength = 50;

    public final double penToRightUpperTopDistance = 33;
    public final double penToRightUpperBottomDistance = 75;


}

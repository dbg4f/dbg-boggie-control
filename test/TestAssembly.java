import dbg.misc.calc.CartesianPoint;
import dbg.misc.calc.CoupledTwainLeverPair;
import dbg.misc.calc.LeverAnglesSensor;
import dbg.misc.calc.LeversPosition;
import dbg.misc.calc.drive2.CommandQueue;
import dbg.misc.calc.drive2.PositioningRestrictions;

/**
 * @author bogdel on 22.01.16.
 */
public class TestAssembly {

    public final CoupledTwainLeverPair pair = new CoupledTwainLeverPair();
    public final LeversPosition position = new LeversPosition(pair);
    public final PositioningRestrictions restrictions = new PositioningRestrictions(pair, position);
    public final CommandQueue commandQueue = new CommandQueue();

    public TestAssembly() {

        position.calcAdcAngleDependency();




    }


    public static void main(String[] args) {

        TestAssembly testAssembly = new TestAssembly();

        CartesianPoint p1 = testAssembly.position.penByAdc(new LeverAnglesSensor(0.607276, 0.105258));
        CartesianPoint p2 = testAssembly.position.penByAdc(new LeverAnglesSensor(0.611484,0.101925));

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);

    }

}

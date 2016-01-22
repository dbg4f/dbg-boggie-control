import dbg.misc.calc.CoupledTwainLeverPair;
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
}

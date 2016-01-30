import dbg.misc.calc.*;
import dbg.misc.calc.drive.CncCommand;
import dbg.misc.calc.drive.CncCommandCode;
import dbg.misc.calc.drive2.CommandQueue;
import dbg.misc.calc.drive2.LeversController;
import dbg.misc.calc.drive2.PositioningRestrictions;
import dbg.misc.calc.drive2.push.FixedPwmPushCalculator;
import dbg.misc.calc.drive2.push.PushCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bogdel on 22.01.16.
 */
public class TestAssembly {

    private static Logger log = LoggerFactory.getLogger(TestAssembly.class);

    public final CoupledTwainLeverPair pair = new CoupledTwainLeverPair();
    public final LeversPosition position = new LeversPosition(pair);
    public final PositioningRestrictions restrictions = new PositioningRestrictions(pair, position);
    public final CommandQueue commandQueue = new CommandQueue();
    public final LeversController leversController = new LeversController();
    public final CoupledServoPairEmulator emulator = new CoupledServoPairEmulator();
    public final PushCalculator pushCalculator = new FixedPwmPushCalculator(0.6, 1);




    public TestAssembly() {

        position.calcAdcAngleDependency();

        leversController.setActuator(emulator);
        leversController.setCommandQueue(commandQueue);
        leversController.setPosition(position);
        leversController.setPositioningRestrictions(restrictions);
        leversController.setPushCalculator(pushCalculator);
        emulator.setController(leversController);
        emulator.setAnglesSensor(new LeverAnglesSensor(0.607276, 0.105258));
        emulator.launch();


    }


    public static void main(String[] args) {

        //testPosition();

        TestAssembly testAssembly = new TestAssembly();

        testAssembly.commandQueue.addCommand(new CncCommand(CncCommandCode.LINE_TO, 50, 100));
        testAssembly.commandQueue.addCommand(new CncCommand(CncCommandCode.LINE_TO, 60, 80));


    }

    private static void testPosition() {
        TestAssembly testAssembly = new TestAssembly();

        CartesianPoint p1 = testAssembly.position.penByAdc(new LeverAnglesSensor(0.607276, 0.105258));
        CartesianPoint p2 = testAssembly.position.penByAdc(new LeverAnglesSensor(0.611484,0.101925));

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
    }

}

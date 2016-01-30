package dbg.misc.calc;

import dbg.misc.calc.drive2.LeversActuator;
import dbg.misc.calc.drive2.LeversController;
import dbg.misc.calc.drive2.PushPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dmitri on 30.01.16.
 */
public class CoupledServoPairEmulator implements Runnable, LeversActuator {

    private static Logger log = LoggerFactory.getLogger(CoupledServoPairEmulator.class);

    private LeversController controller;

    private LeverAnglesSensor anglesSensor;

    private SensorPwmDirectionDependency dependency = SensorPwmDirectionDependency.STRAIGHT;

    public void setDependency(SensorPwmDirectionDependency dependency) {
        this.dependency = dependency;
    }

    public void setController(LeversController controller) {
        this.controller = controller;
    }

    public void setAnglesSensor(LeverAnglesSensor anglesSensor) {
        this.anglesSensor = anglesSensor;
    }

    public void launch() {

        new Thread(this).start();


    }

    @Override
    public void blinkBoth(PushPair pushPair) {

        double step = 0.001;

        pushPair.applyDepenency(dependency);

        anglesSensor = anglesSensor.move(
                dependency.applyLeft(pushPair.getLeft().getPwm() > 0 ? step : -step),
                dependency.applyRight(pushPair.getRight().getPwm() > 0 ? step : -step));

        log.info("blinkBoth: " + pushPair +  " new sensors " + anglesSensor);

    }


    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted()) {
            controller.onPositionReport(anglesSensor);

            controller.onTimer();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.info("Exit");
            }
        }

    }
}

package dbg.misc.calc.drive2;

/**
 * Created by dmitri on 10.01.16.
 */
public interface LeversActuator {


    void blinkBoth(int periodLeftMs, int periodRightMs, double pwmLeft, double pwmRight);

}

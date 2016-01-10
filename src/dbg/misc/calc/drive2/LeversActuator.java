package dbg.misc.calc.drive2;

/**
 * Created by dmitri on 10.01.16.
 */
public interface LeversActuator {

    void blinkLeft(int periodMs, double pwm);

    void blinkRight(int periodMs, double pwm);


}

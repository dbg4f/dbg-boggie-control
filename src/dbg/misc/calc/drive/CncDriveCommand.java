package dbg.misc.calc.drive;

public class CncDriveCommand {

    public static CncDriveCommand pen(boolean up) {
        return new CncDriveCommand();
    }

    public static CncDriveCommand move(boolean right, double pwm, int duration){
        return new CncDriveCommand();
    }




}

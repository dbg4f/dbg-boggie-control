package dbg.misc.calc.drive;

public enum DriveCode {
    LEFT("stepLeft"),
    RIGHT("stepRight"),
    LIFT("stepLift");

    private final String cmdCode;

    DriveCode(String cmdCode) {
        this.cmdCode = cmdCode;
    }

    public String getCmdCode() {
        return cmdCode;
    }
}

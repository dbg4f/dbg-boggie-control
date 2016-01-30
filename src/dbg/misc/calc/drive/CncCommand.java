package dbg.misc.calc.drive;

public class CncCommand {

    private final CncCommandCode code;
    private final int x;
    private final int y;

    public CncCommand(CncCommandCode code, int x, int y) {
        this.code = code;
        this.x = x;
        this.y = y;
    }

    public static CncCommand moveTo(int x, int y) {
        return new CncCommand(CncCommandCode.MOVE_TO, x, y);
    }

    public static CncCommand lineTo(int x, int y) {
        return new CncCommand(CncCommandCode.LINE_TO, x, y);
    }


    public CncCommandCode getCode() {
        return code;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "CncCommand{" +
                "code=" + code +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

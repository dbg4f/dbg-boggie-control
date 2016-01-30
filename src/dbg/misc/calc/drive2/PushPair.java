package dbg.misc.calc.drive2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dbg.misc.calc.SensorPwmDirectionDependency;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dmitri on 30.01.16.
 */
public class PushPair {
    DrivePush left;
    DrivePush right;
    long time;

    public PushPair(DrivePush left, DrivePush right) {
        this.left = left;
        this.right = right;
        this.time = System.currentTimeMillis();
    }

    public void applyDepenency(SensorPwmDirectionDependency dependency) {
        if (dependency.isLeftStraight()) {
            left = left.invertPwm();
        }

        if (dependency.isRightStraight()) {
            right = right.invertPwm();
        }
    }

    public String toJson() {

        Map<String, String> m = new LinkedHashMap<>();
        m.put("pwmLeft",        String.valueOf(left.getPwm()));
        m.put("durationLeft",   String.valueOf(left.getLengthMsec()));
        m.put("pwmRight",       String.valueOf(right.getPwm()));
        m.put("durationRight",  String.valueOf(right.getLengthMsec()));
        Type mapType = new TypeToken<Map<String, Number>>() {}.getType();

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(m, mapType);

        return json;

    }

    public DrivePush getLeft() {
        return left;
    }

    public DrivePush getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "BlinkPair{" +
                "left=" + left +
                ", right=" + right +
                ", time=" + time +
                '}';
    }
}

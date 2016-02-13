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

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("cmd",            "stepBoth");
        m.put("pwmLeft",        left.getPwm());
        m.put("durationLeft",   left.getLengthMsec());
        m.put("pwmRight",       right.getPwm());
        m.put("durationRight",  right.getLengthMsec());



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

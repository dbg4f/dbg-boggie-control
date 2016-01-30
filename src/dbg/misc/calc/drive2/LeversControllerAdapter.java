package dbg.misc.calc.drive2;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import dbg.misc.calc.CoupledTwainLeverPair;
import dbg.misc.calc.LeverAnglesSensor;
import dbg.misc.calc.LeversPosition;
import dbg.misc.calc.drive.CncCommand;
import dbg.misc.calc.drive.CncCommandCode;
import dbg.misc.calc.drive.CncSensors;
import dbg.misc.calc.drive2.push.FixedPwmPushCalculator;
import dbg.misc.calc.drive2.push.PushCalculator;
import dbg.misc.ws.MessageConsumer;
import dbg.misc.ws.MessageFlowMediator;
import dbg.misc.ws.serial.SerialRead;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dmitri on 30.01.16.
 */
public class LeversControllerAdapter implements MessageConsumer, LeversActuator, Runnable {

    private LeversController leversController;

    private CommandQueue commandQueue;

    private static LeversControllerAdapter instance = new LeversControllerAdapter();

    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted()) {

            leversController.onTimer();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                break;
            }

        }

    }

    public LeversControllerAdapter() {
        setup();
        new Thread(this).start();
    }

    public static LeversControllerAdapter getInstance() {
        return instance;
    }




    public void setup() {
         final CoupledTwainLeverPair pair = new CoupledTwainLeverPair();
         final LeversPosition position = new LeversPosition(pair);
         final PositioningRestrictions restrictions = new PositioningRestrictions(pair, position);
         commandQueue = new CommandQueue();
         leversController = new LeversController();
         final PushCalculator pushCalculator = new FixedPwmPushCalculator(0.6, 1);

        position.calcAdcAngleDependency();

        leversController.setActuator(this);
        leversController.setCommandQueue(commandQueue);
        leversController.setPosition(position);
        leversController.setPositioningRestrictions(restrictions);
        leversController.setPushCalculator(pushCalculator);


        MessageFlowMediator.getInstance().registerConsumer(this);

    }

    private boolean isConforms(Class cl, Set<String> fields) {

        Set<String> classFields = classFields(cl);

        classFields.removeAll(fields);

        return classFields.isEmpty(); // provided fields covers all class fields

    }

    public void moveTo(int x, int y) {
        commandQueue.addCommand(new CncCommand(CncCommandCode.LINE_TO, x, y));
    }

    public LeverAnglesSensor getSensors() {
        return leversController.getLastSensors();
    }

    private Set<String> classFields(Class cl) {
        Set<String> fields = new LinkedHashSet<String>();
        for (Field field : cl.getDeclaredFields()) {
            fields.add(field.getName());
        }
        return fields;
    }

    @Override
    public void blinkBoth(PushPair pushPair) {
        MessageFlowMediator.getInstance().senToTarget(pushPair.toJson(), SerialRead.class);
    }

    @Override
    public void onMessage(String message) throws IOException {

        Gson gson = new Gson();

        JsonParser jsonParser = new JsonParser();

        try {

            jsonParser.parse(message);

            Type mapType = new TypeToken<Map<String, String>>() {}.getType();

            Map<String, String> messageImage = gson.fromJson(message, mapType);

            if (messageImage == null) {
                messageImage = new LinkedHashMap<>();
            }

            if (isConforms(CncSensors.class, messageImage.keySet())) {

                Type rowType = new TypeToken<CncSensors>() {}.getType();

                CncSensors sensors = gson.fromJson(message, rowType);

                leversController.onPositionReport(new LeverAnglesSensor(sensors.left, sensors.right, sensors.t));

            }
        } catch (JsonSyntaxException e) {

            // ignored

        }


    }


}

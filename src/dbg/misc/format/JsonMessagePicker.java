package dbg.misc.format;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dbg.misc.calc.CncCalc;
import dbg.misc.calc.PosSensors;
import dbg.misc.calc.TwainLever;
import dbg.misc.ws.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

import static dbg.misc.calc.CncCalc.*;

/**
 * @author bogdel
 */
public class JsonMessagePicker implements MessageConsumer{

  private static Logger log = LoggerFactory.getLogger(JsonMessagePicker.class);

  // {"t":159144,"pwmNear":0.700000,"pwmFar":0.000000,"posNear":0.215721,"posFar":0.140734}

  // {"counter":24234,"near":0.162791,"far":0.055090,"t":2503082}

  private CurrentPositionSnapshot snapshot;

  private List<PositionLogRow> logsRows = new ArrayList<PositionLogRow>();

  private List<Map<String, String>> unrecognizedMessages = new ArrayList<>();

  private List<String> invalidMessages = new ArrayList<>();

  private List<TimedMarker> timedMarkers = new ArrayList<>();

  private List<PositionReport> positionReports = new ArrayList<>();


    TwainLever lever = new TwainLever(NEAR, FAR, ANGLE_XAB_BY_SENSOR_NEAR, ANGLE_ABC_BY_SENSOR_FAR);


    @Override
    public void onMessage(String message) throws IOException {
        probe(message);
    }

    public void probe(String rawInput) {

    Gson gson = new Gson();

    JsonParser jsonParser = new JsonParser();

    try {

      jsonParser.parse(rawInput);

      Type mapType = new TypeToken<Map<String, String>>(){}.getType();

      Map<String, String> messageImage = gson.fromJson(rawInput, mapType);

      if (messageImage == null) {
        messageImage = new LinkedHashMap<String, String>();
      }

      if (isConforms(PositionLogRow.class, messageImage.keySet())) {

        Type rowType = new TypeToken<PositionLogRow>(){}.getType();

        PositionLogRow row = gson.fromJson(rawInput, rowType);

        logsRows.add(row);

      }
      if (isConforms(TimedMarker.class, messageImage.keySet())) {

        Type rowType = new TypeToken<TimedMarker>(){}.getType();

        TimedMarker row = gson.fromJson(rawInput, rowType);

        timedMarkers.add(row);

      }
      if (isConforms(PositionReport.class, messageImage.keySet())) {

        Type rowType = new TypeToken<PositionReport>(){}.getType();

         PositionReport row = gson.fromJson(rawInput, rowType);

        positionReports.add(row);

      }
      else if (isConforms(CurrentPositionSnapshot.class, messageImage.keySet())) {

        Type rowType = new TypeToken<CurrentPositionSnapshot>(){}.getType();

        snapshot = gson.fromJson(rawInput, rowType);

      }
      else {

        unrecognizedMessages.add(messageImage);

      }

    }
    catch (JsonSyntaxException e) {

      log.error("JSON message is not recognized as correct: " + rawInput + " " + e.getMessage(), e);

      invalidMessages.add(rawInput);

    }


  }


  private boolean isConforms(Class cl, Set<String> fields) {

    Set<String> classFields = classFields(cl);

    classFields.removeAll(fields);

    return classFields.isEmpty(); // provided fields covers all class fields

  }

  private Set<String> classFields(Class cl) {
    Set<String> fields = new LinkedHashSet<String>();
    for (Field field : cl.getDeclaredFields()) {
      fields.add(field.getName());
    }
    return fields;
  }


  public void reset() {
      logsRows.clear();
      timedMarkers.clear();
      positionReports.clear();
  }


  public String log() {
      JsonArray array = new JsonArray();
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      for (PositionLogRow positionLogRow :logsRows) {
          array.add(gson.toJsonTree(positionLogRow));
      }

      return gson.toJson(array);
  }

  public String markers() {
      JsonArray array = new JsonArray();
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      for (TimedMarker timedMarker : timedMarkers) {
          array.add(gson.toJsonTree(timedMarker));
      }

      return gson.toJson(array);
  }

  public String positions() {
      JsonArray array = new JsonArray();
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      for (PositionReport positionReport : positionReports) {
          array.add(gson.toJsonTree(positionReport));
      }

      return gson.toJson(array);
  }

  public String sensors() {
      JsonArray array = new JsonArray();
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      List<PosSensors> dependency = TimedMarkerUtils.merge(timedMarkers);

      for (PosSensors posSensors : dependency) {
          array.add(gson.toJsonTree(posSensors));
      }

      return gson.toJson(array);
  }

  public String points() {
      JsonArray array = new JsonArray();
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      double currentFar = 0;
      double currentNear = 0;


      for (TimedMarker timedMarker : timedMarkers) {


          PosSensors posSensors = new PosSensors(currentNear, currentFar);
          if (timedMarker.getTypeCode() == 102) {
              currentFar = timedMarker.getValue();
              array.add(gson.toJsonTree(lever.position(posSensors).toCartesianPoint()));
          }
          if (timedMarker.getTypeCode() == 110) {
              currentNear = timedMarker.getValue();
              array.add(gson.toJsonTree(lever.position(posSensors).toCartesianPoint()));
          }

      }

      return gson.toJson(array);
  }



   /*
  public static void main(String[] args) {

    JsonMessagePicker picker = new JsonMessagePicker();

    picker.probe("{\"t\":159144,\"pwmNear\":0.700000,\"pwmFar\":0.000000,\"posNear\":0.215721,\"posFar\":0.140734}");
    picker.probe("{\"t\":159122,\"pwmNear\":0.99999,\"pwmFar\":0.77777,\"posNear\":0.55555,\"posFar\":0.5553333}");
    picker.probe("{\"counter\":24234,\"near\":0.162791,\"far\":0.055090,\"t\":2503082}");
    picker.probe("{\"counter1\":1,\"near\":0.162791,\"far\":0.055090,\"t\":2503082}");
    picker.probe("AAAAAAAAAAAAAAa");

    System.out.println("picker = " + picker);

    System.out.println("picker = " + picker.log());



  }
  */

  public List<PositionLogRow> getLogsRows() {
    return logsRows;
  }

  public List<Map<String, String>> getUnrecognizedMessages() {
    return unrecognizedMessages;
  }

  public List<String> getInvalidMessages() {
    return invalidMessages;
  }

  public List<TimedMarker> getTimedMarkers() {
    return timedMarkers;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("JsonMessagePicker{");
    sb.append("snapshot=").append(snapshot);
    sb.append(", logsRows=").append(logsRows);
    sb.append('}');
    return sb.toString();
  }
}

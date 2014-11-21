package dbg.misc.ws;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * @author bogdel
 */
public class PosCalc {

  public static final Pair LEVER_LENGTHS = new Pair(87.0, 160.0);


  public static final Pair NEAR_CALIBRATION_POINT1 = new Pair(degreesToAngle(90.0), 0.3165); // degrees - sensor
  public static final Pair NEAR_CALIBRATION_POINT2 = new Pair(degreesToAngle(33.0), 0.179);

  public static final double NEAR_CALIBRATION_POS = 0.179;   //

  public static final Pair FAR_CALIBRATION_POINT1 = new Pair(degreesToAngle(0.0), 0.1008);
  public static final Pair FAR_CALIBRATION_POINT2 = new Pair(degreesToAngle(46.0), 0.24435);

  static class Pair {
    final double near;
    final double far;

    Pair(double near, double far) {
      this.near = near;
      this.far = far;
    }

    @Override
    public String toString() {
      return "Pair{" +
             "near=" + near +
             ", far=" + far +
             '}';
    }
  }

  private static Pair calcCaretPos(Pair sensors) {

    double angleNear = interpolateFindNear(sensors.near, NEAR_CALIBRATION_POINT1, NEAR_CALIBRATION_POINT2);

    double angleFar = interpolateFindNear(sensors.far, FAR_CALIBRATION_POINT1, FAR_CALIBRATION_POINT2);

    Pair pointNearLever = new Pair(LEVER_LENGTHS.near * Math.cos(angleNear), LEVER_LENGTHS.near * Math.sin(angleNear));
    Pair pointFarLeverFromStart = new Pair(LEVER_LENGTHS.far * Math.cos(angleFar), LEVER_LENGTHS.far * Math.sin(angleFar));

    return new Pair(pointNearLever.near + pointFarLeverFromStart.near, pointNearLever.far + pointFarLeverFromStart.far);
  }

  private static double interpolateFindNear(double far, Pair calibrationPair1, Pair calibrationPair2) {
      // y = k*x + c
      // k = tg(alpha)
      // tg(a) = dy/dx
      // c = y - k*x
      // x = (y - c) / k

    double k = (calibrationPair2.far - calibrationPair1.far) / (calibrationPair2.near - calibrationPair1.near);
    double c = calibrationPair1.far - k * calibrationPair1.near;

    return (far - c) / k;

  }

  private static double degreesToAngle(double degrees) {
    return (2.0*Math.PI / 360.0) * degrees;
  }

  private static double angleToDegrees(double angle) {
    return (360.0 / (2.0*Math.PI)) * angle;
  }



  public static final String LOG = "[\n" +
                                   "{\"t\":2501222,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.212824,\"posFar\":0.152784}\n" +
                                   ",{\"t\":2501224,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.212873,\"posFar\":0.152789}\n" +
                                   ",{\"t\":2501225,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.213051,\"posFar\":0.152839}\n" +
                                   ",{\"t\":2501227,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.213159,\"posFar\":0.152885}\n" +
                                   ",{\"t\":2501229,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.212978,\"posFar\":0.152822}\n" +
                                   ",{\"t\":2501231,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.212831,\"posFar\":0.152769}\n" +
                                   ",{\"t\":2501232,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.212720,\"posFar\":0.152744}\n" +
                                   ",{\"t\":2501234,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.212622,\"posFar\":0.152706}\n" +
                                   ",{\"t\":2501236,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.212740,\"posFar\":0.152798}\n" +
                                   ",{\"t\":2501238,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.212792,\"posFar\":0.152779}\n" +
                                   ",{\"t\":2501239,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212647,\"posFar\":0.152474}\n" +
                                   ",{\"t\":2501241,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212778,\"posFar\":0.152509}\n" +
                                   ",{\"t\":2501243,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212755,\"posFar\":0.152564}\n" +
                                   ",{\"t\":2501245,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212740,\"posFar\":0.152558}\n" +
                                   ",{\"t\":2501246,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.213078,\"posFar\":0.152465}\n" +
                                   ",{\"t\":2501248,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212915,\"posFar\":0.151269}\n" +
                                   ",{\"t\":2501250,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212752,\"posFar\":0.151012}\n" +
                                   ",{\"t\":2501252,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212657,\"posFar\":0.150854}\n" +
                                   ",{\"t\":2501253,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212511,\"posFar\":0.150619}\n" +
                                   ",{\"t\":2501255,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212515,\"posFar\":0.150320}\n" +
                                   ",{\"t\":2501257,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212708,\"posFar\":0.149894}\n" +
                                   ",{\"t\":2501259,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212712,\"posFar\":0.149386}\n" +
                                   ",{\"t\":2501260,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212700,\"posFar\":0.148985}\n" +
                                   ",{\"t\":2501262,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212737,\"posFar\":0.148573}\n" +
                                   ",{\"t\":2501264,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212740,\"posFar\":0.148023}\n" +
                                   ",{\"t\":2501266,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.213037,\"posFar\":0.147248}\n" +
                                   ",{\"t\":2501268,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212998,\"posFar\":0.146311}\n" +
                                   ",{\"t\":2501269,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212822,\"posFar\":0.145458}\n" +
                                   ",{\"t\":2501271,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212680,\"posFar\":0.144608}\n" +
                                   ",{\"t\":2501273,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212470,\"posFar\":0.143963}\n" +
                                   ",{\"t\":2501275,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212393,\"posFar\":0.143323}\n" +
                                   ",{\"t\":2501276,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212645,\"posFar\":0.142580}\n" +
                                   ",{\"t\":2501278,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212535,\"posFar\":0.141570}\n" +
                                   ",{\"t\":2501280,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212523,\"posFar\":0.140627}\n" +
                                   ",{\"t\":2501282,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.212079,\"posFar\":0.139725}\n" +
                                   ",{\"t\":2501283,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.211508,\"posFar\":0.138897}\n" +
                                   ",{\"t\":2501285,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.210643,\"posFar\":0.138195}\n" +
                                   ",{\"t\":2501287,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.210301,\"posFar\":0.137412}\n" +
                                   ",{\"t\":2501289,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.210004,\"posFar\":0.136649}\n" +
                                   ",{\"t\":2501290,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.209117,\"posFar\":0.135970}\n" +
                                   ",{\"t\":2501292,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.207256,\"posFar\":0.135230}\n" +
                                   ",{\"t\":2501294,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.196786,\"posFar\":0.134585}\n" +
                                   ",{\"t\":2501296,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.196117,\"posFar\":0.133855}\n" +
                                   ",{\"t\":2501297,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.195698,\"posFar\":0.132735}\n" +
                                   ",{\"t\":2501299,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.195100,\"posFar\":0.131656}\n" +
                                   ",{\"t\":2501301,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.194252,\"posFar\":0.130547}\n" +
                                   ",{\"t\":2501303,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.193867,\"posFar\":0.129589}\n" +
                                   ",{\"t\":2501304,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.193423,\"posFar\":0.128591}\n" +
                                   ",{\"t\":2501306,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.193022,\"posFar\":0.127596}\n" +
                                   ",{\"t\":2501308,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.192218,\"posFar\":0.126409}\n" +
                                   ",{\"t\":2501310,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.191519,\"posFar\":0.125238}\n" +
                                   ",{\"t\":2501311,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.190771,\"posFar\":0.124103}\n" +
                                   ",{\"t\":2501313,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.189985,\"posFar\":0.122997}\n" +
                                   ",{\"t\":2501315,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.189490,\"posFar\":0.121871}\n" +
                                   ",{\"t\":2501317,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.189230,\"posFar\":0.120690}\n" +
                                   ",{\"t\":2501318,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.188606,\"posFar\":0.119277}\n" +
                                   ",{\"t\":2501320,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.188138,\"posFar\":0.117775}\n" +
                                   ",{\"t\":2501322,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.187466,\"posFar\":0.116410}\n" +
                                   ",{\"t\":2501324,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.186912,\"posFar\":0.115152}\n" +
                                   ",{\"t\":2501325,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.186464,\"posFar\":0.113895}\n" +
                                   ",{\"t\":2501327,\"pwmNear\":0.700000,\"pwmFar\":-0.500000,\"posNear\":0.186426,\"posFar\":0.112290}\n" +
                                   ",{\"t\":2501329,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.185986,\"posFar\":0.111041}\n" +
                                   ",{\"t\":2501331,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.185568,\"posFar\":0.109879}\n" +
                                   ",{\"t\":2501332,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.184555,\"posFar\":0.108452}\n" +
                                   ",{\"t\":2501334,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.183645,\"posFar\":0.107309}\n" +
                                   ",{\"t\":2501336,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.183011,\"posFar\":0.106062}\n" +
                                   ",{\"t\":2501338,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.182635,\"posFar\":0.104689}\n" +
                                   ",{\"t\":2501339,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.181907,\"posFar\":0.103275}\n" +
                                   ",{\"t\":2501341,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.181242,\"posFar\":0.101985}\n" +
                                   ",{\"t\":2501343,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.180795,\"posFar\":0.100809}\n" +
                                   ",{\"t\":2501345,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.180504,\"posFar\":0.099701}\n" +
                                   ",{\"t\":2501346,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.180345,\"posFar\":0.098538}\n" +
                                   ",{\"t\":2501348,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.179689,\"posFar\":0.097163}\n" +
                                   ",{\"t\":2501350,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.179538,\"posFar\":0.096057}\n" +
                                   ",{\"t\":2501352,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.178846,\"posFar\":0.094781}\n" +
                                   ",{\"t\":2501353,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.178248,\"posFar\":0.093526}\n" +
                                   ",{\"t\":2501355,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.177673,\"posFar\":0.092265}\n" +
                                   ",{\"t\":2501357,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.177380,\"posFar\":0.091099}\n" +
                                   ",{\"t\":2501359,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.171276,\"posFar\":0.089928}\n" +
                                   ",{\"t\":2501360,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.167074,\"posFar\":0.088859}\n" +
                                   ",{\"t\":2501362,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.166656,\"posFar\":0.087787}\n" +
                                   ",{\"t\":2501364,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.166239,\"posFar\":0.086792}\n" +
                                   ",{\"t\":2501366,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.166303,\"posFar\":0.085834}\n" +
                                   ",{\"t\":2501367,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.165971,\"posFar\":0.084901}\n" +
                                   ",{\"t\":2501369,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.165498,\"posFar\":0.083989}\n" +
                                   ",{\"t\":2501371,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.165100,\"posFar\":0.083050}\n" +
                                   ",{\"t\":2501373,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.164811,\"posFar\":0.082173}\n" +
                                   ",{\"t\":2501374,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.164497,\"posFar\":0.081282}\n" +
                                   ",{\"t\":2501376,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.164686,\"posFar\":0.080371}\n" +
                                   ",{\"t\":2501378,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.164474,\"posFar\":0.079533}\n" +
                                   ",{\"t\":2501380,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.164350,\"posFar\":0.078691}\n" +
                                   ",{\"t\":2501381,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.164169,\"posFar\":0.077894}\n" +
                                   ",{\"t\":2501383,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.164042,\"posFar\":0.077128}\n" +
                                   ",{\"t\":2501385,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.163809,\"posFar\":0.076387}\n" +
                                   ",{\"t\":2501387,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.163922,\"posFar\":0.075676}\n" +
                                   ",{\"t\":2501388,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.163615,\"posFar\":0.074903}\n" +
                                   ",{\"t\":2501390,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.163471,\"posFar\":0.074119}\n" +
                                   ",{\"t\":2501392,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.163154,\"posFar\":0.073268}\n" +
                                   ",{\"t\":2501394,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.162994,\"posFar\":0.072560}\n" +
                                   ",{\"t\":2501395,\"pwmNear\":0.000000,\"pwmFar\":0.000000,\"posNear\":0.162774,\"posFar\":0.071667}\n" +
                                   " ]";


  public static void main(String[] args) {

    JsonArray entries = (JsonArray)new JsonParser().parse(LOG);



    for (int i=0; i< entries.size(); i++) {

      Pair p = calcCaretPos(new Pair(entries.get(i).getAsJsonObject().get("posNear").getAsDouble(),entries.get(i).getAsJsonObject().get("posFar").getAsDouble()));

      System.out.println(", " + new Gson().toJson(p));

    }

    //Pair p = calcCaretPos(new Pair(0.213078,0.152465));



  }




}

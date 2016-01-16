package dbg.misc.calc.drive2;

import dbg.misc.calc.*;

/**
 * Created by dmitri on 16.01.16.
 */
public class PositioningRestrictions {

    private final CoupledTwainLeverPair pair;
    private final LeversPosition position;

    public PositioningRestrictions(CoupledTwainLeverPair pair, LeversPosition position) {
        this.pair = pair;
        this.position = position;
    }

    public boolean inBorders(LeverAnglesSensor sensor) {

        CartesianPoint point = position.penByAdc(sensor);

        CoordinateRelation[] coordinateRelations = pair.workingArea.pointRelation(point);

        return false;


    }

}

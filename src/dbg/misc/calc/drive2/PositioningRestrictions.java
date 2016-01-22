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

        return inBorders(position.penByAdc(sensor));
    }

    public boolean inBorders(CartesianPoint point) {


        CoordinateRelation[] coordinateRelations = pair.workingArea.pointRelation(point);

        return
                coordinateRelations[0] == CoordinateRelation.MATCHES
                        && coordinateRelations[1] == CoordinateRelation.MATCHES;
    }

}

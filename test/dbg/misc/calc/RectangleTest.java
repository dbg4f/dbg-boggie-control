package dbg.misc.calc;

import org.junit.Assert;
import org.junit.Test;

import static dbg.misc.calc.CoordinateRelation.AFTER;
import static dbg.misc.calc.CoordinateRelation.BEFORE;
import static dbg.misc.calc.CoordinateRelation.MATCHES;


/**
 * @author bogdel on 22.01.16.
 */
public class RectangleTest {


    @Test
    public void testRelation() throws Exception {
        Rectangle rectangle = new Rectangle(2, 2, 4, 4);

        assertRelation(rectangle, 0, 0, BEFORE, BEFORE);
        assertRelation(rectangle, 5, 5, AFTER, AFTER);
        assertRelation(rectangle, 3, 3, MATCHES, MATCHES);

        assertRelation(rectangle, 0, 3, BEFORE, MATCHES);
        assertRelation(rectangle, 5, 3, AFTER, MATCHES);

        assertRelation(rectangle, 3, 0, MATCHES, BEFORE);
        assertRelation(rectangle, 3, 5, MATCHES, AFTER);

    }

    private void assertRelation(Rectangle rectangle, int x, int y, CoordinateRelation relX, CoordinateRelation relY) {

        CoordinateRelation[] coordinateRelations1 = rectangle.pointRelation(new CartesianPoint(x, y));
        Assert.assertEquals(coordinateRelations1[0], relX);
        Assert.assertEquals(coordinateRelations1[1], relY);

    }

}
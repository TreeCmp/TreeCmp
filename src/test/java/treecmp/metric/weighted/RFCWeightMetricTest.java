package treecmp.metric.weighted;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import pal.tree.Tree;
import treecmp.metric.RFClusterMetric;
import treecmp.test.util.TreeCreator;

class RFCWeightMetricTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetDistanceForTheSameTreesResult_0() {
        RFCWeightMetric instance = new RFCWeightMetric();
        RFClusterMetric rc = new RFClusterMetric();

        Tree su1 = TreeCreator.getWeightedSimpleUnitT1();
        Tree su2 = TreeCreator.getWeightedSimpleUnitT2();
        double rc1 = rc.getDistance(su1, su2);
        double inst1 = instance.getDistance(su1, su2);
        assertEquals(rc1, inst1, 0.0);
    }
}
package treecmp.metric;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import pal.tree.Tree;
import treecmp.test.util.TreeCreator;

class RMASTMetricTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetDistanceForTheSameTreesResult_0() {
        Tree t1 = TreeCreator.getTreeFromString("((a,b),(c,d));");
        Tree t2 = TreeCreator.getTreeFromString("((a,b),(c,d));");
        RMASTMetric instance = new RMASTMetric();
        double expResult = 0.0;
        double result = instance.getDistance(t1, t2);
        assertEquals(expResult, result, 0.0);
    }
}
package treecmp.metric;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import pal.tree.Tree;
import treecmp.test.util.TreeCreator;

class CopheneticL2MetricTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test of getDistance method, of class CopheneticL2Metric.
     */
    @Test
    public void testGetDistanceForTheSameTreesResult_0() {
        Tree t1 = TreeCreator.getTreeFromString("((a,b),(c,d));");
        Tree t2 = TreeCreator.getTreeFromString("((a,b),(c,d));");
        CopheneticL2Metric instance = new CopheneticL2Metric();
        double expResult = 0.0;
        double result = instance.getDistance(t1, t2);
        assertEquals(expResult, result, 0.0);
    }
    @Test
    public void testGetDistanceForDifferentTreesResult_4() {
        Tree t1 = TreeCreator.getTreeFromString("((a,b),((c,d),e));");
        Tree t2 = TreeCreator.getTreeFromString("((a,c),((b,e),d));");
        CopheneticL2Metric instance = new CopheneticL2Metric();
        double expResult = 4.0;
        double result = instance.getDistance(t1, t2);
        assertEquals(expResult, result, 0.01);
    }
}
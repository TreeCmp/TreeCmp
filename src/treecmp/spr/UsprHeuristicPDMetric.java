/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package treecmp.spr;

import treecmp.metric.Metric;
import treecmp.metric.NodalL2Metric;
import treecmp.metric.RFMetric;

/**
 *
 * @author Damian
 */
public class UsprHeuristicPDMetric extends UsprHeuristicBaseMetric {

    @Override
    protected Metric getMetric() { return new NodalL2Metric(); }
}


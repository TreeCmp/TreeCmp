/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package treecmp.spr;

import treecmp.metric.MatchingPairUnrootedMetric;
import treecmp.metric.MatchingTripletMetric;
import treecmp.metric.Metric;
import treecmp.metric.RFMetric;

/**
 *
 * @author Damian
 */
public class UsprHeuristicMPUMetric extends UsprHeuristicBaseMetric {

    @Override
    protected Metric getMetric() { return new MatchingPairUnrootedMetric(); }
}



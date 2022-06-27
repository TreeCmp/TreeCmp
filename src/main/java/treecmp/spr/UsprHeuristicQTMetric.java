/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package treecmp.spr;

import treecmp.metric.Metric;
import treecmp.metric.QuartetMetricDouble;
import treecmp.metric.QuartetMetricLong;
import treecmp.metric.RFMetric;

/**
 *
 * @author Damian
 */
public class UsprHeuristicQTMetric extends UsprHeuristicBaseMetric {

    @Override
    protected Metric getMetric(){
        return new QuartetMetricLong();
    }
}


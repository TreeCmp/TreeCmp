/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package treecmp.spr;

import treecmp.metric.Metric;
import treecmp.metric.RFMetric;
import treecmp.metric.UmastMetric;

/**
 *
 * @author Damian
 */
public class UsprHeuristicUMMetric extends UsprHeuristicBaseMetric {

    @Override
    protected Metric getMetric(){
        return new UmastMetric();
    }
}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package treecmp.spr;

import treecmp.metric.Metric;
import treecmp.metric.MatchingPairMetric;


/**
 *
 * @author Damian
 */
public class SprHeuristicMpRfcMetric extends SprHeuristicRfcBaseMetric{

 @Override
 protected Metric getMetric(){
    return new MatchingPairMetric();
 }
}

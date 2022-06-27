/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package treecmp.spr;

import treecmp.metric.CopheneticL2Metric;
import treecmp.metric.Metric;

/**
 *
 * @author Damian
 */
public class SprHeuristicCophMetric extends SprHeuristicBaseMetric{

 @Override
protected Metric getMetric(){
    return new CopheneticL2Metric();
 }
}

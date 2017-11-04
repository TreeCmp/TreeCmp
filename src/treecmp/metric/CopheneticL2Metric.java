/**
 * This file is part of TreeCmp, a tool for comparing phylogenetic trees using
 * the Matching Split distance and other metrics. Copyright (C) 2011, Damian
 * Bogdanowicz
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package treecmp.metric;

import pal.misc.IdGroup;
import pal.tree.Node;
import pal.tree.Tree;
import pal.tree.TreeUtils;
import treecmp.common.TreeCmpUtils;


public class CopheneticL2Metric extends BaseMetric implements Metric {

    public CopheneticL2Metric() {
        super();
    }

    public double getDistance(Tree t1, Tree t2) {

        int extT1Num = t1.getExternalNodeCount();
        int extT2Num = t2.getExternalNodeCount();
        if (extT1Num <= 2) {
            return 0.0;
        }
        IdGroup id1 = TreeUtils.getLeafIdGroup(t1);
        int[][] lcaMatrix1 = TreeCmpUtils.calcLcaMatrix(t1, null);
        int[][] lcaMatrix2 = TreeCmpUtils.calcLcaMatrix(t2, id1);

        int intT1Num = t1.getInternalNodeCount();
        int intT2Num = t2.getInternalNodeCount();

        Node[] preOrderT1 = TreeCmpUtils.getNodesInPreOrder(t1);
        Node[] preOrderT2 = TreeCmpUtils.getNodesInPreOrder(t2);

        short[] intDepthT1 = new short[intT1Num];
        short[] intDepthT2 = new short[intT2Num];

        short[] extDepthT1 = new short[extT1Num];
        short[] extDepthT2 = new short[extT2Num];

        TreeCmpUtils.calcNodeDepth(t1, preOrderT1, extDepthT1, intDepthT1);
        TreeCmpUtils.calcNodeDepth(t2, preOrderT2, extDepthT2, intDepthT2);

        double diff, dist = 0.0;
        int xNodeNum, yNodeNum, xyNodeNumT1, xyNodeNumT2;
        Node xNode, yNode;
        for (int i = 0; i < extT1Num; i++) {
            xNode = t1.getExternalNode(i);
            xNodeNum = xNode.getNumber();
            for (int j = i + 1; j < extT1Num; j++) {
                yNode = t1.getExternalNode(j);
                yNodeNum = yNode.getNumber();
                xyNodeNumT1 = lcaMatrix1[xNodeNum][yNodeNum];
                xyNodeNumT2 = lcaMatrix2[xNodeNum][yNodeNum];

                diff = intDepthT1[xyNodeNumT1] - intDepthT2[xyNodeNumT2];
                dist += diff * diff;
            }
        }
        for (int i = 0; i < extT1Num; i++) {
            xNode = t1.getExternalNode(i);
            xNodeNum = xNode.getNumber();
            diff = extDepthT1[xNodeNum] - extDepthT2[xNodeNum];
            dist += diff * diff;
        }

        return Math.sqrt(dist);
    }
}

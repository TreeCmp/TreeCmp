/** This file is part of TreeCmp, a tool for comparing phylogenetic trees
    using the Matching Split distance and other metrics.
    Copyright (C) 2011,  Damian Bogdanowicz

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package treecmp.command;

import pal.tree.Tree;
import pal.tree.TreeParseException;
import treecmp.common.*;
import treecmp.config.ActiveMetricsSet;
import treecmp.io.ResultWriter;
import treecmp.io.TreeReader;
import treecmp.metrics.Metric;

import java.util.ArrayList;

public class RunWCommand extends Command {

    public RunWCommand(int paramNumber, String name) {
        super(paramNumber, name);
    }

    public RunWCommand(int paramNumber, String name,int paramValue) {
        super(paramNumber, name);
        this.param=paramValue;        
    }

    @Override
    public void run() throws TreeCmpException, TreeParseException {
        super.run();

        out.init();
        reader.open();

        windowCompareExecute(reader, this.getParam(), out);

        reader.close();
        out.close();
    }

    private void windowCompareEx(TreeReader reader, int winSize, ResultWriter out, StatCalculator[] metrics) throws TreeCmpException, TreeParseException {

        Tree tree1, tree2;
        ArrayList<Tree> tree_vec = new ArrayList<Tree>();
        Object[] row;
        int k, num, base;
        int n = 0;
        double val=0;
        int mSize = metrics.length;
        ReportUtils.setRowCount(mSize);

        //initialize summary stat calculators
        SummaryStatCalculator[] sStatCalc=new SummaryStatCalculator[mSize];
        for(int i=0;i<mSize;i++){
            sStatCalc[i]=new SummaryStatCalculator(metrics[i]);
        }
        
        Object[] head = ReportUtils.getHeaderRow(metrics);
        out.setRow(head);
        out.write();

        AlignWriter aw = new AlignWriter();
        aw.initFiles(metrics);

        int numberOfTreas=reader.getEffectiveNumberOfTrees();

        int nWin = numberOfTreas/winSize;
        int lastWinNum = numberOfTreas%winSize;
        long maxIt = ((long)winSize*((long)winSize-1)/2)*(long)nWin+((long)lastWinNum*((long)lastWinNum-1)/2);

        long counter = 1;
        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxVal(maxIt);
        progress.setPrintInterval(600);
        progress.setPrintPercentInterval(5.0);
        progress.init();

        //System.out.println(head);
        num = 0;
        do {
            n = 0;
            tree_vec.clear();

            do {
                tree1 = reader.readNextTree();
                if (tree1 != null) {
                    tree_vec.add(tree1);
                    n++;
                }
            } while (tree1 != null && n < winSize);

            //comparing all pairs in vector
            int N = tree_vec.size();

            if (N > 1) {
                for (k = 0; k < metrics.length; k++) {
                    metrics[k].clear();
                }
                
                for (int i = 0; i < N; i++) {
                    for (int j = i + 1; j < N; j++) {
                        tree1 = tree_vec.get(i);
                        tree2 = tree_vec.get(j);

                        for (k = 0; k < metrics.length; k++) {
                            val = metrics[k].getDistance(tree1, tree2, i + 1, j + 1) ;
                            sStatCalc[k].insertValue(val);
                        }
                        //print row statistic
                        base = num + 1;
                        row = ReportUtils.getResultRow((int)counter, base+ i, base + j, metrics, sackin_ind_vec, sackin_unrooted_ind_vec);
                        out.setRow(row);
                        out.write();

                        aw.writeAlignments((int)counter, base+ i, base + j, metrics);

                        progress.displayProgress(counter);
                        counter++;
                    }
                }
                num += winSize;
            }
        } while (tree1 != null);

        aw.closeFiles(metrics);
        //print summary data to file
        SummaryStatCalculator.printSummary(out, sStatCalc);
    }

    public void windowCompareExecute(TreeReader reader, int winSize, ResultWriter out) throws TreeCmpException, TreeParseException {

        Metric[] metrics=ActiveMetricsSet.getActiveMetricsSet().getActiveMetricsTable();
        StatCalculator[] statsMetrics=new StatCalculator[metrics.length];

        for(int i = 0; i < metrics.length;i++){
            statsMetrics[i]=new StatCalculator(metrics[i]);
        }
        if (ioSet.isGenSackinIndexes()) {
            countSackinIndexes(reader);
        }
        windowCompareEx(reader, winSize, out, statsMetrics);
    }
}

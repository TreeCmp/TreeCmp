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

import pal.tree.TreeParseException;
import treecmp.common.*;
import treecmp.config.ActiveMetricsSet;
import treecmp.io.ResultWriter;
import treecmp.io.TreeReader;
import treecmp.metrics.Metric;

public class RunSCommand extends Command {

    public RunSCommand(int paramNumber, String name) {
        super(paramNumber, name);
    }

    @Override
    public void run() throws TreeCmpException, TreeParseException {
        super.run();
        out.init();
        reader.open();
        
        pairCompareExecute(reader, out);
        
        reader.close();
        out.close();
    }

    public void pairCompareExecute(TreeReader reader, ResultWriter out ) throws TreeCmpException, TreeParseException {

        Metric[] metrics = ActiveMetricsSet.getActiveMetricsSet().getActiveMetricsTable();
        StatCalculator[] statsMetrics = new StatCalculator[metrics.length];

        for(int i=0;i<metrics.length;i++){
            statsMetrics[i]=new StatCalculator(metrics[i]);
        }
        if (ioSet.isGenSackinIndexes()) {
            countSackinIndexes(reader);
        }
        pairCompareEx(reader, out, statsMetrics);
    }

private void pairCompareEx(TreeReader reader, ResultWriter out, StatCalculator[] metrics ) throws TreeCmpException, TreeParseException {

        pal.tree.Tree tree1 = reader.readNextTree();
        pal.tree.Tree tree2;
        int i;
        double val;
        Object[] row;
        int num = 1;

        int mSize = metrics.length;
    ReportUtils.setRowCount(mSize);

        //initialize summary stat calculators
        SummaryStatCalculator[] sStatCalc=new SummaryStatCalculator[mSize];
        for(i=0;i<mSize;i++){
            sStatCalc[i]=new SummaryStatCalculator(metrics[i]);
        }

        Object[] head = ReportUtils.getHeaderRow(metrics);
        out.setRow(head);
        out.write();

        AlignWriter aw = new AlignWriter();
        aw.initFiles(metrics);

        ProgressIndicator progress=new ProgressIndicator();
        int numnerOfTrees=reader.getEffectiveNumberOfTrees();

        progress.setMaxVal(numnerOfTrees-1);
        progress.setPrintInterval(600);
        progress.setPrintPercentInterval(5.0);
        
        progress.init();
        while ((tree2 = reader.readNextTree()) != null) {
            
            for(i=0; i<metrics.length; i++){
                val = metrics[i].getDistance(tree1, tree2, num, num+1);
                 //summary
                sStatCalc[i].insertValue(val);
            }
            row = ReportUtils.getResultRow(num, num, num + 1, metrics, sackin_ind_vec, sackin_unrooted_ind_vec);
            out.setRow(row);
            out.write();

            aw.writeAlignments(num, num, num + 1, metrics);

            progress.displayProgress(num);

            num++;
            tree1 = tree2;
        }

        aw.closeFiles(metrics);
        //print summary data to file
        SummaryStatCalculator.printSummary(out, sStatCalc);
    }
}

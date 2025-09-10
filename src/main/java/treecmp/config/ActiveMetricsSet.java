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

package treecmp.config;

import treecmp.metrics.Metric;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ActiveMetricsSet {

    private static ActiveMetricsSet AMset;
    private ArrayList<Metric> metricList;

    protected ActiveMetricsSet()
    {
        AMset=null;
        metricList=new ArrayList<Metric>();
        metricList.clear();

    }

    public static ActiveMetricsSet getActiveMetricsSet()
    {
        if(AMset==null)
        {
            AMset=new ActiveMetricsSet();
        }
        return AMset;
    }

    public void setActiveMetricsSet(String[] metrics) {
        DefinedMetricsSet DMSet = DefinedMetricsSet.getDefinedMetricsSet();
        List<Metric> DMetrics = DMSet.getDefinedMetrics();
        ListIterator<Metric> itDM = DMetrics.listIterator();
        for(String dm :metrics) {
            Metric found=null;
            while (itDM.hasNext()) {
                Metric m = itDM.next();
                if(m.getCommandLineName().equals(dm)){
                    found = m;
                }
            }
            if (found != null){
                metricList.add(found);
            }
        }
    }

    public void addMetric(Metric m)
    {

        /**
         *
         * Here can be added a protection against adding the same metric more than onec
         */

        this.metricList.add(m);
        if (m.isRooted()) {
            IOSettings.getIOSettings().setRootedMetricUsed(true);
        }

    }
    public ArrayList<Metric> getActiveMetrics()
    {
        return this.metricList;
    }

     public Metric[] getActiveMetricsTable()
    {
        int size=this.metricList.size();
        Metric[] mTable=new Metric[size];

        for(int i=0;i<size;i++)
        {
            mTable[i]=this.metricList.get(i);
        }
         return mTable;
    }
     
     public void clearAllMetrics() {
    	 metricList.clear();
     }

    public boolean isAnyRootedMetric() {
        int size=this.metricList.size();
        for(int i=0;i<size;i++)
        {
            if(this.metricList.get(i).isWeighted()) {
                return true;
            }
        }
        return false;
    }
}

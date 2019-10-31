/** This file is part of TreeCmp, a tool for comparing phylogenetic trees    using the Matching Split distance and other metrics.    Copyright (C) 2011,  Damian Bogdanowicz    This program is free software: you can redistribute it and/or modify    it under the terms of the GNU General Public License as published by    the Free Software Foundation, either version 3 of the License, or    (at your option) any later version.    This program is distributed in the hope that it will be useful,    but WITHOUT ANY WARRANTY; without even the implied warranty of    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the    GNU General Public License for more details.    You should have received a copy of the GNU General Public License    along with this program.  If not, see <http://www.gnu.org/licenses/>. */package treecmp.io;import java.io.*;import pal.tree.*;import treecmp.config.ActiveMetricsSet;import treecmp.metric.Metric;public class TreeReader {    private FileInputStream FIStream;    private BufferedInputStream BIStream;    private String FileName;    private int numberOfTrees;    private int step;    private boolean isFirstTree;    private boolean checkWeightsValues = false;    public int getStep() {        return step;    }    public void setStep(int step) {        this.step = step;    }    public int getNumberOfTrees() {        return numberOfTrees;    }    public int getEffectiveNumberOfTrees() {        if (this.numberOfTrees == 0) {            return 0;        } else {            return 1 + (this.numberOfTrees - 1) / this.step;        }    }    /** Creates a new instance of TreeReader */    public TreeReader(String filename) {        this.step = 1;        this.FileName = filename;    }    public int open() {        isFirstTree = true;        try {            this.FIStream = new FileInputStream(this.FileName);            this.BIStream = new BufferedInputStream(this.FIStream);        } catch (FileNotFoundException e) {            System.out.println("Error. Specified file: " + this.FileName + " not found.\n");            return -1;        }        return 0;    }    public void close() {        try {            //this.FIStream.close();            this.BIStream.close();        } catch (IOException ex) {            ex.printStackTrace();        }    }    private String readUntil(char c) {        int i;        char ch;        //String temp = new String("");        StringBuilder s = new StringBuilder(8192);        try {            do {                //i = this.FIStream.read();                i = this.BIStream.read();                if (i != -1 && i != (int) c) {                    ch = (char) i;                    //temp = temp.concat(Character.toString(ch));                    s.append(ch);                }            } while (i != -1 && i != (int) c);        } catch (IOException ex) {            ex.printStackTrace();        }        //return temp;        return s.toString();    }    public String readNextTreeString() {        int i;        char ch;        String temp = new String("");        try {            do {                //i = this.FIStream.read();                i = this.BIStream.read();            } while (i != -1 && i != '(');            if (i != -1) {                temp = "(" + readUntil(';') + ";";            } else {                temp = null;            }        } catch (IOException ex) {            ex.printStackTrace();        }        return temp;    }    public pal.tree.Tree readNextTree() {        ReadTree tree = null;        String treeString = null;        int i, iStep;        try {            //We want first tree in input file be always read            if (this.isFirstTree) {                iStep = 1;                this.isFirstTree = false;            } else {                iStep = this.step;            }            for (i = 1; i <= iStep; i++) {                treeString = this.readNextTreeString();            }            if (treeString != null) {                pal.io.InputSource in1 = pal.io.InputSource.openString(treeString);                tree = new ReadTree(in1);                if(checkWeightsValues && !(NodeUtils.getMinimumPathLengthLengthToLeaf(tree.getRoot()) > 0.0)) {                    throw new TreeParseException("Not positive edge weight value");                };            }        } catch (TreeParseException ex) {            ex.printStackTrace();        }        return tree;    }    public pal.tree.Tree readNextUnrootedTree() {        pal.tree.Tree t = readNextTree();        pal.tree.Tree ut = null;        if (t != null) {            Node r = t.getRoot();            if (r.getChildCount() == 2) {                ut = TreeTool.getUnrooted(t);              //  System.out.println("Rooted tree found!!!");            } else {                ut = t;            }        }        return ut;    }    public int scan() {        checkWeightsValues = ActiveMetricsSet.getActiveMetricsSet().isAnyRootedMetric();        int counter = 0;        while (readNextTree() != null) {            counter++;        }        this.numberOfTrees = counter;        return counter;    }}
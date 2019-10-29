/** This file is part of TreeCmp, a tool for comparing phylogenetic trees using the Matching Split distance and other metrics. Copyright (C) 2011,  Damian Bogdanowicz This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>. */package treecmp.io;import java.io.*;import org.apache.poi.ss.usermodel.Cell;import org.apache.poi.ss.usermodel.Row;import org.apache.poi.xssf.usermodel.XSSFSheet;import org.apache.poi.xssf.usermodel.XSSFWorkbook;import treecmp.config.IOSettings;public class ResultWriter {    public final static String NEW_LINE = System.getProperty("line.separator");    private static IOSettings ioSet = IOSettings.getIOSettings();    private static String sep = ioSet.getSSep();    private static String cvsSep = ioSet.getCsvSep();    private String resultString;    private Object[] result;    public boolean writeToFile;    public OutputFileType outputFileType;    private String fileName;    private File file;    private FileWriter filewriter;    private FileOutputStream outputStream;    private XSSFWorkbook workbook;    private XSSFSheet sheet;    private int rowIter;    /** Creates a new instance of ResultWriter */    public ResultWriter() {        this.outputFileType = OutputFileType.txt;    }    public ResultWriter(Object[] txt, boolean writeToFile_, String fileName_, OutputFileType oft) {        this.result = txt;        this.writeToFile = writeToFile_;        this.fileName = fileName_;        this.outputFileType = oft;    }    public ResultWriter(Object[] txt, boolean writeToFile_, String fileName_) {        this.result = txt;        this.writeToFile = writeToFile_;        this.fileName = fileName_;        this.outputFileType = OutputFileType.txt;    }    public ResultWriter(Object[] txt) {        this.result = txt;        this.writeToFile = false;    }    public void init() {        if (this.writeToFile == true) {            switch (this.outputFileType) {                case xlsx:                    try {                        this.outputStream = new FileOutputStream(this.fileName);                    } catch (FileNotFoundException e) {                        e.printStackTrace();                    }                    this.workbook = new XSSFWorkbook();                    this.sheet = workbook.createSheet("Report");                    rowIter = 0;                    Row row = sheet.createRow(rowIter++);                    Cell cell = row.createCell(0);                    cell.setCellValue((String) "TreeCmp: Comparison of Phylogenetic Trees on the Web - Report");                    break;                default:                    this.file = new File(this.fileName);                    try {                        this.filewriter = new FileWriter(this.file);                    } catch (IOException ex) {                        System.out.print("Error. There is a problem with an output file: "+this.fileName+"\n");                    }                    break;            }        }    }    public void close() {        if (this.filewriter != null) {            try {                this.filewriter.close();            } catch (IOException ex) {                ex.printStackTrace();            }        }        if (this.outputStream != null) {            try {                this.workbook.write(outputStream);                this.workbook.close();                this.outputStream.close();            } catch (IOException e) {                e.printStackTrace();            }        }    }    public void write() {        if (this.writeToFile == false) {            System.out.println(this.result);        } else {            switch (this.outputFileType) {                case csf:                    try {                        StringBuilder sb = new StringBuilder();                        for (Object field : result) {                            if(field != null) {                                sb.append(field + cvsSep);                            }                        }                        sb.append('\n');                        this.filewriter.write(sb.toString());                    } catch (IOException ex) {                        ex.printStackTrace();                    }                    break;                case xlsx:                    Row row = this.sheet.createRow(rowIter++);                    int colNum = 0;                    for (Object field : result) {                        Cell cell = row.createCell(colNum++);                        if (field instanceof String) {                            cell.setCellValue((String) field);                        } else if (field instanceof Integer) {                            cell.setCellValue((Integer) field);                        } else if (field instanceof Double) {                            cell.setCellValue((Double) field);                        }                    }                    break;                default:                    try {                        StringBuilder sb = new StringBuilder();                        for (Object field : result) {                            if(field != null) {                                sb.append(field + sep);                            }                        }                        sb.append('\n');                        this.filewriter.write(sb.toString());                    } catch (IOException ex) {                        ex.printStackTrace();                    }                    break;            }        }    }    public void write_pure() {        if (this.writeToFile == false) {            System.out.println(this.result);        } else {            try {                this.filewriter.write(this.resultString);            } catch (IOException ex) {                ex.printStackTrace();            }        }    }    public void setRowString(String text) {        this.resultString = text;    }    public void setRow(Object[] text) {        this.result = text;    }    public void setFileName(String text) {        this.fileName = text;    }    public void setOutputFileType(OutputFileType ost) {        this.outputFileType = ost;    }    public void setOutputFileType(String ext) {        switch (ext) {            case "csv":                this.outputFileType = OutputFileType.csf;                break;            case "xlsx":                this.outputFileType = OutputFileType.xlsx;                break;            case "pdf":                this.outputFileType = OutputFileType.pdf;                break;            case "txt":            default:                this.outputFileType = OutputFileType.txt;                break;        }    }    public void isWriteToFile(boolean is) {        this.writeToFile = is;    }}
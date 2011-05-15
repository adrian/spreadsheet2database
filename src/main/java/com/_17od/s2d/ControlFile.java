package com._17od.s2d;

public interface ControlFile {
    public String getJDBCDriver();
    public String getJDBCURL();
    public String getJDBCUsername();
    public String getJDBCPassword();
    public String getTargetTable();
    public String getStartCell();
    public String getEndCell();
    public int getSheetNumber();
    public ColumnMetaData[] getColumnMetaData();
}

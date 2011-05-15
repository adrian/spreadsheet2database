package com._17od.s2d;

public class ControlFileStub implements ControlFile {

    private String jdbcDriver;
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private String targetTable;
    private String startCell;
    private String endCell;
    private int sheetNumber;
    private ColumnMetaData[] columnMetadata;

    @Override
    public String getJDBCDriver() {
        return jdbcDriver;
    }

    public void setJDBCDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    @Override
    public String getJDBCURL() {
        return jdbcURL;
    }

    public void setJDBCURL(String jdbcURL) {
        this.jdbcURL = jdbcURL;
    }

    @Override
    public String getJDBCUsername() {
        return jdbcUsername;
    }

    public void setJDBCUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    @Override
    public String getJDBCPassword() {
        return jdbcPassword;
    }

    public void setJDBCPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    @Override
    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    @Override
    public String getStartCell() {
        return startCell;
    }

    public void setStartCell(String startCell) {
        this.startCell = startCell;
    }

    @Override
    public String getEndCell() {
        return endCell;
    }

    public void setEndCell(String endCell) {
        this.endCell = endCell;
    }

    @Override
    public int getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    @Override
    public ColumnMetaData[] getColumnMetaData() {
        return columnMetadata;
    }

    public void setColumnMetaData(ColumnMetaData[] columnMetadata) {
        this.columnMetadata = columnMetadata;
    }

}

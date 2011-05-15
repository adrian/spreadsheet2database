package com._17od.s2d;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

@SuppressWarnings("serial")
public class ControlFileImpl extends Properties implements ControlFile {

    private static final String JDBC_DRIVER = "jdbc.driver.classname";
    private static final String JDBC_URL = "jdbc.url";
    private static final String JDBC_USERNAME = "jdbc.username";
    private static final String JDBC_PASSWORD = "jdbc.password";
    private static final String TARGET_TABLE = "target.table";
    private static final String START_CELL = "startcell";
    private static final String END_CELL = "endcell";
    private static final String SHEET_NUMBER = "sheetnumber";
    private static final String COLUMN_TARGET_DB_COLUMN = "column.%d.target.db.column";
    private static final String COLUMN_DAFAULT_VALUE = "column.%d.default.value";

    public static ControlFileImpl read(String controlFileName) throws FileNotFoundException, IOException {
        ControlFileImpl controlFile = new ControlFileImpl();
        controlFile.load(new FileReader(controlFileName));
        return controlFile;
    }

    public String getJDBCDriver() {
        return getProperty(JDBC_DRIVER);
    }

    public String getJDBCURL() {
        return getProperty(JDBC_URL);
    }

    public String getJDBCUsername() {
        return getProperty(JDBC_USERNAME);
    }

    public String getJDBCPassword() {
        return getProperty(JDBC_PASSWORD);
    }

    public String getTargetTable() {
        return getProperty(TARGET_TABLE);
    }

    public String getStartCell() {
        return getProperty(START_CELL);
    }

    public String getEndCell() {
        return getProperty(END_CELL);
    }

    /**
     * @return If no sheet number is specified then return 0
     */
    public int getSheetNumber() {
        String sheetNumber = getProperty(SHEET_NUMBER);
        return sheetNumber == null ? 0 : Integer.parseInt(sheetNumber);
    }

    public ColumnMetaData[] getColumnMetaData() {
        ArrayList<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();
        int i = 1;
        String columnName = null;
        String defaultValue = null;

        do {
            columnName = getProperty(String.format(COLUMN_TARGET_DB_COLUMN, i));
            defaultValue = getProperty(String.format(COLUMN_DAFAULT_VALUE, i));
            if (columns != null) {
                columns.add(new ColumnMetaData(columnName, defaultValue));
            }
            i++;
        } while (columnName != null);

        return columns.toArray(new ColumnMetaData[columns.size()]);
    }

}

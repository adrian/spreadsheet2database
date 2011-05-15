package com._17od.s2d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class SpreadsheetReaderTest {

    private SpreadsheetReader spreadsheetReader;
    private DatabaseStub database;
    private ColumnMetaData[] columnMetaData;

    @Before
    public void prepare() {
        database = new DatabaseStub();
        spreadsheetReader = new SpreadsheetReader();

        columnMetaData = new ColumnMetaData[10];
        columnMetaData[0] = new ColumnMetaData("firstname");
        columnMetaData[1] = new ColumnMetaData("lastname");
        columnMetaData[2] = new ColumnMetaData("address1");
        columnMetaData[3] = new ColumnMetaData("city");
        columnMetaData[4] = new ColumnMetaData("country");
    }

    @Test
    public void loadToTheEndOfSheet() throws FileNotFoundException, IOException, ParseException {
        ControlFileStub controlFileStub = new ControlFileStub();
        controlFileStub.setSheetNumber(0);
        controlFileStub.setStartCell("B4");
        controlFileStub.setEndCell("F0");
        controlFileStub.setColumnMetaData(columnMetaData);

        ClassPathResource spreadsheet = new ClassPathResource("test.xls");

        spreadsheetReader.load(spreadsheet.getFile().getAbsolutePath(), controlFileStub, database);
        assertEquals(10, database.getSaves().size());
    }

    @Test
    public void loadRangeOfRows() throws FileNotFoundException, IOException, ParseException {
        ControlFileStub controlFileStub = new ControlFileStub();
        controlFileStub.setSheetNumber(0);
        controlFileStub.setStartCell("B4");
        controlFileStub.setEndCell("F9");
        controlFileStub.setColumnMetaData(columnMetaData);

        ClassPathResource spreadsheet = new ClassPathResource("test.xls");

        spreadsheetReader.load(spreadsheet.getFile().getAbsolutePath(), controlFileStub, database);
        assertEquals(6, database.getSaves().size());
    }

    @Test
    public void loadRow8() throws FileNotFoundException, IOException, ParseException {
        ControlFileStub controlFileStub = new ControlFileStub();
        controlFileStub.setSheetNumber(0);
        controlFileStub.setStartCell("B8");
        controlFileStub.setEndCell("F8");
        controlFileStub.setColumnMetaData(columnMetaData);

        ClassPathResource spreadsheet = new ClassPathResource("test.xls");

        spreadsheetReader.load(spreadsheet.getFile().getAbsolutePath(), controlFileStub, database);
        assertEquals(1, database.getSaves().size());
        Hashtable<String, CellContents> row = database.getSaves().get(0);
        assertEquals("Hyatt", row.get((controlFileStub.getColumnMetaData()[0]).getDatabaseName()).getData());
        assertEquals("Ryan", row.get((controlFileStub.getColumnMetaData()[1]).getDatabaseName()).getData());
        assertEquals("5299 Malesuada Av.", row.get((controlFileStub.getColumnMetaData()[2]).getDatabaseName()).getData());
        assertEquals("Greenfield", row.get((controlFileStub.getColumnMetaData()[3]).getDatabaseName()).getData());
        assertEquals("Cuba", row.get((controlFileStub.getColumnMetaData()[4]).getDatabaseName()).getData());
    }

    @Test
    public void endRowBeforeStartRow() throws FileNotFoundException, IOException, ParseException {
        ClassPathResource spreadsheet = new ClassPathResource("test.xls");
        ControlFileStub controlFileStub = new ControlFileStub();
        controlFileStub.setSheetNumber(0);
        controlFileStub.setStartCell("B5");
        controlFileStub.setEndCell("F4");
        controlFileStub.setColumnMetaData(columnMetaData);
        try {
            spreadsheetReader.load(spreadsheet.getFile().getAbsolutePath(), controlFileStub, database);
            fail("expected exception");
        } catch (RuntimeException e) {
            assertEquals("end row is before start row", e.getMessage());
        }
    }

    @Test
    public void endColumnBeforeStartColumn() throws FileNotFoundException, IOException, ParseException {
        ClassPathResource spreadsheet = new ClassPathResource("test.xls");
        ControlFileStub controlFileStub = new ControlFileStub();
        controlFileStub.setSheetNumber(0);
        controlFileStub.setStartCell("B4");
        controlFileStub.setEndCell("A5");
        controlFileStub.setColumnMetaData(columnMetaData);
        try {
            spreadsheetReader.load(spreadsheet.getFile().getAbsolutePath(), controlFileStub, database);
            fail("expected exception");
        } catch (RuntimeException e) {
            assertEquals("end column is before start column", e.getMessage());
        }
    }

}

package com._17od.s2d;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpreadsheetReader {

    private static Logger logger = LoggerFactory.getLogger(SpreadsheetReader.class);

    private ArrayList<DataDecorator> dataDecorators = new ArrayList<DataDecorator>();

    public void load(String filename, ControlFile controlFile, Database database) throws FileNotFoundException, IOException, ParseException {

        if (logger.isDebugEnabled()){
            logger.debug(">> load(filename: {}, controlFile: {}, database: {})", 
                    new Object[] {filename, controlFile, database});
        }

        Cell startCell = Cell.parse(controlFile.getStartCell());
        Cell endCell = Cell.parse(controlFile.getEndCell());
        if (startCell == null) {
            throw new RuntimeException("no start cell supplied");
        }
        if (endCell == null) {
            throw new RuntimeException("no end cell supplied");
        }
        if (endCell.getRow() != 0 && endCell.getRow() < startCell.getRow()) {
            throw new RuntimeException("end row is before start row");
        }
        if (endCell.getColumnNumber() < startCell.getColumnNumber()) {
            throw new RuntimeException("end column is before start column");
        }

        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filename));
        HSSFSheet sheet = wb.getSheetAt(controlFile.getSheetNumber());

        // Figure out what row to start and end on
        int startRow = startCell.getRow() - 1; // rows are 0 based to subtract 1 from the physical row
        int endRow = endCell.getRow() == 0 ? Integer.MAX_VALUE : endCell.getRow();

        // Figure out what column to start and end on
        int startCol = startCell.getColumnNumber();
        int endCol = endCell.getColumnNumber();

        String tableName = controlFile.getTargetTable();
        ColumnMetaData columns[] = controlFile.getColumnMetaData();

        boolean reachedEnd = false;
        int row = startRow;
        do {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Loading row %s", row));
            }

            Hashtable<String, CellContents> rowOfData = new Hashtable<String, CellContents>();

            HSSFRow hsfRow = sheet.getRow(row);
            if (hsfRow != null) {
                for (int col=startCol; col<endCol + 1; col++) {
                    HSSFCell cell = hsfRow.getCell(col);
                    String cellValue = cell == null ? "" : cell.getStringCellValue();
                    ColumnMetaData columnMetaData = columns[col - startCol];
                    String columnName = columnMetaData.getDatabaseName();
                    CellContents cellContents = new CellContents(cellValue, columnMetaData);
                    rowOfData.put(columnName, cellContents);
                }

                if (!empty(rowOfData)) {
                    for (DataDecorator decorator : dataDecorators) {
                        decorator.update(rowOfData);
                    }
    
                    try {
                        database.save(tableName, rowOfData);
                    } catch (RuntimeException e) {
                        throw new RuntimeException("Error loading row " + row, e);
                    }
    
                    if (row % 100 == 0) {
                        if (logger.isInfoEnabled()) {
                            logger.info(String.format("processing record %s", row));
                        }
                    }

                    // Move to the next row
                    row++;
                } else {
                    reachedEnd = true;
                }

            } else {
                reachedEnd = true;
            }

        } while (!reachedEnd && row < endRow);

    }

    private boolean empty(Hashtable<String, CellContents> rowOfData) {
        boolean empty = true;

        Iterator<CellContents> valuesIterator = rowOfData.values().iterator();
        while (valuesIterator.hasNext() && empty) {
            CellContents nextCell = valuesIterator.next();
            if (nextCell.getData() != null && !nextCell.getData().equals("")) {
                empty = false;
            }
        }

        return empty;
    }


    public void addDataDecorator(DataDecorator dataDecorator) {
        this.dataDecorators.add(dataDecorator);
    }

}

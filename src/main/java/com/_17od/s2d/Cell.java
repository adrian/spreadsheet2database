package com._17od.s2d;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cell {

    private String column;
    private int row;

    private Cell(String column, int row) {
        this.column = column;
        this.row = row;
    }

    public static Cell parse(String cellStr) {
        Cell cell = null;

        Pattern pattern = Pattern.compile("([a-z]+)([0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cellStr);
        if (matcher.matches()) {
            String column = matcher.group(1).toUpperCase();
            int row = Integer.parseInt(matcher.group(2));
            cell = new Cell(column, row);
        }

        return cell;
    }

    public int getRow() {
        return row;
    }

    public String getColumn() {
        return column;
    }

    /**
     * Return the column letters as a number, 0 based
     * e.g. A = 0
     *      B = 1
     *      AA = 26
     * @return -1 if no column specified, actual column number otherwise 
     */
    public int getColumnNumber() {
        if (column.equals("")) {
            return -1;
        }

        int columnNumber = 0;

        for (int i=0; i<column.length(); i++) {
            char c = column.charAt(i);
            columnNumber += ((int) c) - 65;
            if (i > 0) {
                columnNumber += 26;
            }
        }

        return columnNumber;
    }

    public String toString() {
        return column + row;
    }

}

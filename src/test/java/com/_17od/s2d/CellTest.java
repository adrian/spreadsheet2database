package com._17od.s2d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class CellTest {

    @Test
    public void testSimple() {
        Cell cell = Cell.parse("A1");
        assertEquals("A", cell.getColumn());
        assertEquals(1, cell.getRow());
    }

    @Test
    public void testLong() {
        Cell cell = Cell.parse("BCA1234");
        assertEquals("BCA", cell.getColumn());
        assertEquals(1234, cell.getRow());
    }

    @Test
    public void testNoColumn() {
        Cell cell = Cell.parse("1234");
        assertNull(cell);
    }

    @Test
    public void testRow0() {
        Cell cell = Cell.parse("ABC0");
        assertEquals("ABC", cell.getColumn());
        assertEquals(0, cell.getRow());
    }

    @Test
    public void testInvalidText() {
        assertNull(Cell.parse("-A:9"));
    }

    @Test
    public void testColumnNumberA() {
        Cell cell = Cell.parse("A0");
        assertEquals(0, cell.getColumnNumber());
    }

    @Test
    public void testColumnNumberB() {
        Cell cell = Cell.parse("B0");
        assertEquals(1, cell.getColumnNumber());
    }

    @Test
    public void testColumnNumberAA() {
        Cell cell = Cell.parse("AA0");
        assertEquals(26, cell.getColumnNumber());
    }

    @Test
    public void testColumnNumberAB() {
        Cell cell = Cell.parse("AB0");
        assertEquals(27, cell.getColumnNumber());
    }

    @Test
    public void testColumnNumberABCD() {
        Cell cell = Cell.parse("ABCD0");
        assertEquals(84, cell.getColumnNumber());
    }

}

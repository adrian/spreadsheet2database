package com._17od.s2d;

import java.util.ArrayList;
import java.util.Hashtable;

public class DatabaseStub implements Database {

    private ArrayList<Hashtable<String, CellContents>> saves = new ArrayList<Hashtable<String, CellContents>>();

    @Override
    public int save(String tableName, Hashtable<String, CellContents> rowOfData) {
        saves.add(rowOfData);
        return 1;
    }

    public ArrayList<Hashtable<String, CellContents>> getSaves() {
        return saves;
    }

    public void reset() {
        saves.clear();
    }

}

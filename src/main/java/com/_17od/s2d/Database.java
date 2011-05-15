package com._17od.s2d;

import java.util.Hashtable;

public interface Database {
    public int save(String tableName, Hashtable<String, CellContents> rowOfData);
}

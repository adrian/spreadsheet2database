package com._17od.s2d;

public class CellContents {

    private String data;
    private ColumnMetaData metaData;
    
    public CellContents(String data, ColumnMetaData metaData) {
        this.data = data;
        this.metaData = metaData;
    }

    public String getData() {
        return data;
    }

    public ColumnMetaData getMetaData() {
        return metaData;
    }

}

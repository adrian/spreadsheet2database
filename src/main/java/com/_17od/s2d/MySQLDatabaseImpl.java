package com._17od.s2d;

import java.util.ArrayList;
import java.util.Hashtable;

import org.springframework.jdbc.core.JdbcOperations;

public class MySQLDatabaseImpl implements Database {

    private JdbcOperations jdbcOperations;

    public MySQLDatabaseImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public int save(String tableName, Hashtable<String, CellContents> rowOfData) {
        StringBuffer columnsSQL = new StringBuffer();
        StringBuffer valuesSQL = new StringBuffer();
        ArrayList<String> data = new ArrayList<String>();

        Iterable<String> columnsIterator = rowOfData.keySet();
        for (String columnName : columnsIterator) {
            ColumnMetaData columnMetaData = rowOfData.get(columnName).getMetaData();
            String value = rowOfData.get(columnName).getData();

            // Check if we have a default value
            if (value == null || value.equals("")) {
                value = (String) columnMetaData.getDefaultValue();
            }

            // Don't load empty values. Let the database default them to their default value.
            if (value != null) {
                if (columnsSQL.length() > 0) {
                    columnsSQL.append(", ");
                }
                columnsSQL.append(columnName);

                if (valuesSQL.length() > 0) {
                    valuesSQL.append(", ");
                }
                valuesSQL.append("?");

                data.add(value);
            }
        }

        int result = 0;
        if (columnsSQL.length() > 0) {
            StringBuffer sql = new StringBuffer();
            sql.append(String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columnsSQL.toString(), valuesSQL.toString()));
            result = jdbcOperations.update(sql.toString(), data.toArray());
        }

        return result;
    }

}

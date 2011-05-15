package com._17od.s2d;

import java.util.Hashtable;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;

public class MySQLDatabaseImplTest {

    @Test
    public void testInsertWithOneColumn() {
        String tableName = "person";

        String firstnameColumnName = "firstname";
        String firstname = "tom";

        Hashtable<String, CellContents> rowOfData = new Hashtable<String, CellContents>();
        rowOfData.put(firstnameColumnName, new CellContents(firstname, new ColumnMetaData(firstnameColumnName)));

        JdbcOperations mockJdbcOperations = EasyMock.createMock(JdbcOperations.class);
        EasyMock.expect(
                mockJdbcOperations.update(
                        "INSERT INTO person (firstname) VALUES (?)", new Object[] {firstname})).andReturn(1);
        EasyMock.replay(mockJdbcOperations);

        MySQLDatabaseImpl databaseImpl = new MySQLDatabaseImpl(mockJdbcOperations);
        databaseImpl.save(tableName, rowOfData);
        EasyMock.verify(mockJdbcOperations);
    }

    @Test
    public void testInsertWithThreeColumns() {
        String tableName = "person";

        String firstnameColumnName = "firstname";
        String firstname = "tom";
        String lastnameColumnName = "lastname";
        String lastname = "jones";
        String titleColumnName = "title";
        String title = "mr";

        Hashtable<String, CellContents> rowOfData = new Hashtable<String, CellContents>();
        rowOfData.put(firstnameColumnName, new CellContents(firstname, new ColumnMetaData(firstnameColumnName)));
        rowOfData.put(lastnameColumnName, new CellContents(lastname, new ColumnMetaData(lastnameColumnName)));
        rowOfData.put(titleColumnName, new CellContents(title, new ColumnMetaData(titleColumnName)));

        JdbcOperations mockJdbcOperations = EasyMock.createMock(JdbcOperations.class);
        EasyMock.expect(
                mockJdbcOperations.update(
                        "INSERT INTO person (lastname, firstname, title) VALUES (?, ?, ?)", 
                            new Object[] {lastname, firstname, title})).andReturn(1);
        EasyMock.replay(mockJdbcOperations);

        MySQLDatabaseImpl databaseImpl = new MySQLDatabaseImpl(mockJdbcOperations);
        databaseImpl.save(tableName, rowOfData);
        EasyMock.verify(mockJdbcOperations);
    }

}

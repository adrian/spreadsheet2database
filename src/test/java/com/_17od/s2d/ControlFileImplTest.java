package com._17od.s2d;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class ControlFileImplTest {

    @Test(expected=FileNotFoundException.class)
    public void nonExistantFile() throws FileNotFoundException, IOException {
        ControlFileImpl.read("bad file");
    }

    public void getJDBCDriver() throws FileNotFoundException, IOException {
        ControlFile controlFile = ControlFileImpl.read("controlfile.properties");
        assertEquals("com.mysql.jdbc.Driver", controlFile.getJDBCDriver());
    }

}

package com._17od.s2d;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class S2D {

    private static Logger logger = LoggerFactory.getLogger(S2D.class);

    private static String usage() {
        StringBuilder sb = new StringBuilder("usage: S2D\n");
        sb.append(" <filename>\n");
        sb.append(" <controlfile>");
        return sb.toString();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        if (args.length < 2) {
            System.err.println("incorrect number of arguments\n");
            System.err.println(usage());
            System.exit(1);
        }

        // Load the control file
        ControlFile controlFile = ControlFileImpl.read(args[1]);

        // Create the database objects
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName(controlFile.getJDBCDriver());
        dataSource.setUrl(controlFile.getJDBCURL());
        dataSource.setUsername(controlFile.getJDBCUsername());
        dataSource.setPassword(controlFile.getJDBCPassword());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Database database = new MySQLDatabaseImpl(jdbcTemplate);

        SpreadsheetReader spreadsheetReader = new SpreadsheetReader();

        if (logger.isInfoEnabled()){
            logger.info(String.format("Loading the spreadsheet [%s] using the control file [%s]", args[0], args[1]));
        }

        // Instantiate and add Decorators here
        //spreadsheetReader.addDataDecorator(areaDecorator);

        spreadsheetReader.load(args[0], controlFile, database);
    }

}

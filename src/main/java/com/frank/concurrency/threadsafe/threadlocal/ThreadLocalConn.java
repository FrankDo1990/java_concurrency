package com.frank.concurrency.threadsafe.threadlocal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Frank on 2017/1/15.
 */
public class ThreadLocalConn {

    public static final String SQL_URL = "URL";

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>(){
        @Override
        protected Connection initialValue() {
            try {
                return DriverManager.getConnection(SQL_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    public Connection getThreadHolder(){
        return connectionHolder.get();
    }
}

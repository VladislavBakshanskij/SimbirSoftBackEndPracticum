package com.simbirsoft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Map;

public class DataBase {
    private static final Logger logger = LogManager.getLogger(DataBase.class);
    private static Connection connection;

    public static void connect() throws ClassNotFoundException, SQLException {
        String driver = "org.sqlite.JDBC";
        String connectionString = "jdbc:sqlite:app.db";
        Class.forName(driver);
        connection = DriverManager.getConnection(connectionString);
        createDB();
    }

    private static void createDB() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS 'stat' ('word' TEXT PRIMARY KEY, 'countRepeat' INT)");
        }
    }

    public static void saveWordMapToDb(Map<String, Integer> wordMap) throws SQLException {
        for (Map.Entry<String, Integer> pair : wordMap.entrySet()) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO 'stat' ('word', 'countRepeat') VALUES (?, ?)")) {
                String word = pair.getKey();
                int countRepeat = pair.getValue();

                ps.setString(1, word);
                ps.setInt(2, countRepeat);
                ps.execute();
            }
        }
    }

    private static void deleteAllFromTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM 'stat'");
        }
    }

    public static void closeDb() throws SQLException {
        connection.close();
    }

    public static void writeWordMap(Map<String, Integer> wordMap) {
        try {
            DataBase.connect();
        } catch (ClassNotFoundException e) {
            logger.error("Нет дравера для базы данных.");
            return;
        } catch (SQLException e) {
            logger.error("Неправильная строка подключения к базе данных.");
            return;
        }

        try {
            createDB();
            deleteAllFromTable();
            saveWordMapToDb(wordMap);
        } catch (SQLException e) {
            logger.error("Некорректный запрос.");
            return;
        }

        try {
            closeDb();
        } catch (SQLException e) {
            logger.error("Невозможно закрыть соединение.");
        }
    }
}

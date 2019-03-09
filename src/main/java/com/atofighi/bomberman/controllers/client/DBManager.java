package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.configs.DBConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private Connection connect = null;

    public DBManager() {
        try {
            connect = DriverManager
                    .getConnection("jdbc:mysql://" + DBConfiguration.host + "/" + DBConfiguration.db + "?"
                            + "user=" + DBConfiguration.user + "&password=" + DBConfiguration.pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSaves() {
        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("select name from " + DBConfiguration.table);
            List<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("name"));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String get(String name) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "select data, name from " + DBConfiguration.table + " " +
                            "where `name` = ?");
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertOrUpdate(String name, String data) {
        try {
            PreparedStatement preparedStatement;
            if (get(name) == null) {
                preparedStatement = connect.prepareStatement(
                        "INSERT INTO `saves` (`id`, `data`, `name`) VALUES (NULL, ?, ?);");
            } else {
                preparedStatement = connect.prepareStatement(
                        "UPDATE `saves` SET `data` = ? WHERE `name` = ?;\n");
            }
            preparedStatement.setString(2, name);
            preparedStatement.setString(1, data);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

package com.jdbc;

import java.sql.*;

public class DBProcessor {
    private final DatabaseConnection database;
    private int countOfProducts = 0;

    public DBProcessor (DatabaseConnection database) {
        this.database = database;
        try(Statement statement = database.getConnection().createStatement()) {
            statement.execute("truncate table goods.products");
            String[] string = {"банан", "кокос", "перец", "пончик", "амиак", "газель", "петрушка", "сушки", "корм", "мел"};
            int[] cost = {20, 50, 35, 25, 120, 2500, 300, 15, 87, 250};
            for (int i = 0; i < string.length; ++i) {
                insert(string[i], cost[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String title, int cost) {
        String query = "select title from goods.products where title=?";
        try(PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Товар с именем " + title + " уже существует");
                return;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        query = "insert into goods.products (prodid, title, cost) values (?, ?, ?)";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            ++countOfProducts;
            preparedStatement.setInt(1, countOfProducts);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, cost);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteProduct(String title) {
        String query = "delete from goods.products where title=?";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void showAll() {
        String query = "select * from goods.products";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            show(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void getCost(String title) {
        String query = "select cost from goods.products where title=?";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int cost = resultSet.getInt("cost");
                System.out.println(cost);
            } else {
                System.out.println("Такого товара нет");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateCost(String title, int cost) {
        String query = "update goods.products set cost=? where title=?";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, cost);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void showInterval(int min, int max) {
        String query = "select * from goods.products where cost between ? AND ?";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2,max);
            ResultSet resultSet = preparedStatement.executeQuery();
            show(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void show(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String string = resultSet.getInt("id") +
                    " " + resultSet.getInt("prodid") +
                    " " + resultSet.getString("title") +
                    " " + resultSet.getInt("cost");
            System.out.println(string);
        }
    }
}

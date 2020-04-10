package com.jdbc;

import java.sql.*;

public class DBProcessor {
    private final DatabaseConnection database;
    private int countOfProducts = 0;

    public DBProcessor (int numberOfProducts, DatabaseConnection database) {
        this.database = database;
        try(Statement statement = database.getConnection().createStatement()) {
            try {
                statement.executeUpdate(cleanTable("goods.products"));
            } catch (SQLSyntaxErrorException e) {
                statement.executeUpdate(createTable("goods.products"));
            }
            for (int i = 1; i < numberOfProducts + 1; ++i) {
                insert("товар" + i, 1 + (int) (Math.random() * 2000));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String cleanTable(String tableName) {
        return "truncate table " + tableName;
    }

    private String createTable(String tableName) {
        StringBuilder query = new StringBuilder();
        query.append("(id int(6) unsigned auto_increment primary key, ");
        query.append("prodid int(6) not null, ");
        query.append("title varchar(30) not null, ");
        query.append("cost int(6) unsigned not null )");
        return query.toString();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "insert into goods.products (prodid, title, cost) values (?, ?, ?)";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            ++countOfProducts;
            preparedStatement.setInt(1, countOfProducts);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, cost);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(String title) {
        String query = "delete from goods.products where title=?";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAll() {
        String query = "select * from goods.products";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            show(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCost(String title, int cost) {
        String query = "update goods.products set cost=? where title=?";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, cost);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showInterval(int min, int max) {
        String query = "select * from goods.products where cost between ? AND ?";
        try(PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2,max);
            ResultSet resultSet = preparedStatement.executeQuery();
            show(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
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

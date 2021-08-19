package com.adressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdressBookDBService {
    private static AdressBookDBService adressBookDBService;

    /**
     * Purpose : For creating a singleton object
     */
    public static AdressBookDBService getInstance() {
        if (adressBookDBService == null)
            adressBookDBService = new AdressBookDBService();
        return adressBookDBService;
    }

    /**
     * Purpose : To read AdressBook data from database using JDBC.
     */
    public List<AdressBookData> readData() {
        String query = "SELECT * FROM adressbook";
        List<AdressBookData> addressBookList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String address = result.getString("address");
                String city = result.getString("city");
                String state = result.getString("state");
                int zipCode = result.getInt("zipCode");
                String phoneNumber = result.getString("phoneNumber");
                String email = result.getString("email");
                addressBookList.add(new AdressBookData(id, firstName, lastName, address, city, state, zipCode, phoneNumber, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }


    /**
     * Purpose : Create connection with the database
     */
    private Connection getConnection() throws SQLException {

        //Step1: Load & Register Driver Class
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        //Step2: Establish a MySql Connection
        String jdbcURL = "jdbc:mysql://localhost:3306/adressbook";
        String userName = "root";
        String password = "";
        Connection connection;
        System.out.println("Connecting to database: " + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful! " + connection);
        return connection;
    }
}
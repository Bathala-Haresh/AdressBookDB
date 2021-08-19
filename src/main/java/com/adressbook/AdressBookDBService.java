package com.adressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdressBookDBService {
    private static AdressBookDBService adressBookDBService;
    private PreparedStatement adressBookDataStatement;

    /**
     * Purpose : For creating a singleton object
     */
    public static AdressBookDBService getInstance() {
        if (adressBookDBService == null)
            adressBookDBService = new AdressBookDBService();
        return adressBookDBService;
    }
    /**
     * Purpose : Update the salary in the DB using Statement Interface
     */
    public int updateEmployeeData(String name, String city) throws AdressBookException {
        return this.updateAdressDataUsingStatement(name, city);
    }
    /**
     * Purpose : To check whether the EmployeePayrollData is in sync with the DB
     *           Use to equals() to compare the values
     */

    public boolean checkEmployeePayrollInSyncWithDB(String name) throws AdressBookException {
        List<AdressBookData> employeePayrollDataList = AdressBookDBService.getAdressData(name);
        return employeePayrollDataList.get(0).equals(getAdressData(name));
    }
    /**
     * Purpose : Update the salary in the DB using Statement Interface
     */
    private int updateAdressDataUsingStatement(String name, String city) throws AdressBookException {
        String sql = String.format("UPDATE adressbook SET city = '%s' WHERE name = '%s'",city, name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new AdressBookException("Please check the updateAdressBookUsingStatement() for detailed information!");
        }
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
                Date startDate = result.getDate("start");
                addressBookList.add(new AdressBookData(id, firstName, lastName, address, city, state, zipCode, phoneNumber, email,startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }
    /**
     * Purpose : Read the data for a certain date range from the database
     * @param startDate
     * @param endDate
     */
    public List<AdressBookData> getEmployeeForDateRange(LocalDate startDate, LocalDate endDate) throws AdressBookException {
        String sql = String.format("SELECT * FROM employee_payroll WHERE START BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return getAdressDataUsingDB(sql);
    }
    /**
     * Purpose : To get the details of a particular employee from the DB using PreparedStatement Interface
     */
    private void preparedStatementForEmployeeData() throws AdressBookException {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employeetables WHERE name = ?";
            adressBookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new AdressBookException("Please check the preparedStatementForEmployeeData() for detailed information!");
        }
    }
    /**
     * Purpose : Create connection to execute query and read the value from the database
     * Assign the value in a list variable
     */
    private List<AdressBookData> getAdressDataUsingDB(String sql) throws AdressBookException {
        List<AdressBookData> addressBookList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
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
                Date startDate = result.getDate("start");
                addressBookList.add(new AdressBookData(id, firstName, lastName, address, city, state, zipCode, phoneNumber, email,startDate));
            }
        } catch (SQLException e) {
            throw new AdressBookException("Please check the getEmployeePayrollDataUsingDB() for detailed information!");
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
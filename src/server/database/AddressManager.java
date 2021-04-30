package server.database;

import server.model.domain.user.Address;

import java.sql.*;
import java.util.ArrayList;

public class AddressManager {


    public AddressManager() {
    }
    public void addAddress(Address address) throws SQLException {
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO address VALUES (?,?,?)");
            insertStatement.setString(1,address.getStreet());
            insertStatement.setString(2,address.getNumber());
            insertStatement.setInt(3,address.getZipcode());
            if(!isCity(address))
                addCity(address);
            insertStatement.executeUpdate();
        }
    }

    public boolean isAddress(String street, String number, int zipcode)throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM address WHERE zip_code = ? AND street = ? AND number = ?");
            selectStatement.setInt(1,zipcode);
            selectStatement.setString(2,street);
            selectStatement.setString(3,number);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public String getCityByZipcode(int zipcode)throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement selectStatement = connection.prepareStatement("SELECT city FROM city WHERE zip_code = ?");
            selectStatement.setInt(1,zipcode);
            ResultSet resultSet = selectStatement.executeQuery();
            if(resultSet.next()){
                String city = resultSet.getString("city");
                return city;
            }
            else {
                throw new IllegalStateException("No existing city with this zipcode");
            }
        }
    }

    public void removeAddress(Address address) throws SQLException
    {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM address WHERE zip_code = ?");
            statement.setInt(1,address.getZipcode());
            statement.executeQuery();
        }
    }

    public void addCity(Address address)throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO city VALUES (?,?)");
            statement.setString(1,address.getCity());
            statement.setInt(2,address.getZipcode());
            statement.executeUpdate();
        }
    }

    public boolean isCity(Address address) throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM city WHERE zip_code = ?");
            selectStatement.setInt(1,address.getZipcode());
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
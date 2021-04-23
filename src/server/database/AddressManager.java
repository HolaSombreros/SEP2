package server.database;

import server.model.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressManager extends DatabaseManager{

    public AddressManager() {
    }

    public void addAddress(Address address) throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO address VALUES (?,?,?)");
            insertStatement.setString(1,address.getStreet());
            insertStatement.setString(2,address.getNumber());
            insertStatement.setInt(3,address.getZipcode());
            insertStatement.executeUpdate();
        }
    }

    public ArrayList<Address> getAddressByCity(int zipcode)throws SQLException{
        ArrayList<Address> addresses = new ArrayList<>();
        try(Connection connection = getConnection()){
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM address WHERE zip_code = ?");
            selectStatement.setInt(1,zipcode);
            ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    String street = resultSet.getString("street");
                    String number = resultSet.getString("number");
                    addresses.add(new Address(street, number, zipcode, getCityByZipcode(zipcode)));
                    return addresses;
                } else {
                    throw new IllegalStateException("No addresses for this city in the database");
                }
        }
    }

    public boolean isAddress(String street, String number, int zipcode)throws SQLException{
        try(Connection connection = getConnection()){
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
        try(Connection connection = getConnection()){
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
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM address WHERE zip_code = ?");
            statement.setInt(1,address.getZipcode());
            statement.executeQuery();
        }
    }
}
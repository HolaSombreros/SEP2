package server.database;

import server.model.Address;
import server.model.Administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
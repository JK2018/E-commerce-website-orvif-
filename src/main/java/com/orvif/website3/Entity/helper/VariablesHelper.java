package com.orvif.website3.Entity.helper;

import com.orvif.website3.Repository.DAOException;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VariablesHelper {





//WTF NOT IN DB

/**

    public String get(String name) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement("SELECT value FROM VARIABLES WHERE name = ?");
            preparedStmt.setString(1, name);
            resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("value");
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException("An SQL exception occured while trying to fetch variable (\"" + name + "\") value : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception occured while trying to fetch variable (\"" + name + "\") value");
        } finally {
            fermeturesSilencieuses(resultSet, preparedStmt, connection);
        }
    }

    @Override
    public void update(String name, String value) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement("UPDATE VARIABLES SET value = ? WHERE name = ?");
            preparedStmt.setString(1, value);
            preparedStmt.setString(2, name);
            preparedStmt.execute();
        } catch (SQLException e) {
            throw new DAOException("An SQL exception occured while trying to update variable (\"" + name + "\") value to \"" + value + "\": " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception occured while trying to update variable (\"" + name + "\") value to \"" + value + "\"");
        } finally {
            fermeturesSilencieuses(preparedStmt, connection);
        }
    }**/





}

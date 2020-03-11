package com.orvif.website3.Entity.helper;

import com.orvif.website3.Repository.DAOException;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class MetadataHelper {



// TABLE NOT IN DATABASE...WTF?

/**


    public void update(com.orvif.bean.Metadata metadata) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement("UPDATE METADATA SET page_title = ?, page_description = ? WHERE page_name = ?;");
            preparedStmt.setString(1, metadata.getPageTitle());
            preparedStmt.setString(2, metadata.getPageDescription());
            preparedStmt.setString(3, metadata.getPageName());
            preparedStmt.execute();
        } catch (SQLException e) {
            throw new DAOException("SQL Exception while trying to update a metadata (" + metadata.toString() + ") : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception while trying to update a metadata (" + metadata.toString() + ")");
        } finally {
            fermeturesSilencieuses(preparedStmt, connection);
        }
    }

    @Override
    public List<com.orvif.bean.Metadata> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement("SELECT * FROM METADATA;");
            resultSet = preparedStmt.executeQuery();
            List<Metadata> metadataList = new ArrayList<>();
            while (resultSet.next()) {
                Metadata metadata = new Metadata();
                metadata.setPageName(resultSet.getString("page_name"));
                metadata.setPageTitle(resultSet.getString("page_title"));
                metadata.setPageUrl(resultSet.getString("page_url"));
                metadata.setPageDescription(resultSet.getString("page_description"));
                metadataList.add(metadata);
            }
            return metadataList;
        } catch (SQLException e) {
            throw new DAOException("SQL Exception while trying to get all metadata : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception while trying to get all metadata");
        } finally {
            fermeturesSilencieuses(resultSet, preparedStmt, connection);
        }
    }

    @Override
    public com.orvif.bean.Metadata getByName(String name) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement("SELECT * FROM METADATA WHERE page_name = ?;");
            preparedStmt.setString(1, name);
            resultSet = preparedStmt.executeQuery();
            List<Metadata> metadataList = new ArrayList<>();
            if (resultSet.next()) {
                Metadata metadata = new Metadata();
                metadata.setPageName(resultSet.getString("page_name"));
                metadata.setPageTitle(resultSet.getString("page_title"));
                metadata.setPageUrl(resultSet.getString("page_url"));
                metadata.setPageDescription(resultSet.getString("page_description"));
                return metadata;
            }
            throw new DAOException("Metadata not found (name : " + name + ")");
        } catch (SQLException e) {
            throw new DAOException("SQL Exception while trying to get a metadata (name : \"" + name + "\"): " + e.getMessage());
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception while trying to get a metadata (name : \"" + name + "\")");
        } finally {
            fermeturesSilencieuses(resultSet, preparedStmt, connection);
        }
    }

    @Override
    public Metadata getByUrl(String url) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement("SELECT * FROM METADATA WHERE page_url = ?;");
            preparedStmt.setString(1, url);
            resultSet = preparedStmt.executeQuery();
            List<Metadata> metadataList = new ArrayList<>();
            if (resultSet.next()) {
                Metadata metadata = new Metadata();
                metadata.setPageName(resultSet.getString("page_name"));
                metadata.setPageTitle(resultSet.getString("page_title"));
                metadata.setPageUrl(resultSet.getString("page_url"));
                metadata.setPageDescription(resultSet.getString("page_description"));
                return metadata;
            }
            throw new DAOException("Metadata not found (url : " + url + ")");
        } catch (SQLException e) {
            throw new DAOException("SQL Exception while trying to get a metadata (url : \"" + url + "\"): " + e.getMessage());
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e.getClass().getName() + " exception while trying to get a metadata (name : \"" + url + "\")");
        } finally {
            fermeturesSilencieuses(resultSet, preparedStmt, connection);
        }
    }
**/



















}

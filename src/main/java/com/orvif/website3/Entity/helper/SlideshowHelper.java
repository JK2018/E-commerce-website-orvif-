package com.orvif.website3.Entity.helper;

import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.service.SlideshowImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SlideshowHelper {


    //NOT IN DATABASE...WTF?

    /**
    public void add(SlideshowImage slideshowImage) throws DAOException {

        PreparedStatement preparedStmt = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement(STMT_ADD);
            preparedStmt.setInt(1, slideshowImage.getPosition());
            preparedStmt.setString(2, slideshowImage.getUrl());
            preparedStmt.setString(3, slideshowImage.getAlt());
            preparedStmt.execute();
        } catch (SQLException e) {
            throw new DAOException("An sql exception occured (" + e.getMessage() + ") when trying to add the slideshow image : " + slideshowImage.toString());
        } catch (Exception e) {
            throw new DAOException("An exception occured (" + e.getClass().getName() + ") when trying to add the slideshow image : " + slideshowImage.toString());
        } finally {
            fermeturesSilencieuses(preparedStmt, connection);
        }
    }

    @Override
    public void delete(SlideshowImage slideshowImage) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement(STMT_DELETE);
            preparedStmt.setString(1, slideshowImage.getUrl());
            preparedStmt.execute();
        } catch (SQLException e) {
            throw new DAOException("An sql exception occured (" + e.getMessage() + ") when trying to delete the slideshow image : " + slideshowImage.toString());
        } catch (Exception e) {
            throw new DAOException("An exception occured (" + e.getClass().getName() + ") when trying to delete the slideshow image : " + slideshowImage.toString());
        } finally {
            fermeturesSilencieuses(preparedStmt, connection);
        }
    }

    @Override
    public void updatePosition(SlideshowImage slideshowImage) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement(STMT_UPDATE_POS);
            preparedStmt.setInt(1, slideshowImage.getPosition());
            preparedStmt.setString(2,slideshowImage.getUrl());
            preparedStmt.execute();
        } catch (SQLException e) {
            throw new DAOException("An sql exception occured (" + e.getMessage() + ") when trying to update the slideshow image position : " + slideshowImage.toString());
        } catch (Exception e) {
            throw new DAOException("An exception occured (" + e.getClass().getName() + ") when trying to update the slideshow image position : " + slideshowImage.toString());
        } finally {
            fermeturesSilencieuses(preparedStmt, connection);
        }
    }

    @Override
    public List<SlideshowImage> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStmt = connection.prepareStatement(STMT_FETCH);
            resultSet = preparedStmt.executeQuery();
            List<SlideshowImage> slideshowImageList = new ArrayList<>();
            while (resultSet.next()) {
                slideshowImageList.add(new SlideshowImage(resultSet.getInt("position"), resultSet.getString("url"), resultSet.getString("alt")));
            }
            return slideshowImageList;
        } catch (SQLException e) {
            throw new DAOException("An sql exception occured (" + e.getMessage() + ") when trying to fetch slideshow images");
        } catch (Exception e) {
            throw new DAOException("An exception occured (" + e.getClass().getName() + ") when trying to fetch slideshow images");
        } finally {
            fermeturesSilencieuses(resultSet, preparedStmt, connection);
        }
    }**/























}

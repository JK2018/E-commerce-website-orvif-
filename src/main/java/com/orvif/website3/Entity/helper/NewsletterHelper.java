package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Newsletter;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.NewsletterRepository;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsletterHelper {


private NewsletterRepository nr;





    public void add(Newsletter newsletter) throws DAOException {
        try {
            nr.save(newsletter);
            nr.flush();
        } catch (NullPointerException e) {
            throw new DAOException("NullPointer error when inserting newsletter entry : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException("Error when inserting newsletter entry");
        }
    }





    public void remove(Newsletter newsletter) throws DAOException {
        try {
            nr.delete(newsletter);
            nr.flush();
        } catch (NullPointerException e) {
            throw new DAOException("NullPointer error when deleting newsletter entry : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException("Error when deleting newsletter entry");
        }
    }





    public List<Newsletter> getAll() throws DAOException {
        try {
            List<Newsletter> newsletterList = new ArrayList<>();
           newsletterList =  nr.findAll();
            return newsletterList;
        } catch (NullPointerException e) {
            throw new DAOException("NullPointer error when deleting newsletter entry : " + e.getMessage());
        } catch (Exception e) {
            throw new DAOException("Error when deleting newsletter entry");
        }
    }




    private Newsletter resultsetToNewsletter(ResultSet resultSet) throws SQLException {
        Newsletter n = new Newsletter();
        n.setMail(resultSet.getString("mail"));
        n.setNom(resultSet.getString("prenom"));
        n.setPrenom(resultSet.getString("nom"));
        return n;
    }




















}

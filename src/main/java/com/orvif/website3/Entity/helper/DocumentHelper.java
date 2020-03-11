package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.Document;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DocumentHelper {



    @Autowired
    private DocumentRepository dr;


    public DocumentHelper() {
    }

    // possible error in query
    public Map<String, List<Document>> getDocumentListByProduit(int id) {
        Map<String, List<Document>> ret = new HashMap<>();
        List<Document> listImg = new ArrayList<>();
        List<Document> listOther = new ArrayList<>();
        List<Document> dList = new ArrayList<>();
        try {
            dList = dr.getDocumentListByProduit(id);
            for (Document tDoc : dList) {
                if (tDoc.getType().equals("image")) {
                    listImg.add(tDoc);
                } else {
                    listOther.add(tDoc);
                }
            }
            ret.put("imageCollection", listImg);
            ret.put("otherCollection", listOther);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    public Document getLogoMarque(int id) {
        Document ret = null;
        try {
            ret = dr.getLogoMarques(id);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






    public Document getFirstImageByProduct(int id) throws DAOException {
        try {
           Document d = dr.getFirstImageByProduct(id);
            return d;
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " Exception occured when fetching first image of product.");
        }
    }




    public List<Document> getCatalogues() {
        List<Document> ret;
        try {
            ret = dr.getCatalogues();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    public void add(Document document) throws DAOException {
        try {
            dr.save(document);
            dr.flush();
        } catch (DAOException e) {
            throw e;
        }  catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception occured when adding document.");
        }
    }






    public Document getPictureByLink(String link) {
        try {
            Document d = dr.getPictureByLink(link);
            return d;
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(e.getClass().getName() + " exception occured when fetching picture.");
        }
    }







}

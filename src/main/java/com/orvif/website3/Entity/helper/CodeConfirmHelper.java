package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.CodeConfirm;
import com.orvif.website3.Repository.CodeConfirmRepository;
import com.orvif.website3.Repository.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.ArrayList;

@Component
public class CodeConfirmHelper {


    @Autowired
    private CodeConfirmRepository ccr;



    public boolean isUnique(String code) {
        boolean ret = false;
        try {
            ArrayList<CodeConfirm> c = ccr.isUnique(code);
            if(c.size()==1){
                ret = true;
            }
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






    public boolean add(CodeConfirm code) {
        boolean ret = false;
        PreparedStatement preparedStmtDelete = null;
        try {
            ccr.deleteByClient(code.getClient());
            ccr.flush();

            ccr.addCustom(code.getCode(), code.getClient(), code.getTypeCode());
            ccr.flush();

            ret = true;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }






    public boolean removeByCode(String code) {
        boolean ret = false;
        try {

            ccr.deleteByCode(code);
            ccr.flush();
            ret = true;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }





    public boolean removeByClient(int client) {
        boolean ret = false;
        try {
            ccr.deleteByClient(client);
            ccr.flush();
            ret = true;
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    public CodeConfirm getByCode(String code) {
        CodeConfirm ret = null;
        try {
            CodeConfirm c = ccr.findByCode(code);
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }

















}

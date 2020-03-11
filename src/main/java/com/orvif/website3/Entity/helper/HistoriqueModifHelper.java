package com.orvif.website3.Entity.helper;

import com.orvif.website3.Entity.HistoriqueModification;
import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.HistoriqueModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoriqueModifHelper {



@Autowired
private HistoriqueModificationRepository hmr;





    public ArrayList<HistoriqueModification> getByProduit(int idProduit) {
        ArrayList<HistoriqueModification> ret = null;
        try {
            ret = hmr.getByProduit(idProduit);
            /**
            int cpt = 0;
            while (resultat.next()) {
                if (cpt == 0) {
                    ret = new ArrayList<HistoriqueModificationProduit>();
                    cpt++;
                }
                // Construction de l'objet historique
                HistoriqueModification h = new HistoriqueModification();
                h.setIdProduit(resultat.getInt("id_produit"));
                h.setChamp(resultat.getString("champ_modif"));
                h.setValeurInitial(resultat.getString("val_initial"));
                h.setNouvelleValeur(resultat.getString("val_modif"));
                h.setDate(new Date(resultat.getTimestamp("date_modif").getTime()));
                h.setModificateur(resultat.getString("modificateur"));
                ret.add(h);
            }**/
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }




    public int add(HistoriqueModification modif) {
        int ret = -1;
        try {
            hmr.save(modif);
            hmr.flush();
            /**
            preparedStmt = connexion.prepareStatement(stmtAddModification);
            preparedStmt.setInt(1, modif.getIdProduit());
            preparedStmt.setString(2, modif.getChamp());
            preparedStmt.setString(3, modif.getValeurInitial());
            preparedStmt.setString(4, modif.getNouvelleValeur());
            preparedStmt.setString(5, modif.getModificateur());**/
            ret = 1; //? or ret = 0? preparedStmt.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Une erreur est survenue : " + e.getMessage());
        }
        return ret;
    }











}

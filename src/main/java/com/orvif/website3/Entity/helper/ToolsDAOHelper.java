package com.orvif.website3.Entity.helper;

import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class ToolsDAOHelper {

// ?????????????????? NOT IN DB

/**


    public void generateNumberFilterTable(List<Marque> marqueList, List<Famille> familleList, List<SousFamille> sousFamilleList, List<Categorie> categorieList, List<SousCategorie> sousCategorieList, List<Caracteristique> caracteristiqueList) {
        Connection connection = null;
        PreparedStatement statementDropTable = null;
        PreparedStatement statementCreateTable = null;
        try{
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            //Drop table request if exist
            String DROPREQUEST = "DROP TABLE IF EXISTS NB_FILTERS;";
            statementDropTable = connection.prepareStatement(DROPREQUEST);
            statementDropTable.executeUpdate();

            //Generation de la table
//			String CREATEREQUEST = "CREATE TABLE NB_FILTERS(" +
//					"NB INTEGER," +
//					"MARQUE VARCHAR(40)," +
//					"FAMILLE VARCHAR(50)," +
//					"SOUSFAMILLE VARCHAR(50)," +
//					"CATEGORIE VARCHAR(50)," +
//					"SOUSCATEGORIE VARCHAR(50)";
//			List<String> attributes = new ArrayList<>();
//			attributes.add("MARQUE");
//			attributes.add("FAMILLE");
//			attributes.add("SOUSFAMILLE");
//			attributes.add("CATEGORIE");
//			attributes.add("SOUSCATEGORIE");
//
//			for(Caracteristique c:caracteristiqueList){
//				CREATEREQUEST += ","+c.getLibelle()+" VARCHAR(50)";
//				attributes.add(c.getLibelle());
//			}
//			CREATEREQUEST += ");";
//			statementCreateTable = connection.prepareStatement(CREATEREQUEST);
//			statementCreateTable.executeUpdate();

            //Remplissage de la table qui vient d'etre creee

            connection.commit();

        }catch (SQLException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fermetureSilencieuse(statementDropTable);
            fermetureSilencieuse(statementCreateTable);
            fermetureSilencieuse(connection);
        }
    }

    **/




}

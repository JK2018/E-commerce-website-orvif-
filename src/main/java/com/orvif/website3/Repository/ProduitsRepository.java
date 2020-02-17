package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitsRepository extends JpaRepository<Produits, Integer> {
/**
 @Query(value = "SELECT * FROM PRODUITS WHERE codeArticle IN (SELECT * FROM (SELECT DISTINCT(codeArticle) FROM PRODUITS LIMIT 5, :number )as t) ORDER BY codeArticle;",
 nativeQuery = true)
 public List<Groupe> getRandomGroups(@Param("number") Integer number, String cleClient) throws DAOException {

 PreparedStatement preparedStmt = null;
 ResultSet resultSet = null;
 try {

 //  preparedStmt = connection.prepareStatement("SELECT * FROM PRODUITS WHERE codeArticle IN (SELECT * FROM (SELECT DISTINCT(codeArticle) FROM PRODUITS LIMIT 5," + number + ")as t) ORDER BY codeArticle;");
 // resultSet = preparedStmt.executeQuery();
 List<Groupe> groupes = new ArrayList<>();
 Groupe currentGroupe = null;
 while (resultSet.next()) {
 int codeArticle = resultSet.getInt("codeArticle");
 Produit produit = new Produit();
 produit.setId(resultSet.getInt("id_produits"));
 produit.setCleSystem(resultSet.getInt("cle_system"));
 produit.setLibelle(resultSet.getString("libelle"));
 produit.setCodeOrvif(resultSet.getString("code_orvif"));
 produit.setDestockage(resultSet.getInt("destockage") == 1);
 if (currentGroupe == null || currentGroupe.getId() != codeArticle) {
 if (currentGroupe != null) {
 groupes.add(currentGroupe);
 }
 currentGroupe = new Groupe();
 currentGroupe.setId(codeArticle);
 List<com.orvif.bean.Document> documents = new ArrayList<>();
 documents.add(daoFactory.getDocumentDao().getFirstImageByProduct(produit.getId()));
 produit.setImageCollection(documents);
 }
 currentGroupe.getProducts().add(produit);
 }
 if (currentGroupe != null) {
 groupes.add(currentGroupe);
 }
 getProductsInfoFromNxGroup(groupes, cleClient);
 return groupes;
 } catch (DAOException e) {
 throw e;
 } catch (SQLException e) {
 throw new DAOException("SQL ERROR : " + e.getMessage());
 } catch (Exception e) {
 e.printStackTrace();
 throw new DAOException(e.getClass().getName() + " exception while fetching random groups.");
 } finally {
 fermeturesSilencieuses(resultSet, preparedStmt, connection);
 }
 }**/


}

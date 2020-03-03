package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CaracteristiquesProduits;
import com.orvif.website3.Entity.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ProduitsRepository extends JpaRepository<Produits, Integer> {



    Produits findByCleSystem(int cleSystem);

//add produit OK ! make sure it is updated if the product already exists
//("SELECT * FROM PRODUITS WHERE codeArticle IN (SELECT * FROM (SELECT DISTINCT(codeArticle) FROM PRODUITS LIMIT 5," + number + ")as t) ORDER BY codeArticle;");


    @Query(value = "SELECT id_produits, cle_system, code_orvif, ref_fournisseur, libelle, descriptif, avantages, ppht, eco_part, eco_mobilier," +
            " id_marques, id_uv, id_uf, id_familles, id_ssfamilles, id_categories, id_sscategories, id_gammes, visible, code_article, available, " +
            "defi, destockage, libelle_url, obligatoire, ppht_public, ppttc, ppttc_public " +
            "FROM PRODUITS WHERE code_article IN (SELECT * FROM (SELECT DISTINCT(code_article) FROM PRODUITS LIMIT 5, :number )as t) ORDER BY code_article", nativeQuery = true)
    List<Produits> getRandomGroups2( @Param("number") int number);

    // for method setProductNotVisible in ProduitsHelper
    @Query(value = "UPDATE PRODUITS SET visible = '0' WHERE cle_system = :cle_system" , nativeQuery = true)
    void setProductNotVisible(@Param("cle_system") int cle_system);

    // for method countProductInSubCategory in ProduitsHelper
    @Query(value = "SELECT COUNT(DISTINCT code_article) FROM PRODUITS WHERE id_sscategories = :id_sscategories AND visible = 1", nativeQuery = true)
    int countProdInSubCategory(@Param("id_sscategories") int id_sscategories);

    // for method countProductInSubCategory in ProduitsHelper
    @Query(value = "SELECT COUNT(DISTINCT code_article) FROM PRODUITS WHERE id_categories = :id_categories AND visible = 1", nativeQuery = true)
    int countProdInSubCategory2(@Param("id_categories") int id_categories);

    // for method checkAllKeys in ProduitsHelper
    @Query(value = "SELECT cle_system FROM PRODUITS WHERE visible = 1", nativeQuery = true)
    List<Integer> checkAllKeys();

    // for method checkKeys in ProduitsHelper
    @Query(value = "SELECT id_produits FROM PRODUITS WHERE cle_system = :cle_system", nativeQuery = true)
    Produits checkKeys(@Param("cle_system") String cle_system);

    // for method buildCaracteristiqueArborescence in ProduitsHelper
    @Query(value = "SELECT COUNT(*) FROM PRODUITS WHERE id_categories = :id_categories", nativeQuery = true)
    int buildCaracteristiqueArborescence1(@Param("id_categories") int id_categories);

    // for method buildCaracteristiqueArborescence in ProduitsHelper
    @Query(value = "SELECT COUNT(*) FROM PRODUITS P WHERE P.id_categories = :id_categories AND EXISTS (SELECT * FROM CARACTERISTIQUES_PRODUITS CP WHERE P.id_produits = CP.id_produits AND CP.id_caracteristiques = :id_caracteristiques)", nativeQuery = true)
    int buildCaracteristiqueArborescence2(@Param("id_categories") int id_categoriess, @Param("id_caracteristiques") int id_caracteristiques);

    //THIS QUERY IS IN CARACTERISTIQUESPRODUITSREPO !
    //for method buildCaracteristiqueArborescence in ProduitsHelper
    @Query(value = "SELECT DISTINCT(valeur) FROM CARACTERISTIQUES_PRODUITS WHERE id_caracteristiques = :id_caracteristiques AND id_produits IN (SELECT id_produits FROM PRODUITS WHERE id_categories = :id_categories)", nativeQuery = true)
    List<CaracteristiquesProduits> buildCaracteristiqueArborescence3(@Param("id_caracteristiques") int id_caracteristiques, @Param("id_categories") int id_categories);

    // for method  getBySearchProduitAssocie in ProduitsHelper
    @Query(value = "SELECT COUNT(id_produits),MAX(id_produits),libelle,descriptif,code_article,id_marques FROM PRODUITS WHERE (libelle LIKE :search1 OR code_orvif LIKE :search2 OR ref_fournisseur LIKE :search3) GROUP BY codeArticle,libelle,descriptif,id_marques AND id_produits NOT IN (:listId)", nativeQuery = true)
    ArrayList<Produits>  getBySearchProduitAssocie(@Param("search1") String search1, @Param("search2") String search2, @Param("search3") String search3, @Param("listId") StringBuilder listId);

    // for method  getBySearchAdmin in ProduitsHelper
    @Query(value = "SELECT COUNT(id_produits),MAX(id_produits),libelle,descriptif,code_article,id_marques FROM PRODUITS WHERE (libelle LIKE :libelle OR code_orvif LIKE :code_orvif OR ref_fournisseur LIKE :ref_fournisseur) GROUP BY code_article,libelle,descriptif,id_marques", nativeQuery = true)
    ArrayList<Produits> getBySearchAdmin(@Param("libelle") String libelle, @Param("code_orvif") String code_orvif, @Param("ref_fournisseur") String ref_fournisseur);

    // for method  getGroupeByCodeArticle in ProduitsHelper
    @Query(value = "SELECT * FROM PRODUITS WHERE code_article = :code_article", nativeQuery = true)
    ArrayList<Produits> getGroupeByCodeArticle(@Param("code_article") int code_article);

    // for method  getByCleSysteme in ProduitsHelper
    @Query(value = "SELECT * FROM PRODUITS WHERE cle_system = :cle_system", nativeQuery = true)
    Produits getByCleSysteme(@Param("cle_system") int cle_system);

    // for method  getProduitsSimilaire in ProduitsHelper
    @Query(value = "SELECT * FROM PRODUITS P, PRODUITS_ALTERNATIFS PA WHERE P.id_produits = PA.id_produits_alt AND PA.id_produits = :id_produits AND P.visible = 1", nativeQuery = true)
    ArrayList<Produits> getProduitsSimilaire(@Param("id_produits") int id_produits);

    // for method  getProduitsComplementaire in ProduitsHelper
    @Query(value = "SELECT * FROM PRODUITS P, PRODUITS_COMPLEMENTAIRES PC WHERE P.id_produits = PC.id_produits_comp AND PC.id_produits = :id_produits AND P.visible = 1", nativeQuery = true)
    ArrayList<Produits> getProduitsComplementaire(@Param("id_produits") int id_produits);

    // for method  getById in ProduitsHelper
    @Query(value = "SELECT * FROM PRODUITS WHERE id_produits = :id_produits", nativeQuery = true)
    Produits getById(@Param("id_produits") int id_produits);

    // for method  getGroupeByProduct in ProduitsHelper
    @Query(value = "SELECT * FROM PRODUITS WHERE code_article = (SELECT code_article FROM PRODUITS WHERE id_produits = :id_produits) AND visible = 1", nativeQuery = true)
    ArrayList<Produits> getGroupeByProduct(@Param("id_produits") int id_produits);

    // for method  fillDisplayListItemsBySearch in ProduitsHelper
    @Query(value = "SELECT * FROM PRODUITS WHERE code_article IN (SELECT * FROM (SELECT DISTINCT(code_article) FROM PRODUITS WHERE code_orvif = :code_orvif OR ref_fournisseur = :ref_fournisseur OR libelle LIKE %:words0% AND visible = 1 LIMIT :int1 , :int2 ) as t) ORDER BY code_article", nativeQuery = true)
    ArrayList<Produits> fillDisplayListItemsBySearch1(@Param("code_orvif") String code_orvif, @Param("ref_fournisseur") String ref_fournisseur, @Param("words0") String words0, @Param("int1") int int1, @Param("int2") int int2 );

    // for method  fillDisplayListItemsBySearch in ProduitsHelper
    @Query(value = "SELECT * FROM PRODUITS WHERE code_article IN (SELECT * FROM (SELECT DISTINCT code_article FROM PRODUITS WHERE libelle LIKE :param1 ", nativeQuery = true)
    ArrayList<Produits> fillDisplayListItemsBySearch2(@Param("param1") String param1);

    // for method  fillDisplayListItemsBySearch in ProduitsHelper
    @Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT(code_article) FROM PRODUITS WHERE code_orvif = :code_orvif OR ref_fournisseur = :ref_fournisseur OR libelle LIKE :param1 AND visible = 1) as t ORDER BY code_article", nativeQuery = true)
    int fillDisplayListItemsBySearch3(@Param("code_orvif") String code_orvif, @Param("ref_fournisseur") String ref_fournisseur, @Param("param1") String param1);

    // for method  fillDisplayListItemsBySearch in ProduitsHelper
    @Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT code_article FROM PRODUITS WHERE libelle LIKE :param1 ", nativeQuery = true)
    int fillDisplayListItemsBySearch4(@Param("param1") String param1);

    // for method  fillDisplayListItems in ProduitsHelper
    @Query(value = ":theQuery", nativeQuery = true)
    ArrayList<Produits> fillDisplayListItems(@Param("theQuery") String theQuery);

    // for method  fillDisplayListItems in ProduitsHelper
    @Query(value = ":theQuery", nativeQuery = true)
    int fillDisplayListItems3(@Param("theQuery") String theQuery);

    // for method  changeVisibility in ProduitsHelper
    @Query(value = "UPDATE PRODUITS SET visible = :visible WHERE cle_system = :cle_system", nativeQuery = true)
    void changeVisibility(@Param("visible") boolean visible, @Param("cle_system") int cle_system);

    // for method   update in produitshelper
    @Query(value = "UPDATE PRODUITS SET cle_system = :cle_system, code_orvif = :code_orvif, ref_fournisseur = :ref_fournisseur, libelle = :libelle, descriptif = :descriptif, avantages = :avantages, id_marques = :id_marques, id_uv = :id_uv, id_uf = :id_uf, id_familles = :id_familles, id_ssfamilles = :id_ssfamilles, id_categories = :id_categories, id_sscategories = :id_sscategories, id_gammes = :id_gammes, visible = :visible WHERE id_produits = :id_produits", nativeQuery = true)
    void updateCustom(@Param("id_produits") int id_produits, @Param("cle_system") int cle_system, @Param("code_orvif") String code_orvif, @Param("ref_fournisseur") String ref_fournisseur, @Param("libelle") String libelle, @Param("descriptif") String descriptif, @Param("avantages") String avantages, @Param("id_marques") int id_marques, @Param("id_uv") int id_uv, @Param("id_uf") int id_uf, @Param("id_familles") int id_familles, @Param("id_ssfamilles") int id_ssfamilles, @Param("id_categories") int id_categories, @Param("id_sscategories") int id_sscategories, @Param("id_gammes") int id_gammes, @Param("visible") boolean visible);












}

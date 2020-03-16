package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Categories;
import com.orvif.website3.Entity.Familles;
import com.orvif.website3.Entity.SsFamilles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {






    // for method getIdByName in ProduitsHelper
    //this query is a dynamic query in several repositories , check method in ProduitsHelper
    @Query(value = "SELECT id_categories FROM categories WHERE libelle = :libelle" , nativeQuery = true)
    int getIdByName(@Param("libelle") String libelle);



    // for method getGroupeByProduct && getByIdProduit in ProduitsHelper
    @Query(value = "SELECT * FROM CATEGORIES C,PRODUITS P WHERE C.id_categories=P.id_categories AND P.id_produits = :id_produits" , nativeQuery = true)
    Categories getByIdProduit(@Param("id_produits") int id_produits);



    // for method getBySousFamille in CategoriesHelper
    @Query(value = "SELECT * FROM CATEGORIES WHERE parent_ssfamille = :parent_ssfamille ORDER BY id_categories" , nativeQuery = true)
    ArrayList<Categories> getBySousFamille(@Param("parent_ssfamille") int parent_ssfamille);


    // for method getBySousCategorie in CategoriesHelper
    @Query(value = "SELECT * FROM CATEGORIES WHERE id_categories = (SELECT parent_categorie FROM SS_CATEGORIES WHERE id_sscategories = :id_sscategories) ORDER BY id_categories" , nativeQuery = true)
    Categories getBySousCategorie(@Param("id_sscategories") int id_sscategories);

    // for ProduitsController
    @Query(value = "SELECT * FROM categories WHERE libelle = :libelle" , nativeQuery = true)
    Categories getByLibelle(@Param("libelle") String libelle);




}

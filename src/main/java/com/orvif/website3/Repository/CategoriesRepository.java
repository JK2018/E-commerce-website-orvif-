package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Categories;
import com.orvif.website3.Entity.SsFamilles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {






    // for method getIdByName in ProduitsHelper
    //this query is a dynamic query in several repositories , check method in ProduitsHelper
    @Query(value = "SELECT id_categories FROM categories WHERE libelle = :libelle" , nativeQuery = true)
    int getIdByName(@Param("libelle") String libelle);



    // for method getGroupeByProduct in ProduitsHelper
    @Query(value = "SELECT * FROM CATEGORIES C,PRODUITS P WHERE C.id_categories=P.id_categories AND P.id_produits = :id_produits" , nativeQuery = true)
    Categories getByIdProduit(@Param("id_produits") int id_produits);










}

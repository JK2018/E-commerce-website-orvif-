package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CaracteristiquesCategories;
import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesCategoriesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CaracteristiquesCategoriesRepository extends JpaRepository<CaracteristiquesCategories, CaracteristiquesCategoriesPK> {



    // for method buildCaracteristiqueArborescence in ProduitsHelper
    @Query(value = "INSERT INTO CARACTERISTIQUES_CATEGORIES(id_caracteristiques,id_categories,valeur) VALUES(:id_caracteristiques,:id_categories,:valeur) ON DUPLICATE KEY UPDATE id_caracteristiques=id_caracteristiques", nativeQuery = true)
    void buildCaracteristiqueArborescence4(@Param("id_caracteristiques") int id_caracteristiques, @Param("id_categories") int id_categories, @Param("valeur") String valeur );


}

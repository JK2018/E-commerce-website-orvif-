package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CaracteristiquesCategories;
import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesCategoriesPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaracteristiquesCategoriesRepository extends JpaRepository<CaracteristiquesCategories, CaracteristiquesCategoriesPK> {
}

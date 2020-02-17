package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Caracteristiques;
import com.orvif.website3.Entity.CompositeKeys.CaracteristiquesProduitsPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaracteristiquesRepository extends JpaRepository<Caracteristiques, CaracteristiquesProduitsPK> {
}

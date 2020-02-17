package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CaracteristiqueReference;
import com.orvif.website3.Entity.CompositeKeys.CaracteristiqueReferencePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaracteristiqueReferenceRepository extends JpaRepository<CaracteristiqueReference, CaracteristiqueReferencePK> {
}

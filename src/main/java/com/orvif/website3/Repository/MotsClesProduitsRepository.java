package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.MotsClesProduitsPK;
import com.orvif.website3.Entity.MotsClesProduits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotsClesProduitsRepository extends JpaRepository<MotsClesProduits, MotsClesProduitsPK> {
}

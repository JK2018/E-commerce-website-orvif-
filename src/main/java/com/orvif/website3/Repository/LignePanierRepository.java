package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.LignePanierPK;
import com.orvif.website3.Entity.LignePanier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LignePanierRepository extends JpaRepository<LignePanier, LignePanierPK> {
}

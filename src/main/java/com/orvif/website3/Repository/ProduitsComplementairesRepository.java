package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.ProduitsComplementairesPK;
import com.orvif.website3.Entity.ProduitsComplementaires;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitsComplementairesRepository extends JpaRepository<ProduitsComplementaires, ProduitsComplementairesPK> {
}

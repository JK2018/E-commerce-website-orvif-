package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.ProduitsAlternatifsPK;
import com.orvif.website3.Entity.ProduitsAlternatifs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitsAlternatifsRepository extends JpaRepository<ProduitsAlternatifs, ProduitsAlternatifsPK> {
}

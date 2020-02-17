package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.HistoriqueModificationPK;
import com.orvif.website3.Entity.HistoriqueModification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueModificationRepository extends JpaRepository<HistoriqueModification, HistoriqueModificationPK> {
}

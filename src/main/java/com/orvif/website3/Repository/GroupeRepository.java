package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.GroupePK;
import com.orvif.website3.Entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeRepository extends JpaRepository<Groupe, GroupePK> {
}

package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.GroupeCriterePK;
import com.orvif.website3.Entity.GroupeCritere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeCritereRepository extends JpaRepository<GroupeCritere, GroupeCriterePK> {
}

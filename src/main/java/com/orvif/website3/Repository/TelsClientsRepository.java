package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CompositeKeys.TelsClientsPK;
import com.orvif.website3.Entity.TelsClients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelsClientsRepository extends JpaRepository<TelsClients, TelsClientsPK> {
}

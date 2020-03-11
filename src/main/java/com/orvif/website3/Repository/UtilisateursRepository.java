package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UtilisateursRepository extends JpaRepository<Utilisateurs, Integer> {




    // for method getAdminByLogin in UtilisateursHelper
    @Query(value = "SELECT * FROM UTILISATEURS WHERE login = :login", nativeQuery = true)
    Utilisateurs findByLogin(@Param("login") String login);


























}

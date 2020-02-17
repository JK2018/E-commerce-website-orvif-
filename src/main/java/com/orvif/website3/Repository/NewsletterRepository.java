package com.orvif.website3.Repository;


import com.orvif.website3.Entity.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, Integer> {
}

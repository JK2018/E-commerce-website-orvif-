package com.orvif.website3.Repository;


import com.orvif.website3.Entity.CodeConfirm;
import com.orvif.website3.Entity.CompositeKeys.CodeConfirmPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeConfirmRepository extends JpaRepository<CodeConfirm, CodeConfirmPK> {
}

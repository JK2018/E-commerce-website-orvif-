package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.CodeConfirmPK;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "code_confirm", schema = "t_orvif_web_dev")
@IdClass(CodeConfirmPK.class)
public class CodeConfirm {
    private String code;
    private int client;
    private Timestamp date;
    private String typeCode;

    @Id
    @Column(name = "code", nullable = false, length = 25)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Id
    @Column(name = "client", nullable = false)
    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Id
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "type_code", nullable = false, length = 45)
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeConfirm that = (CodeConfirm) o;

        if (client != that.client) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (typeCode != null ? !typeCode.equals(that.typeCode) : that.typeCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + client;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (typeCode != null ? typeCode.hashCode() : 0);
        return result;
    }
}

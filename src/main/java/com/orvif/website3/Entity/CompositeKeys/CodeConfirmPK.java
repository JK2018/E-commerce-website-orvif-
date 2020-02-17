package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

public class CodeConfirmPK implements Serializable {
    private String code;
    private int client;
    private Timestamp date;

    @Column(name = "code", nullable = false, length = 25)
    @Id
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "client", nullable = false)
    @Id
    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Column(name = "date", nullable = false)
    @Id
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeConfirmPK that = (CodeConfirmPK) o;

        if (client != that.client) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + client;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}

package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CaracteristiqueReferencePK implements Serializable {
    private int idCaract;
    private int idReference;

    @Column(name = "id_caract", nullable = false)
    @Id
    public int getIdCaract() {
        return idCaract;
    }

    public void setIdCaract(int idCaract) {
        this.idCaract = idCaract;
    }

    @Column(name = "id_reference", nullable = false)
    @Id
    public int getIdReference() {
        return idReference;
    }

    public void setIdReference(int idReference) {
        this.idReference = idReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CaracteristiqueReferencePK that = (CaracteristiqueReferencePK) o;

        if (idCaract != that.idCaract) return false;
        if (idReference != that.idReference) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCaract;
        result = 31 * result + idReference;
        return result;
    }
}

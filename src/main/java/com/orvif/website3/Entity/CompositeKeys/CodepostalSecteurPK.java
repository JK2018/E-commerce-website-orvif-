package com.orvif.website3.Entity.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CodepostalSecteurPK implements Serializable {
    private int idSecteur;
    private String codePostal;

    @Column(name = "id_secteur", nullable = false)
    @Id
    public int getIdSecteur() {
        return idSecteur;
    }

    public void setIdSecteur(int idSecteur) {
        this.idSecteur = idSecteur;
    }

    @Column(name = "code_postal", nullable = false, length = 7)
    @Id
    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodepostalSecteurPK that = (CodepostalSecteurPK) o;

        if (idSecteur != that.idSecteur) return false;
        if (codePostal != null ? !codePostal.equals(that.codePostal) : that.codePostal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSecteur;
        result = 31 * result + (codePostal != null ? codePostal.hashCode() : 0);
        return result;
    }
}

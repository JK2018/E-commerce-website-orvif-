package com.orvif.website3.Entity;


import com.orvif.website3.Entity.CompositeKeys.CodepostalSecteurPK;

import javax.persistence.*;

@Entity
@Table(name = "codepostal_secteur", schema = "t_orvif_web_dev")
@IdClass(CodepostalSecteurPK.class)
public class CodepostalSecteur {
    private int idSecteur;
    private String codePostal;

    @Id
    @Column(name = "id_secteur", nullable = false)
    public int getIdSecteur() {
        return idSecteur;
    }

    public void setIdSecteur(int idSecteur) {
        this.idSecteur = idSecteur;
    }

    @Id
    @Column(name = "code_postal", nullable = false, length = 7)
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

        CodepostalSecteur that = (CodepostalSecteur) o;

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

package com.orvif.website3.Entity;

import com.orvif.website3.Repository.DAOException;
import com.orvif.website3.Repository.DocumentRepository;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "document", schema = "t_orvif_web_dev")
public class Document {
    private int id;
    private String url;
    private String type;
    private String titre;
    private String description;

    @Autowired
    private DocumentHelper dh;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url", nullable = false, length = 100)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 45)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "titre", nullable = false, length = 45)
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 150)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (id != document.id) return false;
        if (url != null ? !url.equals(document.url) : document.url != null) return false;
        if (type != null ? !type.equals(document.type) : document.type != null) return false;
        if (titre != null ? !titre.equals(document.titre) : document.titre != null) return false;
        if (description != null ? !description.equals(document.description) : document.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (titre != null ? titre.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

/**
    public Map<String, List<Document>> getDocumentListByProduit(int id){
        Map<String, List<com.orvif.website3.Entity.Document>>  ret = dh.getDocumentListByProduit(id);
        return ret;
    }**/








}

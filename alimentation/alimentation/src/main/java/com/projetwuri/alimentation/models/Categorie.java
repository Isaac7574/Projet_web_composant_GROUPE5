package com.projetwuri.alimentation.models;

import com.projetwuri.alimentation.abstractClass.AbstractId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "categorie")

public class Categorie extends AbstractId {

    private String libelle;
    private String description;
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Produit> produits = new ArrayList<>();



    public String getLibelle() {
        return libelle;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public String getDescription() {
        return description;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}

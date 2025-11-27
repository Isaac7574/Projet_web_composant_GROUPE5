package com.projetwuri.alimentation.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetwuri.alimentation.abstractClass.AbstractId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "produit")
public class Produit extends AbstractId {

    private String nom;
    private String description;
    private int prix;
    @ManyToOne
    @JoinColumn(name = "categorie", referencedColumnName = "id")
    @JsonIgnore
    private Categorie categorie;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

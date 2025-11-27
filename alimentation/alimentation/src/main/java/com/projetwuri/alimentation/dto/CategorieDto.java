package com.projetwuri.alimentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(NON_NULL)
public class CategorieDto {
    private String libelle;
    private String description;
    private List<UUID> produitIds = new ArrayList<>();


    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UUID> getProduitIds() {
        return produitIds;
    }

    public void setProduitIds(List<UUID> produitIds) {
        this.produitIds = produitIds;
    }
}

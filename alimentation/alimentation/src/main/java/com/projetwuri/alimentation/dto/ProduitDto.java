package com.projetwuri.alimentation.dto;

import com.projetwuri.alimentation.models.Categorie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProduitDto {

    private String nom;
    private String description;
    private int prix;
    private Categorie categorie;
}

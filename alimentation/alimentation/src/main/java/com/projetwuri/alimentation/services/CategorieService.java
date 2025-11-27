package com.projetwuri.alimentation.services;

import com.projetwuri.alimentation.dto.CategorieDto;
import com.projetwuri.alimentation.models.Categorie;

import java.util.List;
import java.util.UUID;

public interface CategorieService {

    Categorie creerCategorie(Categorie categorie);

    Categorie modifierCategorie(CategorieDto categorieDto, UUID idCategorie);

    Categorie consulterCategorie(UUID idCategorie);

    List<Categorie> listerAllCategorie();

    void supprimerCategorie(UUID idCategorie);

    Categorie creerCategories(CategorieDto categorieDto);
}

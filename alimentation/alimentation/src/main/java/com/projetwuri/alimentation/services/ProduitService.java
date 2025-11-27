package com.projetwuri.alimentation.services;

import com.projetwuri.alimentation.models.Produit;

import java.util.List;
import java.util.UUID;

public interface ProduitService {
    Produit creerProduit(Produit produit);

    Produit modifierProduit(Produit produit, UUID idProduit);

    List<Produit> listerAllProduits();

    Produit consulterProduitById(UUID idProduit);

    Produit affecterProduitACategorie(UUID idProduit, UUID idCategorie);

    void supprimerProduit(UUID idProduit);

    Produit retirerProduitDeCategorie(UUID idProduit, UUID idCategorie);

    List<String> readCurrentLogFile(String logFilePath);

    List<String> readLogFileByDate(String logDirectory, String date);
}

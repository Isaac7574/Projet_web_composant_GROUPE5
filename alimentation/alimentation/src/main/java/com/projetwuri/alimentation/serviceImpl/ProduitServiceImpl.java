package com.projetwuri.alimentation.serviceImpl;

import com.projetwuri.alimentation.models.Categorie;
import com.projetwuri.alimentation.models.Produit;
import com.projetwuri.alimentation.repository.CategorieRepository;
import com.projetwuri.alimentation.repository.ProduitRepository;
import com.projetwuri.alimentation.services.ProduitService;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;

    // 🔵 MÉTRIQUES PROMETHEUS
    private final Counter produitCreeCounter;
    private final Counter produitModifieCounter;
    private final Counter produitSupprimeCounter;
    private final Timer tempsCreationProduit;

    public ProduitServiceImpl(ProduitRepository produitRepository,
                              CategorieRepository categorieRepository,
                              MeterRegistry registry) {

        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;

        // Compteur produit créé
        this.produitCreeCounter = Counter.builder("produit_cree_total")
                .description("Nombre total de produits créés")
                .register(registry);

        // Compteur produit modifié
        this.produitModifieCounter = Counter.builder("produit_modifie_total")
                .description("Nombre total de produits modifiés")
                .register(registry);

        // Compteur produit supprimé
        this.produitSupprimeCounter = Counter.builder("produit_supprime_total")
                .description("Nombre total de produits supprimés")
                .register(registry);

        // Timer pour mesurer le temps de création
        this.tempsCreationProduit = registry.timer("temps_creation_produit_seconds");

        registry.gauge("nombre_produits_total",
                (Number) produitRepository.count()
        );

    }

    @Override
    public Produit creerProduit(Produit produit) {
        // ⏱ Timer qui mesure la durée de la création
        return tempsCreationProduit.record(() -> {
            Produit saved = produitRepository.save(produit);
            produitCreeCounter.increment(); // compteur
            return saved;
        });
    }

    @Override
    public Produit modifierProduit(Produit produit, UUID idProduit) {
        Produit produitExisted = produitRepository.findById(idProduit).orElse(null);
        if (produitExisted != null){
            produitExisted.setNom(produit.getNom());
            produitExisted.setPrix(produit.getPrix());
            produitExisted.setDescription(produit.getDescription());
            produitModifieCounter.increment(); // compteur
            return produitRepository.save(produitExisted);
        }
        return null;
    }

    @Override
    public List<Produit> listerAllProduits() {
        return produitRepository.findAll();
    }

    @Override
    public Produit consulterProduitById(UUID idProduit) {
        return produitRepository.findById(idProduit).get();
    }

    @Override
    public void supprimerProduit(UUID idProduit) {
        produitRepository.deleteById(idProduit);
        produitSupprimeCounter.increment(); // compteur
    }

    @Override
    public Produit affecterProduitACategorie(UUID idProduit, UUID idCategorie) {
        Produit produit = produitRepository.findById(idProduit)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id : " + idProduit));
        Categorie categorie = categorieRepository.findById(idCategorie)
                .orElseThrow(() -> new RuntimeException("Categorie non trouvée avec l'id : " + idCategorie));

        produit.setCategorie(categorie);
        return produitRepository.save(produit);
    }

    @Override
    public Produit retirerProduitDeCategorie(UUID idProduit, UUID idCategorie) {
        Produit produit = produitRepository.findById(idProduit)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id : " + idProduit));
        Categorie categorie = categorieRepository.findById(idCategorie)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'id : " + idCategorie));
        if (produit.getCategorie() != null && produit.getCategorie().getId().equals(idCategorie)) {
            categorie.getProduits().removeIf(p -> p.getId().equals(idProduit));
            produit.setCategorie(null);
            categorieRepository.save(categorie);
            return produitRepository.save(produit);
        } else {
            throw new RuntimeException("Ce produit n'appartient pas à cette catégorie.");
        }
    }

    @Override
    public List<String> readCurrentLogFile(String logFilePath) {
        List<String> logLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logLines;
    }

    @Override
    public List<String> readLogFileByDate(String logDirectory, String date) {
        List<String> logLines = new ArrayList<>();
        String fileName = "alimentation." + date + ".log";
        Path logPath = Paths.get(logDirectory, fileName);

        if (!Files.exists(logPath)) {
            System.err.println("Fichier de log introuvable : " + logPath);
            return logLines;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(logPath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logLines;
    }
}

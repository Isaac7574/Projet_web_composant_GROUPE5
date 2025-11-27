package com.projetwuri.alimentation.controller;

import com.projetwuri.alimentation.models.Produit;
import com.projetwuri.alimentation.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
public class ProduitController {

    private ProduitService produitService;
    private static final String LOG_FILE_PATH = Paths.get("logs", "alimentation.log").toString();

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @PostMapping("api/v1/produit/creer")
    public ResponseEntity<Produit> creerProduit(@RequestBody Produit produit){
        Produit produitCreate=produitService.creerProduit(produit);
        return   ResponseEntity.status(HttpStatus.CREATED).body(produitCreate);
    }

    @PutMapping("api/v1/produit/update/{id}")
    public ResponseEntity<Produit> modifierProduit(@RequestBody Produit produit, @PathVariable UUID id)  {
        Produit produitCreate=produitService.modifierProduit(produit,id);
        return   ResponseEntity.status(HttpStatus.OK).body(produitCreate);
    }

    @GetMapping("api/v1/produit/liste")
    public ResponseEntity<List<Produit>> listerProduits(){
        List<Produit> produits=produitService.listerAllProduits();
        return   ResponseEntity.status(HttpStatus.OK).body(produits);
    }


    @GetMapping("api/v1/produit/consulter/{id}")
    public ResponseEntity<Produit> consulterProduitById(@PathVariable UUID id)  {
        Produit produit=produitService.consulterProduitById(id);
        return   ResponseEntity.status(HttpStatus.OK).body(produit);
    }

    @DeleteMapping("api/v1/produit/delete/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable UUID id) {
        produitService.supprimerProduit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("api/v1/idProduit/{idProduit}/idCategorie/{idCategorie}")
    public ResponseEntity<Produit> affecterProduitACategorie(@PathVariable UUID idProduit, @PathVariable UUID idCategorie) {
        Produit produitAffecte = produitService.affecterProduitACategorie(idProduit, idCategorie);
        return ResponseEntity.ok(produitAffecte);
    }


    @PutMapping("api/v1/idProduit/{idProduit}/retirer-produit/idCategorie/{idCategorie}")
    public ResponseEntity<Produit> retirerProduitDeCategorie(
            @PathVariable UUID idCategorie,
            @PathVariable UUID idProduit) {
        Produit produit = produitService.retirerProduitDeCategorie(idProduit, idCategorie);
        return ResponseEntity.ok(produit);
    }


         /*----------------------------------------------------------------------------/
        /                      lire dans fichier log courant                           /
       /----------------------------------------------------------------------------*/

    @GetMapping("api/v1/logs")
    public List<String> getCurrentLog() {
        // Chemin du fichier journal courant (non archivé)
        String currentLogFilePath = "logs/alimentation.log";
        // Lire les lignes du fichier journal
        return produitService.readCurrentLogFile(currentLogFilePath);
    }

        /*----------------------------------------------------------------------------/
        /          lire dans fichier log courant (par date)                          /
       /----------------------------------------------------------------------------*/


    @GetMapping("api/v1/logs/date")
    public ResponseEntity<List<String>> getLogsByDate(@RequestParam String date) {
        List<String> logs = produitService.readLogFileByDate("logs", date);
        if (logs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of("Aucun log trouvé pour cette date"));
        }
        return ResponseEntity.ok(logs);
    }
}

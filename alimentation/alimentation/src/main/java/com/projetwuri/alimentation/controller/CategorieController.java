package com.projetwuri.alimentation.controller;

import com.projetwuri.alimentation.dto.CategorieDto;
import com.projetwuri.alimentation.models.Categorie;
import com.projetwuri.alimentation.models.Produit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projetwuri.alimentation.services.CategorieService;

import java.util.List;
import java.util.UUID;

@RestController
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }



    @PostMapping("api/v1/categorie/creer")
    public ResponseEntity<Categorie> creerCategories(@RequestBody CategorieDto categorieDto) {
        Categorie categorie = categorieService.creerCategories(categorieDto);
        return   ResponseEntity.status(HttpStatus.CREATED).body(categorie);
    }

    @PutMapping("api/v1/categorie/update/{id}")
    public ResponseEntity<Categorie> modifierCategorie(@RequestBody CategorieDto categorieDto, @PathVariable UUID id)  {
        Categorie categorieCreate=categorieService.modifierCategorie(categorieDto,id);
        return   ResponseEntity.status(HttpStatus.OK).body(categorieCreate);
    }

    @DeleteMapping("api/v1/categorie/delete/{id}")
    public ResponseEntity<Void> supprimerCategorie(@PathVariable UUID id)  {
        categorieService.supprimerCategorie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("api/v1/categorie/liste")
    public ResponseEntity<List<Categorie>> listerCategorie()  {
        List<Categorie> categories=categorieService.listerAllCategorie();
        return   ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("api/v1/categorie/consulter/{id}")
    public ResponseEntity<Categorie> consulterCategorieById( @PathVariable UUID id)  {
        Categorie categorie=categorieService.consulterCategorie(id);
        return   ResponseEntity.status(HttpStatus.OK).body(categorie);
    }
}

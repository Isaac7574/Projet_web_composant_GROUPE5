package com.projetwuri.alimentation.serviceImpl;

import com.projetwuri.alimentation.dto.CategorieDto;
import com.projetwuri.alimentation.models.Categorie;
import com.projetwuri.alimentation.repository.CategorieRepository;
import com.projetwuri.alimentation.repository.ProduitRepository;
import com.projetwuri.alimentation.services.CategorieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final ProduitRepository produitRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository, ProduitRepository produitRepository) {
        this.categorieRepository = categorieRepository;
        this.produitRepository = produitRepository;
    }

    @Override
    public Categorie creerCategorie(Categorie categorie) {
        return  categorieRepository.save(categorie);
    }

    @Override
    public Categorie modifierCategorie(CategorieDto categorieDto, UUID idCategorie) {
        Categorie categorieExisted=categorieRepository.findById(idCategorie).get();
        if (categorieExisted != null){
            categorieExisted.setLibelle(categorieDto.getLibelle());
            categorieExisted.setDescription(categorieDto.getDescription());
            categorieExisted.setProduits(produitRepository.findAllById(categorieDto.getProduitIds()));
            return categorieRepository.save(categorieExisted);
        }
        else return null;
    }

    @Override
    public Categorie consulterCategorie(UUID idCategorie) {
        return categorieRepository.findById(idCategorie).get();
    }

    @Override
    public List<Categorie> listerAllCategorie() {
        return categorieRepository.findAll();
    }

    @Override
    public void supprimerCategorie(UUID idCategorie) {
        categorieRepository.deleteById(idCategorie);
    }

    @Override
    @Transactional
    public Categorie creerCategories(CategorieDto categorieDto) {
        Categorie categorie = new Categorie();
        categorie.setLibelle(categorieDto.getLibelle());
        categorie.setDescription(categorieDto.getDescription());
        categorie.setProduits(produitRepository.findAllById(categorieDto.getProduitIds()));
        return  categorieRepository.save(categorie);
    }

}

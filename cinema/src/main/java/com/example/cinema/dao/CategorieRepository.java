package com.example.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.cinema.entities.Categorie;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path="categories")
@CrossOrigin("*")

public interface CategorieRepository extends JpaRepository<Categorie,Long> {

}

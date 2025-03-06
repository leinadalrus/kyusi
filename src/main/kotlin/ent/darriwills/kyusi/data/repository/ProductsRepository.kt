package ent.darriwills.kyusi.data.repository

import org.springframework.data.jpa.repository.JpaRepository

import ent.darriwills.kyusi.models.Products

public interface ProductsRepository extends JpaRepository<Products, Long>() {}
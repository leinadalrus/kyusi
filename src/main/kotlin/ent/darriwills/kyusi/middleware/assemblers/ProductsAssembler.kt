package ent.darriwills.kyusi.middleware.assemblers

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.stereotype.Component

import ent.darriwills.kyusi.controllers.ProductsController
import ent.darriwills.kyusi.models.Products

@Component
public class ProductsAssembler
    : RepresentationModelAssembler<Products, EntityModel<Products>> {
    @Override
    public fun EntityModel<Products> toModel(Products products) {
        return EntityModel.of(products,
            linkTo(methodOn(ProductsController.class).findById(products.getId())).withSelfRel(),
            linkTo(methodOn(ProductsController.class).findAll()).withRel("products"))
    }
}
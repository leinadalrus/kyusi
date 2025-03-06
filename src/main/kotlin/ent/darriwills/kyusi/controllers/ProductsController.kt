package ent.darriwills.kyusi.controllers

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import ent.darriwills.kyusi.data.repository.ProductsRepository
import ent.darriwills.kyusi.middleware.assemblers.ProductsAssembler
import ent.darriwills.kyusi.models.Products

@RestController
public class ProductsController(val repository: ProductsRepository,
    val assembler: ProductsAssembler
) {
    @GetMapping("/products/{id}")
    fun findById(@PathVariable val id: Long): EntityModel<Products> {
        Products products = repository.findById(id)
            .orElseThrow(() -> new ProductsNotFoundException(id))

        return assembler.toModel(products)
    }

    fun findAll(): CollectionModel<EntityModel<Products>> {
        List<EntityModel<Products>> = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList())
        
        return CollectionModel.of(products,
            linkTo(methodOn(ProductsController.class).all()).withSelfRel())
    }

    @PostMapping("/products")
    fun createProduct(@RequestBody val newProduct: Products): ResponseEntity<?> {
        EntityModel<Products> model = assembler.toModel(repository.save(newProduct))

        return ResponseEntity
            .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(model)
    }


    @PutMapping("/products/{id}")
    fun updateProduct(@RequestBody val newProduct: Products,
        @PathVariable val id: Long
    ): ResponseEntity<?> {
        Products updatedProduct = repository.findById(id)
            .map(product -> {
                product.setName(newProduct.getName())
                product.setDescription(newProduct.getDescription())
            })
            .orElseGet(() -> {
                return repository.save(newProduct)
            })

        EntityModel<Products> model = assembler.toModel(updatedProduct)

        return ResponseEntity
            .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(model)
    }

    @DeleteMapping("/products/{id}")
    fun deleteProduct(@PathVariable val id: Long): ResponseEntity<?> {
        repository.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}
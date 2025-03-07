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

import ent.darriwills.kyusi.data.repository.OrdersRepository
import ent.darriwills.kyusi.middleware.assemblers.OrdersAssembler
import ent.darriwills.kyusi.middleware.exceptions.OrdersNotFoundException
import ent.darriwills.kyusi.models.Orders

@RestController
public class OrdersController(val repository: OrdersRepository,
    val assembler: OrdersAssembler
) {
    @GetMapping("/orders/{id}")
    fun findById(@PathVariable val id: Long): EntityModel<Orders> {
        Orders orders = repository.findById(id)
            .orElseThrow(() -> new OrdersNotFoundException(id))

        return assembler.toModel(orders)
    }

    fun findAll(): CollectionModel<EntityModel<Orders>> {
        List<EntityModel<Orders>> = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList())
        
        return CollectionModel.of(orders,
            linkTo(methodOn(OrdersController.class).all()).withSelfRel())
    }

    @PostMapping("/orders")
    fun createOrder(@RequestBody val newOrder: Orders): ResponseEntity<?> {
        EntityModel<Orders> model = assembler.toModel(repository.save(newOrder))

        return ResponseEntity
            .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(model)
    }


    @PutMapping("/orders/{id}")
    fun updateOrder(@RequestBody val newOrder: Orders,
        @PathVariable val id: Long
    ): ResponseEntity<?> {
        Orders updatedOrder = repository.findById(id)
            .map(order -> {
                order.setName(newOrder.getAuthor().getName())
            })
            .orElseGet(() -> {
                return repository.save(newOrder)
            })

        EntityModel<Orders> model = assembler.toModel(updatedOrder)

        return ResponseEntity
            .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(model)
    }

    @DeleteMapping("/orders/{id}")
    fun deleteOrder(@PathVariable val id: Long): ResponseEntity<?> {
        repository.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}
package ent.darriwills.kyusi.middleware.assemblers

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.stereotype.Component

import ent.darriwills.kyusi.controllers.OrdersController
import ent.darriwills.kyusi.models.Orders

@Component
public class OrdersAssembler
    : RepresentationModelAssembler<Orders, EntityModel<Orders>> {
    @Override
    public fun EntityModel<Orders> toModel(Orders orders) {
        return EntityModel.of(orders,
            linkTo(methodOn(OrdersController.class).findById(orders.getId())).withSelfRel(),
            linkTo(methodOn(OrdersController.class).findAll()).withRel("orders"))
    }
}
package ent.darriwills.kyusi.middleware.assemblers

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.stereotype.Component

import ent.darriwills.kyusi.controllers.UserController
import ent.darriwills.kyusi.models.User

@Component
public class UserAssembler
    : RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public fun EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
            linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel(),
            linkTo(methodOn(UserController.class).findAll()).withRel("users"))
    }
}
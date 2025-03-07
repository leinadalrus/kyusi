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

import ent.darriwills.kyusi.data.repository.UserRepository
import ent.darriwills.kyusi.middleware.assemblers.UserAssembler
import ent.darriwills.kyusi.middleware.exceptions.UserNotFoundException
import ent.darriwills.kyusi.models.User

@RestController
public class UserController(val repository: UserRepository,
    val assembler: UserAssembler
) {
    @GetMapping("/users/{id}")
    fun findById(@PathVariable val id: Long): EntityModel<User> {
        User user = repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id))

        return assembler.toModel(user)
    }

    fun findAll(): CollectionModel<EntityModel<User>> {
        List<EntityModel<User>> model = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList())
        
        return CollectionModel.of(model,
            linkTo(methodOn(UserController.class).all()).withSelfRel())
    }

    @PostMapping("/users")
    fun createUser(@RequestBody val newUser: User): ResponseEntity<?> {
        EntityModel<User> model = assembler.toModel(repository.save(newUser))

        return ResponseEntity
            .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(model)
    }


    @PutMapping("/users/{id}")
    fun updateUser(@RequestBody val newUser: User,
        @PathVariable val id: Long
    ): ResponseEntity<?> {
        User updatedUser = repository.findById(id)
            .map(user -> {
                user.setName(newUser.getName())
            })
            .orElseGet(() -> {
                return repository.save(newUser)
            })

        EntityModel<User> model = assembler.toModel(updatedUser)

        return ResponseEntity
            .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(model)
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable val id: Long): ResponseEntity<?> {
        repository.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}
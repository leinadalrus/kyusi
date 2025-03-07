package ent.darriwills.kyusi.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Products(
    @Id @GeneratedValue var id: Long? = null,
    @ManyToOne var author: User,
    var name: String,
    var description: String,
    var createdOn: LocalDateTime = LocalDateTime.now()
) {
    fun getId(): Long {
        return this.id
    }

    fun getName(): String {
        return this.name
    }

    fun getCreatedOn(): LocalDateTime {
        return this.createdOn
    }

    fun setName(val newName: String) {
        this.name = newName
    }

    @Override
    fun int hashCode() {
        return Objects.hash(this.id, this.name, this.createdOn)
    }
}
package ent.darriwills.kyusi.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDERS")
public class Orders(
    @Id @GeneratedValue var id: Long? = null,
    @ManyToOne var author: User,
    var createdOn: LocalDateTime = LocalDateTime.now()
) {
    fun getId(): Long {
        return this.id
    }

    fun getAuthor(): User {
        return this.author
    }

    fun getCreatedOn(): LocalDateTime {
        return this.createdOn
    }

    @Override
    fun int hashCode() {
        return Objects.hash(this.id, this.author, this.createdOn)
    }
}
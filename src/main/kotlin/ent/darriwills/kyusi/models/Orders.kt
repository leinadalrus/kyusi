package ent.darriwills.kyusi.models;

@Entity
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
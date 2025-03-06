package ent.darriwills.kyusi.models

public class User(
    @Id @GeneratedValue var id: Long? = null,
    var name: String,
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
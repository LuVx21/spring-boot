package org.luvx.kotlin.entity

import javax.persistence.*

@Entity
@Table(name = "user")
class User {
    @get:Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var userName: String? = null

    @Column(nullable = false)
    var password: String? = null

    @Column(nullable = true)
    var age: Int? = null

    override fun toString(): String {
        return "User(id=$id, userName=$userName, password=$password, age=$age)"
    }
}
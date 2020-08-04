package org.luvx.kotlin.enrity

import javax.persistence.*

@Entity
@Table(name = "user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1

    @Column(nullable = false)
    var userName: String? = null

    @Column(nullable = false)
    var password: String? = null

    @Column(nullable = true)
    var age: Int = -1
}
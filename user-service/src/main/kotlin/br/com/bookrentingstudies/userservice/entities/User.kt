package br.com.bookrentingstudies.userservice.entities

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name="users")
class User (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field:Size(min = 5, max =58)
    @field:NotNull
    var name: String,

    @field:Size(min = 5, max =58)
    @field:NotNull
    var type: String,

    @field:Email
    var mail: String,

    var telephone: String,

    @field:NotNull
    var active: Boolean,

    @field:NotNull
    var login: String,

    @field:NotNull
    var password: String



  )
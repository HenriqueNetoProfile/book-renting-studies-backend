package br.com.bookrentingstudies.userservice.repositories

import br.com.bookrentingstudies.userservice.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository:JpaRepository<User, Long> {

    fun findByLoginAndPassword(login:String, password:String):List<User>

}
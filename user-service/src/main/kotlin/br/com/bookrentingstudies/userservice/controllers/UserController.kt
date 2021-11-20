package br.com.bookrentingstudies.userservice.controllers

import br.com.bookrentingstudies.userservice.entities.User
import br.com.bookrentingstudies.userservice.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.math.BigInteger
import java.security.MessageDigest
import javax.persistence.EntityNotFoundException


@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var repository: UserRepository

    @GetMapping("/find-all")
    fun findAll(): List<User> {
        return repository.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id:Long): User {
        return repository.findById(id).orElseThrow{EntityNotFoundException(String.format("Não foi possível encontrar o usuário com id %s", id))}
    }

    @GetMapping("/user-by-login-data/{login}/{password}")
    fun userByLoginData(@PathVariable("login") login:String, @PathVariable("password") password:String): User {
        return repository.findByLoginAndPassword(login, password = md5(password)).first()
    }

    @PutMapping("/update")
    fun update(@RequestBody newUser: User): User {


        if(newUser.id >= 0){
            val oldUser = repository.findById(newUser.id).orElseThrow{EntityNotFoundException(String.format("Não foi possível encontrar o usuário com id %s", newUser.id))}
            oldUser.apply {
                this.active = newUser.active
                this.login = newUser.login
                this.name = newUser.name
                this.mail = newUser.mail
                this.password = md5(newUser.password)
                this.telephone = newUser.telephone
                this.login = newUser.login
                this.type = newUser.type

            }
            return repository.save(oldUser)

        }else{
            throw IllegalArgumentException("Um campos Id válido  deve ser informado na atualização.")
        }

    }

    @PutMapping("/change-status/{id}/{status}")
    fun changeStatus(@PathVariable("id") id:Long, @PathVariable("status") status:Boolean): User {
        if(id >= 0){
            val oldUser = repository.findById(id).orElseThrow{EntityNotFoundException(String.format("Não foi possível encontrar o usuário com id %s", id))}
            oldUser.apply {
                this.active = status
            }
            return repository.save(oldUser)

        }else{
            throw IllegalArgumentException("Um campos Id válido  deve ser informado na atualização.")
        }
    }

    @PostMapping("/save")
    fun save(@RequestBody user: User): User {
        user.password = md5(user.password)
        return repository.save(user)
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

}
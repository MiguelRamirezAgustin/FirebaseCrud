package com.example.crudfirebase.appFirebase.data.repository

import com.example.crudfirebase.appFirebase.data.remote.FirebaseAuthService
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test


class AuthRepositoryTest{
    private lateinit var repository: AuthRepository

    private val service: FirebaseAuthService = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = AuthRepository(service)
    }

    @Test
    fun login_callsFirebaseService() {

        repository.login(
            "test@test.com",
            "123456"
        ) { _, _ ->

        }

        verify {
            service.loginUser(
                "test@test.com",
                "123456",
                any()
            )
        }
    }

    @Test
    fun register_callsFirebaseService() {

        repository.register(
            email = "miguel@gmail.com",
            password = "123456",
            name = "Miguel",
            phone = "2381234567",
            gender = "Hombre",
            birthdate = "01/01/2000"
        ) { _, _ ->

        }

        verify {
            service.registerUser(
                "miguel@gmail.com",
                "123456",
                "Miguel",
                "2381234567",
                "Hombre",
                "01/01/2000",
                any()
            )
        }
    }

    @Test
    fun update_callsFirebaseService() {

        repository.update(
            uid = "123",
            email = "miguel@gmail.com",
            name = "Miguel",
            phone = "2381234567",
            gender = "Hombre",
            birthdate = "01/01/2000"
        ) { _, _ ->

        }

        verify {
            service.updateUser(
                "123",
                "miguel@gmail.com",
                "Miguel",
                "2381234567",
                "Hombre",
                "01/01/2000",
                any()
            )
        }
    }

    @Test
    fun deleteUser_callsFirebaseService() {

        repository.deleteUser("123") { _, _ ->

        }

        verify {
            service.deleteUser(
                "123",
                any()
            )
        }
    }

    @Test
    fun getUsers_callsFirebaseService() {

        repository.getUsers {

        }

        verify {
            service.getUsers(any())
        }
    }
}
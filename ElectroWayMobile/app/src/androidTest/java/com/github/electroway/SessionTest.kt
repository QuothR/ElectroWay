package com.github.electroway

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.electroway.models.Register
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionTest {
    val session = Session()

    @Test
    fun authenticate() {
        val info = Register(
            username = "username",
            password = "Password1!",
            firstName = "firstName",
            lastName = "lastName",
            phoneNumber = "phoneNumber",
            emailAddress = "electroway@mailinator.com",
            address1 = "address1",
            city = "city",
            country = "country",
            zipcode = "zipcode"
        )
        val response = session.register(info)
        Log.w("a", "fdsfsd")
        Log.w("a", "")
        Log.w("a", response)
    }
}
package com.github.electroway.ui.start

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.electroway.R
import com.github.electroway.RegisterInfo
import com.github.electroway.Session
import com.github.electroway.ui.main.ProfileFragment
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class CreateAccountNextFragment : Fragment() {
    lateinit var firstName: Editable
    lateinit var lastName: Editable
    lateinit var phoneNumber: Editable
    lateinit var country: Editable
    lateinit var city: Editable
    lateinit var address: Editable
    lateinit var zipcode: Editable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_account_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstName = view.findViewById<EditText>(R.id.create_account_first_name_edit_text).text
        lastName = view.findViewById<EditText>(R.id.create_account_last_name_edit_text).text
        phoneNumber = view.findViewById<EditText>(R.id.create_account_phone_number_edit_text).text
        country = view.findViewById<EditText>(R.id.create_account_country_edit_text).text
        city = view.findViewById<EditText>(R.id.create_account_city_edit_text).text
        address = view.findViewById<EditText>(R.id.create_account_address_edit_text).text
        zipcode = view.findViewById<EditText>(R.id.create_account_zipcode_edit_text).text

        view.findViewById<Button>(R.id.create_account_next_cancel_button).setOnClickListener {
            findNavController().navigate(R.id.action_createAccountNextFragment_to_signInFragment)
        }

        view.findViewById<Button>(R.id.create_account_next_sign_up_button).setOnClickListener {
            val session = Session()
            val info = RegisterInfo(
                username = CreateAccountFragment.username.toString(),
                password = CreateAccountFragment.password.toString(),
                emailAddress = CreateAccountFragment.email.toString(),
                firstName = firstName.toString(),
                lastName = lastName.toString(),
                phoneNumber = phoneNumber.toString(),
                address1 = address.toString(),
                city = city.toString(),
                country = country.toString(),
                zipcode = zipcode.toString()
            )
            ProfileFragment.info = info
            session.register(
                info,
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val handler = Handler(requireContext().mainLooper)
                        handler.post {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    requireContext(),
                                    "Check your Email",
                                    Toast.LENGTH_SHORT
                                )
                                findNavController().navigate(R.id.action_createAccountNextFragment_to_mapFragment)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Request unsuccessful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            )
        }
    }
}

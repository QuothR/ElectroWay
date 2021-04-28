package com.github.electroway.ui.start

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.electroway.Application
import com.github.electroway.LoginInfo
import com.github.electroway.R
import com.github.electroway.Session
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<EditText>(R.id.sign_in_email_edit_text).text
        val password = view.findViewById<EditText>(R.id.sign_in_password_edit_text).text

        view.findViewById<TextView>(R.id.sign_in_forgot_password_text).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }

        view.findViewById<Button>(R.id.sign_in_create_account_button).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_createAccountFragment)
        }

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            val session = (requireActivity().application as Application).session
            val info = LoginInfo(
                email = email.toString(),
                password = password.toString()
            )
            session.login(info, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body!!.string()
                    val handler = Handler(requireContext().mainLooper)
                    handler.post {
                        if (response.isSuccessful) {
                            session.changeToken(body)
                            findNavController().navigate(R.id.action_signInFragment_to_mapFragment)
                        } else {
                            Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            })
        }
    }
}
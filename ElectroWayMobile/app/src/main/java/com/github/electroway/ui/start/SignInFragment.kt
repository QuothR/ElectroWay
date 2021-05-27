package com.github.electroway.ui.start

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.electroway.Application
import com.github.electroway.models.Login
import com.github.electroway.R
import com.github.electroway.network.Session
import com.google.android.material.textfield.TextInputEditText

class SignInFragment : Fragment() {
    lateinit var session: Session

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = (requireActivity().application as Application).session

        val emailEditable = view.findViewById<TextInputEditText>(R.id.sign_in_email_edit_text)
        val passwordEditable = view.findViewById<TextInputEditText>(R.id.sign_in_password_edit_text)

        view.findViewById<TextView>(R.id.sign_in_forgot_password_text).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }

        view.findViewById<Button>(R.id.sign_in_create_account_button).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_createAccountFragment)
        }

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            login(
                Login(emailEditable.text.toString(), passwordEditable.text.toString())
            )
        }

        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val email = preferences.getString("email", null)
        val password = preferences.getString("password", null)
        if (email != null && password != null) {
            login(Login(email, password))
        }
    }

    private fun login(info: Login) {
        session.login(info) { success ->
            if (success) {
                val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
                val checkBox =
                    requireView().findViewById<CheckBox>(R.id.sign_in_remember_me_checkbox)
                if (checkBox.isChecked) {
                    val editor = preferences.edit()
                    editor.putString("email", info.email)
                    editor.putString("password", info.password)
                    editor.apply()
                }
                findNavController().navigate(R.id.action_signInFragment_to_mapFragment)
            } else {
                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
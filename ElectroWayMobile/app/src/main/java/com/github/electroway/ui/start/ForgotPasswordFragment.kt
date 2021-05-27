package com.github.electroway.ui.start

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.electroway.Application
import com.github.electroway.R
import com.github.electroway.network.Session
import com.google.android.material.textfield.TextInputLayout

class ForgotPasswordFragment : Fragment() {
    lateinit var session: Session

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = (requireActivity().application as Application).session

        val emailEditable = view.findViewById<TextInputLayout>(R.id.forgot_password_email_address_edit_text)

        view.findViewById<Button>(R.id.forgot_password_cancel_button).setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
        }

        view.findViewById<Button>(R.id.forgot_password_confirm_button).setOnClickListener {
            forgotPassword(emailEditable.toString())
        }
    }


    private fun forgotPassword(emailEditable: String) {
        session.forgotPassword(emailEditable) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Check your inbox", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
            }
            else {
                Toast.makeText(requireContext(), "Wrong email", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
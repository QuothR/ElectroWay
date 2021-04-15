package com.github.electroway.ui.start

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.electroway.R

class SignInFragment : Fragment() {
    private val fail = false;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.sign_in_forgot_password_text).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }

        view.findViewById<Button>(R.id.sign_in_create_account_button).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_createAccountFragment)
        }

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            if (fail) {
                val toast =
                    Toast.makeText(requireContext(), "Connection failed", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                findNavController().navigate(R.id.action_signInFragment_to_mapFragment)
            }
        }
    }
}
package com.github.electroway.ui.start

import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.electroway.R

class CreateAccountFragment : Fragment() {
    companion object {
        lateinit var username: Editable
        lateinit var email: Editable
        lateinit var password: Editable
        lateinit var confirmPassword: Editable
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = view.findViewById<EditText>(R.id.create_account_username_edit_text).text
        email = view.findViewById<EditText>(R.id.create_account_email_edit_text).text
        password = view.findViewById<EditText>(R.id.create_account_password_edit_text).text
        confirmPassword =
            view.findViewById<EditText>(R.id.create_account_confirm_password_edit_text).text

        view.findViewById<Button>(R.id.create_account_cancel_button).setOnClickListener {
            findNavController().navigate(R.id.action_createAccountFragment_to_signInFragment)
        }

        view.findViewById<Button>(R.id.create_account_next_button).setOnClickListener {
            val invalidMessage = getInvalidMessage()
            if (invalidMessage != null) {
                Toast.makeText(requireContext(), invalidMessage, Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_createAccountFragment_to_createAccountNextFragment)
            }
        }
    }

    private fun getInvalidMessage(): String? {

        if (!Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()) {
            return "Invalid Email"
        }
        if (username.toString() == "") {
            return "Username cannot be empty"
        }
        if (password.length < 8) {
            return "Password length must be at least 8 characters"
        }
        if (!password.contains(Regex(("\\d")))) {
            return "Password must contain at least one digit"
        }
        if (!password.contains(Regex("[a-z]"))) {
            return "Password must contain at least one lowercase letter"
        }
        if (!password.contains(Regex("[A-Z]"))) {
            return "Password must contain at least one uppercase letter"
        }
        if (!password.contains(Regex("[\"!\\\"#\$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~¡¢£¤¥¦§¨©ª«¬\\u00ad®¯°±²³´µ¶·¸¹º»¼½¾¿×÷–—―‗‘’‚‛“”„†‡•…‰′″‹›‼‾⁄⁊₠₡₢₣₤₥₦₧₨₩₪₫€₭₮₯₰₱₲₳₴₵₶₷₸₹₺₻₼₽₾\"]"))) {
            return "Password must contain at least one special character"
        }
        if (password.toString() != confirmPassword.toString()) {
            return "Passwords do not match"
        }
        return null
    }
}
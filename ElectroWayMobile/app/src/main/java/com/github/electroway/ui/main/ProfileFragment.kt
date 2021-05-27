package com.github.electroway.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.electroway.Application
import com.github.electroway.R
import com.github.electroway.models.Wallet
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileFragment : Fragment() {
    private lateinit var walletDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        walletDialog = BottomSheetDialog(requireActivity())
        walletDialog.setContentView(
            inflater.inflate(
                R.layout.wallet_dialog,
                container,
                false
            )
        )
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val session = (requireActivity().application as Application).session
        session.userInfo { json ->
            if (json == null) {
                Toast.makeText(
                    requireContext(),
                    "Failed to get profile information",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                view.findViewById<EditText>(R.id.firstNameText).text.append(json.getString("firstName"))
                view.findViewById<EditText>(R.id.lastNameText).text.append(json.getString("lastName"))
                view.findViewById<EditText>(R.id.emailText).text.append(json.getString("emailAddress"))
                view.findViewById<EditText>(R.id.phoneNumberText).text.append(json.getString("phoneNumber"))
                view.findViewById<EditText>(R.id.addressText).text.append(json.getString("address1"))
                view.findViewById<EditText>(R.id.cityText).text.append(json.getString("city"))
                view.findViewById<EditText>(R.id.countryText).text.append(json.getString("country"))
                view.findViewById<EditText>(R.id.zipcodeText).text.append(json.getString("zipcode"))
            }
        }

        view.findViewById<Button>(R.id.signOutButton).setOnClickListener {
            val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            (requireParentFragment().requireParentFragment() as HomeFragment).navigateToSignIn()
        }

        view.findViewById<Button>(R.id.updateWalletButton).setOnClickListener {
            walletDialog.show()
            walletDialog.findViewById<Button>(R.id.submitWalletButton)!!.setOnClickListener {
                val clientId =
                    walletDialog.findViewById<EditText>(R.id.clientIdText)!!.text.toString()
                val secret = walletDialog.findViewById<EditText>(R.id.secretText)!!.text.toString()
                session.updateWallet(Wallet(clientId, secret)) {
                    if (it) {
                        walletDialog.hide()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to update wallet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
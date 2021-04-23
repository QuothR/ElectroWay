package com.github.electroway.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.github.electroway.R
import com.github.electroway.RegisterInfo

class ProfileFragment : Fragment() {
    companion object {
        lateinit var info: RegisterInfo
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<EditText>(R.id.firstNameText).text.append(info.firstName)
        view.findViewById<EditText>(R.id.lastNameText).text.append(info.lastName)
        view.findViewById<EditText>(R.id.emailText).text.append(info.emailAddress)
        view.findViewById<EditText>(R.id.phoneNumberText).text.append(info.phoneNumber)
        view.findViewById<EditText>(R.id.addressText).text.append(info.address1)
        view.findViewById<EditText>(R.id.cityText).text.append(info.city)
        view.findViewById<EditText>(R.id.countryText).text.append(info.country)
        view.findViewById<EditText>(R.id.zipcodeText).text.append(info.zipcode)
    }
}
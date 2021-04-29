package com.github.electroway.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.github.electroway.Application
import com.github.electroway.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val session = (requireActivity().application as Application).session
        session.userInfo(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val handler = Handler(requireContext().mainLooper)
                val json = JSONObject(response.body!!.string())
                handler.post {
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
        })

        view.findViewById<Button>(R.id.signOutButton).setOnClickListener {
            val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.commit()
            (requireParentFragment().requireParentFragment() as HomeFragment).navigateToSignIn()
        }
    }
}
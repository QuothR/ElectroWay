package com.github.electroway.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.R

class HelpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.helpRecyclerView)
        var items = listOf(
            Pair(
                """
                What is Electroway?
            """.trimIndent(),
                """
                It’s an app designed for drivers of electric cars to find electric charging station. It shows:
                •  Shows locations on map
                •  You can set details of your car
                •  You’ll get stations based on your car’s needs and location
            """.trimIndent()
            ),
            Pair(
                """
                How do I contact Electroway Customer Support?
            """.trimIndent(),
                """
                You can contact us at ….. for any problems.
            """.trimIndent()
            ),
            Pair(
                """
                How do I validate my account?
            """.trimIndent(),
                """
                After registration you will get through email a link which will activate your account
            """.trimIndent()
            ),
            Pair(
                """
                How many cars can I add?
            """.trimIndent(),
                """
                You have to add at least one car so that you can use the app.
            """.trimIndent()
            ),
            Pair(
                """
                How many stations can I add?
            """.trimIndent(),
                """
                There is not a limit for the number of stations added.
            """.trimIndent()
            ),
            Pair(
                """
                How do I get a copy of my supply receipt?
            """.trimIndent(),
                """
                You can find all your receipts at our website https://.......... after signing into your account at Statistics.
            """.trimIndent()
            ),
            Pair(
                """
                Terms & Conditions
            """.trimIndent(),
                """
    <h1> Customer Service Electroway </h1>
    <p>
        Email:
        <a href="mailto:electroway@mailinator.com">electroway@mailinator.com</a>
    </p>
    <p>
        Phone number:
        <a href="tel:(+40) 711111111">(+40) 711111111</a>
    </p>

    <h3>    I. Contractual Relationship </h3>
    <p>
    By using our Service, you are agreeing to these Terms including that you are of legal age to enter into binding contracts and that you already own at least an electrical car, so please read them carefully. You are also agreeing that you have authority to agree to these Terms, whether personally or on behalf of an entity you’ve included in your user account registration.
    </p>
    <p>
    You have agreed to comply with these Terms as a condition of using our Services. We may amend these Terms from time to time and the revised version will be available for you in our Help section.
    </p>
    <p>
    You may of course stop using our Services at any time, and we may terminate these Terms or your use of any Services, or generally cease offering or deny access to any portion of the Services, at any time and for any reason in our sole discretion.
    </p>

    <h3>  II. The Types of Information We Collect </h3>
    <p>
        We only collect or receive information about your location. We use and disclose this information for the purposes described further below:
    </p>
    <p>
        We collect and process location data when you sign up and use the Services. For example, to accurately show you potential stations near your location, it is necessary to collect the physical location of your device( the data isn’t saved on our server). In the event you register your own electrical vehicle on our Services, we collect location data to show you the location of your vehicle and then you can start your trip.
    </p>

    <h3> III. Your Electroway User Account </h3>
    <h4> Account Set Up. </h4>
    <p>
        In order to use most aspects of the Services, you must register for and maintain an active personal user account,  /*  which requires a valid debit or credit card or other approved payment method with expiration date */ What you provide to us must be true, accurate, complete and updated as necessary to remain accurate. If applicable, create a username and a strong password and don’t share either with anyone – your account is personal to you and not intended for anyone else, and you are responsible for all activity that occurs under it. Let us know immediately if you suspect unauthorized use of your account.
    </p>
    <h5> 
        <u>!! You agree to our Terms & Conditions by creating an account to our app !! </u> 
    </h5>
    <h4> A Note on Fraud. </h4>
    <p>
        If we suspect that any information you’ve provided is inaccurate, incomplete or fraudulent, we may suspend or terminate your account until the issue is resolved. During that time, you will lose access to some or all of our Services, either temporarily or permanently.
    </p>

    <h3> IV. Your Personal Information </h3>
    <p>
        No personal information is saved on our application, apart from your location for us to generate your routes for future destinations.
    </p>
    <p>
        !! Your location isn’t saved on our server  !!
    </p>

    <h3> V. Functionalities </h3>
    <h4> Map View </h4> 
    <p>
        This functionality is available only after login, where you can see your location and potential stations for charging.
    </p>
    <h4> View recharging price </h4>
    <p>
        Some rides may have a minimum fee that could be in addition to any applicable start/unlock fee. This fee will be saved on our website in the Statistics section.
    </p>
    <h4> Generated routes </h4>
    <p>
        A variety of routes for the user to choose from to go to the wanted destination.This functionality is only using the location of your vehicle and not any other infos about the user.
    </p>

    <h5> !!IMPORTANT NOTE: THE GENERATED ROUTES BY OUR APP ARE NOT 100% ACCURATE SINCE THE APPLICATION IS STILL IN DEVELOPMENT !! </h5>

    <h3> VI. Financial Terms </h3>
    <h4>   Pricing & Payment </h4>
    <p>
        Before you start a ride, you will see the applicable fees (e.g., start/unlock fee, per minute fee, and/or minimum fee). For the purpose of calculating fees incurred, ride times will be rounded up to the nearest minute. Please note that we may change pricing for our Services at any time as we deem necessary or appropriate for our business. Our pricing is exclusive of taxes (like sales and value added) and other applicable governmental charges.
    </p>
    <p>
        Fees and charges will be charged to a payment method in your account. We will automatically charge and withhold the applicable taxes as required by law. The fees can be seen on our website, in the Statistics section.
    </p>
    <p>
        As a policy, except to the extent required by applicable laws, we do not offer refunds for your use of our Services, and any exceptions to this policy are in our sole discretion.
    </p>

    <h5> !! The application is still in development and is not ready to be used by potential users !! </h5>
                """.trimIndent()
            )
        )
        val adapter = HelpAdapter(items)
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        view.findViewById<ImageButton>(R.id.helpBackButton).setOnClickListener {
            findNavController().navigate(R.id.action_helpFragment_to_mapFragment)
        }
    }
}
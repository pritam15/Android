package com.example.listfragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class SecondFragment : Fragment() {
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_second,container,false)

        val position = arguments?.getInt("position")!!.toInt()
        var country = ArrayList<String>()
        country.addAll( resources.getStringArray(R.array.countries) )
        var countryName: String = country.get(position)
        var textView: TextView
        textView = view.findViewById(R.id.textView)
        textView.text = "($position) <$countryName>"

        return view
    }
}
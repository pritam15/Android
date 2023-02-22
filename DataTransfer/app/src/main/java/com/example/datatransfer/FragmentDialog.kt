package com.example.datatransfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment


class FragmentDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_dialog, container, false)
        val ok: Button = view.findViewById(R.id.ok)
        val textView_name : TextView = view.findViewById(R.id.textView_name)
        val textView_phoneNo : TextView = view.findViewById(R.id.textView_phoneNo)

            textView_name.text = arguments?.getString("name").toString()
            textView_phoneNo.text = arguments?.getLong("phoneNo",0).toString()


        ok.setOnClickListener {
            dialog!!.dismiss()
        }
        return view
    }

}
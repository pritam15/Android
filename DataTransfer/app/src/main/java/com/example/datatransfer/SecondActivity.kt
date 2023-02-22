package com.example.datatransfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import org.w3c.dom.Text

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val tv_name: TextView = findViewById(R.id.tv_name)
        val tv_phoneNo : TextView = findViewById(R.id.tv_phoneNo)
        val btn_toFreg: Button = findViewById(R.id.btn_transfer_toFrag)

        val bundle = intent.extras

        if (bundle != null) {
            tv_name.text = bundle.getString("name")
        tv_phoneNo.text = bundle.getLong("phoneNo").toString()
        }

        btn_toFreg.setOnClickListener {
            val fm : FragmentManager = supportFragmentManager
            val fragmentDialog = FragmentDialog()
            fragmentDialog.arguments = bundle
            fragmentDialog.show(fm,"FragmentDialog")
        }

    }
}

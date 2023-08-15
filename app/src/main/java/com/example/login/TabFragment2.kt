package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class TabFragment2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tab2, container, false)

        val btnIrAPremio = view.findViewById<Button>(R.id.bntPremio)
        btnIrAPremio.setOnClickListener {
            val intent = Intent(activity, points_page::class.java)
            startActivity(intent)
        }
        return view
    }
}

package com.nurbk.ps.informationindonesya.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nurbk.ps.informationindonesya.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setLanguage("en")
    }


    private fun setLanguage(lan: String) {
        val res = baseContext.resources
        val dr = res.displayMetrics
        val cr = res.configuration
        cr.setLocale(Locale(lan))
        res.updateConfiguration(cr, dr)
    }
}
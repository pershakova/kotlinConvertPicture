package com.kotlin.photorx2

import android.os.Bundle
import android.widget.Toast
import com.kotlin.photorx2.mvp.model.FileInfo
import com.kotlin.photorx2.mvp.presenter.MainPresenter
import com.kotlin.photorx2.mvp.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val presenter by moxyPresenter { MainPresenter(FileInfo("C:\\1.jpg", "C:\\1.png")) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { presenter.convertFile() }


    }

    override fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

}
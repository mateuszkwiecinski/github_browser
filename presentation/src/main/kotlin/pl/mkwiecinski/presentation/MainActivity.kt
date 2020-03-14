package pl.mkwiecinski.presentation

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity

internal class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

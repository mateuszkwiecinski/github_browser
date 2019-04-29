package pl.mkwiecinski.presentation.list

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestApplicationRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}

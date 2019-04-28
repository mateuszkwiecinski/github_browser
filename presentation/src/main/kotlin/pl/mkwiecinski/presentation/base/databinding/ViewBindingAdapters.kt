package pl.mkwiecinski.presentation.base.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("android:visibility")
fun View.bindVisibility(isVisible: Boolean?) {
    visibility = when (isVisible) {
        true -> View.VISIBLE
        false, null -> View.GONE
    }
}
@BindingAdapter("android:visibility")
fun FloatingActionButton.bindVisibility(isVisible: Boolean?) {
    when (isVisible) {
        true -> show()
        false, null -> hide()
    }
}

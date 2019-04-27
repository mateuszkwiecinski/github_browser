package pl.mkwiecinski.presentation.base.databinding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun View.bindVisibility(isVisible: Boolean?) {
    visibility = when (isVisible) {
        true -> View.VISIBLE
        false, null -> View.GONE
    }
}

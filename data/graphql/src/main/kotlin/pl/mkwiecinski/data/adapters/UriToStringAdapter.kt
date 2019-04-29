package pl.mkwiecinski.data.adapters

import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue

internal object UriToStringAdapter : CustomTypeAdapter<String> {

    override fun decode(value: CustomTypeValue<*>) = value.value.toString()

    override fun encode(value: String) = CustomTypeValue.fromRawValue(value)
}

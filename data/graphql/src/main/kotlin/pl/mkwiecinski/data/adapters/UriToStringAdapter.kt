package pl.mkwiecinski.data.adapters

import com.apollographql.apollo.api.Adapter
import com.apollographql.apollo.api.CustomScalarAdapters
import com.apollographql.apollo.api.json.JsonReader
import com.apollographql.apollo.api.json.JsonWriter

internal object UriToStringAdapter : Adapter<String?> {

    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters) = reader.nextString()

    override fun toJson(writer: JsonWriter, customScalarAdapters: CustomScalarAdapters, value: String?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value)
        }
    }
}

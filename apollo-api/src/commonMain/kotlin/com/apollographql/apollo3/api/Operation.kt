package com.apollographql.apollo3.api

import com.apollographql.apollo3.api.http.DefaultHttpRequestComposer
import com.apollographql.apollo3.api.http.HttpRequestComposerParams
import com.apollographql.apollo3.api.http.HttpMethod
import com.apollographql.apollo3.api.internal.ResponseBodyParser
import com.apollographql.apollo3.api.internal.json.BufferedSinkJsonWriter
import com.apollographql.apollo3.api.internal.json.writeObject
import com.apollographql.apollo3.api.json.JsonWriter
import okio.Buffer
import okio.BufferedSink
import okio.BufferedSource
import okio.ByteString

/**
 * Represents a GraphQL operation (mutation, query or subscription).
 */
interface Operation<D : Operation.Data> : Executable<D> {
  /**
   * Returns the raw GraphQL operation String.
   */
  fun document(): String

  /**
   * Returns GraphQL operation name
   */
  fun name(): String

  /**
   * Returns a unique identifier for this operation.
   */
  fun id(): String

  /**
   * Returns an Adapter that maps the server response data to/from generated model class [D].
   *
   * This is the low-level API generated by the compiler. Use [parseResponseBody] and [composeResponseBody] extension functions for a higher level API
   */
  override fun adapter(): ResponseAdapter<D>

  /**
   * Serializes the variables of this operation to a json to send over HTTP or to a Map that we can later use to resolve variables
   */
  override fun serializeVariables(writer: JsonWriter, responseAdapterCache: ResponseAdapterCache)

  /**
   * The tree of fields used for normalizing/reading from the cache
   */
  override fun responseFields(): List<ResponseField.FieldSet>

  /**
   * Marker interface for generated models built from data returned by the server in response to this operation.
   */
  interface Data : Executable.Data
}

fun <D : Operation.Data> Operation<D>.parseResponseBody(
    source: BufferedSource,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
): ApolloResponse<D> {
  return ResponseBodyParser.parse(source, this, responseAdapterCache)
}

fun <D : Operation.Data> Operation<D>.parseResponseBody(
    byteString: ByteString,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
): ApolloResponse<D> {
  return parseResponseBody(Buffer().write(byteString), responseAdapterCache)
}

fun <D : Operation.Data> Operation<D>.parseResponseBody(
    string: String,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
): ApolloResponse<D> {
  return parseResponseBody(Buffer().writeUtf8(string), responseAdapterCache)
}


fun <D : Operation.Data> Operation<D>.composeRequestBody(
    sink: BufferedSink,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
) {
  val composer = DefaultHttpRequestComposer("unused")

  val request = composer.compose(
      ApolloRequest(operation = this)
          .withExecutionContext(responseAdapterCache)
          .withExecutionContext(
              HttpRequestComposerParams(
                  method = HttpMethod.Post,
                  autoPersistQueries = false,
                  sendDocument = true,
                  extraHeaders = emptyMap()
              )
          )
  )

  request.body!!.writeTo(sink)
}

fun <D : Operation.Data> Operation<D>.composeRequestBody(
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
): String {
  return Buffer().apply {
    composeRequestBody(this, responseAdapterCache)
  }.readUtf8()
}

fun <D : Operation.Data> Operation<D>.composeResponseBody(
    sink: BufferedSink,
    data: D,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
    indent: String = "",
) {
  val writer = BufferedSinkJsonWriter(sink)
  writer.indent = indent
  writer.writeObject {
    name("data")
    adapter().toResponse(this, responseAdapterCache, data)
  }
}

fun <D : Operation.Data> Operation<D>.composeResponseBody(
    data: D,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
    indent: String = "",
): String {
  val buffer = Buffer()

  composeResponseBody(
      sink = buffer,
      data = data,
      responseAdapterCache = responseAdapterCache,
      indent = indent
  )

  return buffer.readUtf8()
}

fun <D : Operation.Data> Operation<D>.composeData(
    sink: BufferedSink,
    data: D,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
) {
  adapter().toResponse(BufferedSinkJsonWriter(sink), responseAdapterCache, data)
}

fun <D : Operation.Data> Operation<D>.composeData(
    data: D,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
): String {
  val buffer = Buffer()
  composeData(
      sink = buffer,
      data = data,
      responseAdapterCache = responseAdapterCache
  )
  return buffer.readUtf8()
}

fun <D : Operation.Data> Operation<D>.parseData(
    source: BufferedSource,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
): D {
  return adapter().fromJson(source, responseAdapterCache)
}

fun <D : Operation.Data> Operation<D>.parseData(
    byteString: ByteString,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
): D {
  return adapter().fromJson(Buffer().write(byteString), responseAdapterCache)
}

fun <D : Operation.Data> Operation<D>.parseData(
    string: String,
    responseAdapterCache: ResponseAdapterCache = ResponseAdapterCache.DEFAULT,
): D {
  return adapter().fromJson(string, responseAdapterCache)
}
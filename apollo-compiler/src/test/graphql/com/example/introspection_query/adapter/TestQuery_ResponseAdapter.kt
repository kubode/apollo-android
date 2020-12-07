// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.introspection_query.adapter

import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.internal.ResponseAdapter
import com.apollographql.apollo.api.internal.ResponseReader
import com.apollographql.apollo.api.internal.ResponseWriter
import com.example.introspection_query.TestQuery
import kotlin.Array
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter", "PropertyName",
    "RemoveRedundantQualifierName")
object TestQuery_ResponseAdapter : ResponseAdapter<TestQuery.Data> {
  private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
    ResponseField.forObject("__schema", "__schema", null, false, null),
    ResponseField.forObject("__type", "__type", mapOf<String, Any>(
      "name" to "Vehicle"), false, null)
  )

  override fun fromResponse(reader: ResponseReader, __typename: String?): TestQuery.Data {
    return Data.fromResponse(reader, __typename)
  }

  override fun toResponse(writer: ResponseWriter, value: TestQuery.Data) {
    Data.toResponse(writer, value)
  }

  object Data : ResponseAdapter<TestQuery.Data> {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
      ResponseField.forObject("__schema", "__schema", null, false, null),
      ResponseField.forObject("__type", "__type", mapOf<String, Any>(
        "name" to "Vehicle"), false, null)
    )

    override fun fromResponse(reader: ResponseReader, __typename: String?): TestQuery.Data {
      return reader.run {
        var __schema: TestQuery.Data.__Schema? = null
        var __type: TestQuery.Data.__Type? = null
        while(true) {
          when (selectField(RESPONSE_FIELDS)) {
            0 -> __schema = readObject<TestQuery.Data.__Schema>(RESPONSE_FIELDS[0]) { reader ->
              __Schema.fromResponse(reader)
            }
            1 -> __type = readObject<TestQuery.Data.__Type>(RESPONSE_FIELDS[1]) { reader ->
              __Type.fromResponse(reader)
            }
            else -> break
          }
        }
        TestQuery.Data(
          __schema = __schema!!,
          __type = __type!!
        )
      }
    }

    override fun toResponse(writer: ResponseWriter, value: TestQuery.Data) {
      writer.writeObject(RESPONSE_FIELDS[0]) { writer ->
        __Schema.toResponse(writer, value.__schema)
      }
      writer.writeObject(RESPONSE_FIELDS[1]) { writer ->
        __Type.toResponse(writer, value.__type)
      }
    }

    object __Schema : ResponseAdapter<TestQuery.Data.__Schema> {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
        ResponseField.forObject("queryType", "queryType", null, false, null),
        ResponseField.forList("types", "types", null, false, null)
      )

      override fun fromResponse(reader: ResponseReader, __typename: String?):
          TestQuery.Data.__Schema {
        return reader.run {
          var queryType: TestQuery.Data.__Schema.QueryType? = null
          var types: List<TestQuery.Data.__Schema.Type>? = null
          while(true) {
            when (selectField(RESPONSE_FIELDS)) {
              0 -> queryType = readObject<TestQuery.Data.__Schema.QueryType>(RESPONSE_FIELDS[0]) { reader ->
                QueryType.fromResponse(reader)
              }
              1 -> types = readList<TestQuery.Data.__Schema.Type>(RESPONSE_FIELDS[1]) { reader ->
                reader.readObject<TestQuery.Data.__Schema.Type> { reader ->
                  Type.fromResponse(reader)
                }
              }?.map { it!! }
              else -> break
            }
          }
          TestQuery.Data.__Schema(
            queryType = queryType!!,
            types = types!!
          )
        }
      }

      override fun toResponse(writer: ResponseWriter, value: TestQuery.Data.__Schema) {
        writer.writeObject(RESPONSE_FIELDS[0]) { writer ->
          QueryType.toResponse(writer, value.queryType)
        }
        writer.writeList(RESPONSE_FIELDS[1], value.types) { values, listItemWriter ->
          values?.forEach { value ->
            listItemWriter.writeObject { writer ->
              Type.toResponse(writer, value)
            }
          }
        }
      }

      object QueryType : ResponseAdapter<TestQuery.Data.__Schema.QueryType> {
        private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("name", "name", null, true, null)
        )

        override fun fromResponse(reader: ResponseReader, __typename: String?):
            TestQuery.Data.__Schema.QueryType {
          return reader.run {
            var name: String? = null
            while(true) {
              when (selectField(RESPONSE_FIELDS)) {
                0 -> name = readString(RESPONSE_FIELDS[0])
                else -> break
              }
            }
            TestQuery.Data.__Schema.QueryType(
              name = name
            )
          }
        }

        override fun toResponse(writer: ResponseWriter, value: TestQuery.Data.__Schema.QueryType) {
          writer.writeString(RESPONSE_FIELDS[0], value.name)
        }
      }

      object Type : ResponseAdapter<TestQuery.Data.__Schema.Type> {
        private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("name", "name", null, true, null)
        )

        override fun fromResponse(reader: ResponseReader, __typename: String?):
            TestQuery.Data.__Schema.Type {
          return reader.run {
            var name: String? = null
            while(true) {
              when (selectField(RESPONSE_FIELDS)) {
                0 -> name = readString(RESPONSE_FIELDS[0])
                else -> break
              }
            }
            TestQuery.Data.__Schema.Type(
              name = name
            )
          }
        }

        override fun toResponse(writer: ResponseWriter, value: TestQuery.Data.__Schema.Type) {
          writer.writeString(RESPONSE_FIELDS[0], value.name)
        }
      }
    }

    object __Type : ResponseAdapter<TestQuery.Data.__Type> {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
        ResponseField.forString("name", "name", null, true, null)
      )

      override fun fromResponse(reader: ResponseReader, __typename: String?):
          TestQuery.Data.__Type {
        return reader.run {
          var name: String? = null
          while(true) {
            when (selectField(RESPONSE_FIELDS)) {
              0 -> name = readString(RESPONSE_FIELDS[0])
              else -> break
            }
          }
          TestQuery.Data.__Type(
            name = name
          )
        }
      }

      override fun toResponse(writer: ResponseWriter, value: TestQuery.Data.__Type) {
        writer.writeString(RESPONSE_FIELDS[0], value.name)
      }
    }
  }
}
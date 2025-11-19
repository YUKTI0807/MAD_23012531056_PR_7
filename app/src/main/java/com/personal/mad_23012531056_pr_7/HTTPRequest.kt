package com.personal.mad_23012531056_pr_7

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL

class HTTPRequest {
    private val TAG = "HTTPRequest"
    fun makeServiceCall(reqURL: String?, token:String?=null): String? {
        var response: String? = null
        try {
            val url = URL(reqURL)
            val conn = url.openConnection() as HttpURLConnection
            if(token!=null){
                conn.setRequestProperty("Authorization", "Bearer $token")
                conn.setRequestProperty("Content-Type", "application/json")
            }
            conn.requestMethod = "GET"
            val responseCode = conn.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = convertStreamToString(conn.inputStream)
            } else {
                Log.e(TAG, "HTTP Error Response Code: $responseCode")
                val errorStream = conn.errorStream
                if (errorStream != null) {
                    val errorResponse = convertStreamToString(errorStream)
                    Log.e(TAG, "HTTP Error Response Body: $errorResponse")
                }
            }
        } catch (e: MalformedURLException) {
            Log.e(TAG, "MalformedURLException: " + e.message)
        } catch (e: ProtocolException) {
            Log.e(TAG, "ProtocolException: " + e.message)
        } catch (e: IOException) {
            Log.e(TAG, "IOException: " + e.message)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: " + e.message)
        }
        return response
    }

    private fun convertStreamToString(`is`: InputStream?): String {
        val reader = BufferedReader(InputStreamReader(`is`))

        val sb = StringBuilder()
        var line: String?

        try {

            while (reader.readLine().also { line = it } != null) {

                sb.append(line).append('\n')

            }

        } catch (e : IOException) {

            e.printStackTrace()

        } finally {

            try {

                `is`?.close()

            } catch (e : IOException) {

                e.printStackTrace()

            }

        }

        return sb.toString()

    }
}
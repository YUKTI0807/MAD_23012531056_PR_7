package com.personal.mad_23012531056_pr_7

import org.json.JSONObject
import java.io.Serializable

data class Person(
    var id: String,
    var name: String,
    var email: String,
    var phone: String,
    var address: String
): Serializable
{
    constructor(jsonObject: JSONObject) : this(
        "",
        "",
        "",
        "",
        ""
    ){
        id = jsonObject.optString("id", "")
        name = jsonObject.optString("name", "")

        // Try to get email, phone, and address from a nested 'profile' object
        if (jsonObject.has("profile")) {
            val profileJson = jsonObject.optJSONObject("profile")
            if (profileJson != null) {
                email = profileJson.optString("email", "")
                phone = profileJson.optString("phone", "")
                address = profileJson.optString("address", "")
            } else {
                // If 'profile' exists but is not a valid JSONObject (e.g., null or another type), fall back to top-level
                email = jsonObject.optString("email", "")
                phone = jsonObject.optString("phone", "")
                address = jsonObject.optString("address", "")
            }
        } else {
            // If no 'profile' object, try to get them from the top level
            email = jsonObject.optString("email", "")
            phone = jsonObject.optString("phone", "")
            address = jsonObject.optString("address", "")
        }
    }

    override fun toString(): String {
        return "Person(id='$id', name='$name', email='$email', phone='$phone', address='$address')"
    }
}
package com.personal.mad_23012531056_pr_7.db

class PersonDbTable {
    companion object{
        const val TABLE_NAME = "person"

        const val COLUMN_ID = "id"
        const val COLUMN_PERSON_NAME = "person_name"
        const val COLUMN_PERSON_EMAIL = "person_email_id"
        const val COLUMN_PERSON_PHONE = "person_phone_no"
        const val COLUMN_PERSON_ADDRESS = "person_address"

        val CREATE_TABLE = (
                    "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID TEXT PRIMARY KEY," +
                    "$COLUMN_PERSON_NAME TEXT," +
                    "$COLUMN_PERSON_EMAIL TEXT," +
                    "$COLUMN_PERSON_PHONE TEXT," +
                    "$COLUMN_PERSON_ADDRESS TEXT" +
                    ")"
                )
    }
}
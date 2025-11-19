package com.personal.mad_23012531056_pr_7.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.personal.mad_23012531056_pr_7.Person
import kotlin.arrayOf

class DatabaseHelper(
    context: Context?
): SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {
    companion object{
        val DATABASE_NAME = "PersonDB"
        val DB_VERSION = 1
    }
    override fun onCreate(db: SQLiteDatabase?){
        db?.execSQL(PersonDbTable.CREATE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        db?.execSQL("DROP TABLE IF EXISTS ${PersonDbTable.TABLE_NAME}")
        onCreate(db)
    }
    private fun getValues(person: Person): ContentValues {
        val values = ContentValues()

        values.put(PersonDbTable.COLUMN_ID, person.id)
        values.put(PersonDbTable.COLUMN_PERSON_NAME, person.name)
        values.put(PersonDbTable.COLUMN_PERSON_ADDRESS, person.address)
        values.put(PersonDbTable.COLUMN_PERSON_EMAIL, person.email)
        values.put(PersonDbTable.COLUMN_PERSON_PHONE, person.phone)
        return values
    }
    private fun getPerson(cursor: Cursor): Person {
        return Person(
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTable.COLUMN_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTable.COLUMN_PERSON_NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTable.COLUMN_PERSON_EMAIL)),
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTable.COLUMN_PERSON_PHONE)),
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTable.COLUMN_PERSON_ADDRESS))
        )
    }

    fun getPerson(id: String): Person? {
        val db = this.readableDatabase
        val cursor = db.query(
            PersonDbTable.TABLE_NAME,
            arrayOf(
                PersonDbTable.COLUMN_ID,
                PersonDbTable.COLUMN_PERSON_NAME,
                PersonDbTable.COLUMN_PERSON_EMAIL,
                PersonDbTable.COLUMN_PERSON_PHONE,
                PersonDbTable.COLUMN_PERSON_ADDRESS),
            "${PersonDbTable.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        ) ?: return null
        cursor.moveToFirst()
        if (cursor.count == 0) {
            return null
        }
        val person = getPerson(cursor)
        cursor.close()
        return person
    }
    val allPersons: ArrayList<Person>
        get() {
            val persons = ArrayList<Person>()

            val selectQuery = "SELECT * FROM ${PersonDbTable.TABLE_NAME} ORDER BY ${PersonDbTable.COLUMN_PERSON_NAME} DESC"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val person = getPerson(cursor)
                    persons.add(person)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return persons
        }
    val personsCount: Int
        get() {
            val countQuery = "SELECT * FROM ${PersonDbTable.TABLE_NAME}"
            val db = this.readableDatabase
            val cursor = db.rawQuery(countQuery, null)
            val count = cursor.count
            cursor.close()
            return count
        }
    fun updatePerson(person: Person): Int {
        val db = this.writableDatabase
        return db.update(
            PersonDbTable.TABLE_NAME,
            getValues(person),
            "${PersonDbTable.COLUMN_ID} = ?",
            arrayOf(person.id)
        )
    }
    fun deletePerson(person: Person) {
        val db = this.writableDatabase
        db.delete(
            PersonDbTable.TABLE_NAME,
            "${PersonDbTable.COLUMN_ID} = ?",
            arrayOf(person.id)
        )
        db.close()
    }
    fun insertPerson(person: Person): Long {
        val db = this.writableDatabase
        val initialValues = getValues(person)
        val id = db.insert(PersonDbTable.TABLE_NAME, null, initialValues)
        return id
    }
}

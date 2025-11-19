package com.personal.mad_23012531056_pr_7

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.personal.mad_23012531056_pr_7.db.DatabaseHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    val personList = ArrayList<Person>()
    lateinit var personAdapter: PersonAdapter
    lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        db = DatabaseHelper(applicationContext)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView1)
        personAdapter = PersonAdapter(this, personList, db)
        recyclerView.adapter = personAdapter
        getPersonDetailsFromJsonSqliteDb()
        findViewById<FloatingActionButton>(R.id.fab_sync).setOnClickListener{
            networkDB()
        }
    }

    private fun getPersonDetailsFromJsonSqliteDb(){
        val size = personList.size
        personList.clear()
        personAdapter.notifyItemRangeRemoved(0, size)
        try{
            personList.addAll(db.allPersons)
            personAdapter.notifyItemRangeInserted(0, personList.size)
        }catch (ee: JSONException){
            ee.printStackTrace()
        }
        Toast.makeText(applicationContext, "Synced from SQLite", Toast.LENGTH_SHORT).show()
    }

    fun deletePerson(position: Int){
        if (position >= 0 && position < personList.size) {
            val personToDelete = personList[position] // Get person object before removal
            db.deletePerson(personToDelete)
            personList.removeAt(position)
            personAdapter.notifyItemRemoved(position)
            Toast.makeText(applicationContext, "At $position, ${personToDelete.name} deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Error: Invalid position for deletion", Toast.LENGTH_SHORT).show()
        }
    }

    private fun networkDB(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = HTTPRequest().makeServiceCall(
                    "https://api.json-generator.com/templates/kvHxFgDYej7S/data",
                    "glyycxuoph770p3kybgnvuvsyyqk0yizez16mead"
                )
                withContext(Dispatchers.Main) {
                    try {
                        if (data != null) {
                            getPersonDetailsFromJson(data)
                            Toast.makeText(applicationContext, "Synced from Network", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "Failed to sync from Network: No data", Toast.LENGTH_LONG).show()
                        }
                    } catch (e : Exception) {
                        e.printStackTrace()
                        Toast.makeText(applicationContext, "Failed to sync from Network: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Failed to sync from Network: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getPersonDetailsFromJson(sJson: String){
        val size = personList.size
        personList.clear()
        personAdapter.notifyItemRangeRemoved(0, size)
        try {
            val jsonArray = JSONArray(sJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray[i] as JSONObject
                val person = Person(jsonObject)
                personList.add(person)
                try{
                    if(db.getPerson(person.id) != null){
                        db.updatePerson(person)
                    }else{
                        db.insertPerson(person)
                    }
                } catch (e: JSONException){
                    e.printStackTrace()
                }
            }
            personAdapter.notifyItemRangeInserted(0, personList.size)
        } catch (e: JSONException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Error parsing JSON: ${e.message}", Toast.LENGTH_LONG).show()
        }
        // This Toast is now moved to networkDB based on success/failure
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        return when(item.itemId){
            R.id.action_sqlitedb ->{
                getPersonDetailsFromJsonSqliteDb()
                true
            }
            R.id.action_nwdb ->{
                networkDB()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
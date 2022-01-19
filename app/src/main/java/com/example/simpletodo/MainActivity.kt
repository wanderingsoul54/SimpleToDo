    package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FileUtils.readLines
import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.IOUtils.readLines
import org.apache.commons.io.IOUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

    class MainActivity : AppCompatActivity() {

        var ListOfTasks = mutableListOf<String>()
        lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongCLickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove item from the list
                ListOfTasks.removeAt(position)
                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }
        // 1. Let's detect when the user clicks the button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            //code in here will be executed
//            Log.i("Caren", "User clicked on button")
//        }

        loadItems()

        // Lookup the recyclerView in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(ListOfTasks, onLongCLickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the button field, so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Get a reference to the button
        // and then set an onclickistener
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = findViewById<EditText>(R.id.addTaskField).text.toString()
            // 2. Add the string to list of tasks
            ListOfTasks.add(userInputtedTask)
            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(ListOfTasks.size - 1)
            // 3. Reset text field
            findViewById<EditText>(R.id.addTaskField).setText("")

            saveItems()

        }
    }

    // save the data the user has inputted
    // save data by writing and reading from a file

    // create a method to get file we need
        fun getDataFile() : File {

            //Every Line is going to represent a specific task in our list of tasks
            return File(filesDir, "data.txt")
        }

    // load the items by reading every line in the data file
        fun loadItems() {
            try {
                ListOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }

        }

    // save items by writing them into our data file
        fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), ListOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}
package com.example.todolist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseBooleanArray
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.ListView as ListView


class MainActivity : AppCompatActivity() {

    var listItems = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null){
            listItems = savedInstanceState.getStringArrayList("SavedList")!!
        }
        val addButton = findViewById<Button>(R.id.add_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)
        val toDoList = findViewById<ListView>(R.id.to_do_list_view)
        val addItemField = findViewById<EditText>(R.id.add_item_field)
        //val listItems = arrayListOf<String>()
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.select_dialog_multichoice, listItems)
        toDoList.adapter = arrayAdapter


        //add item
        addButton.setOnClickListener {
            val itemText: String = addItemField.text.toString()
            if (itemText.isNotEmpty()) {
                listItems.add(itemText)
                arrayAdapter.notifyDataSetChanged()
                addItemField.setText("")
                Toast.makeText(this, itemText + " added", Toast.LENGTH_SHORT).show()
            }  else {
                Toast.makeText(this, "Please, fill the gap", Toast.LENGTH_SHORT).show()
            }
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }

        //delete item
        deleteButton.setOnClickListener {
            val position: SparseBooleanArray = toDoList.checkedItemPositions
            val count = toDoList.count
            for(item in count - 1 downTo 0) {
                if(position.get(item)) {
                    arrayAdapter.remove(listItems.get(item))
                }
            }
            position.clear()
            arrayAdapter.notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
      //  var savedList = findViewById<ListView>(R.id.to_do_list_view)

        outState.putStringArrayList("SavedList", listItems)
    }

   /* override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        var taskArray = savedInstanceState.getString("SavedList")
        var savedList = findViewById<ListView>(R.id.to_do_list_view)

        listItems.add(taskArray.toString())
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.select_dialog_multichoice, listItems)
        savedList.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()

        Toast.makeText(this, ">>> " + taskArray + " <<<", Toast.LENGTH_SHORT).show()
    }*/
}
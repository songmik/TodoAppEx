package org.techtown.todoapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.text.SimpleDateFormat
import java.util.*

class AddTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        val date = Date()
        val sdForm= SimpleDateFormat("yyyy-MM-dd")
        addDateView.text=sdForm.format(date)

        addDateView.setOnClickListener{
            val c=Calender.gerInstance()
            val year = c.get(Calender.YEAR)
            val month=c.get(Calender.MONTH)
            val day=c.get(Calender.DAY_OF_MONTH)

            val dateDialog=DatePickerDialog(this,object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    addDateView.text="$year-${month+1}-${dayOfMonth}"
                }
            }, year, month, day).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId===R.id.menu_add){
            if(addTitleEditView.text.toString() != null && addContentEditView.text.toString() != null){
                val helper=DBHelper(this)
                val db=helper.writableDatabase

                val contentValues= ContentValues()
                contentValues.put("title", addTitleEditView.text.toString())
                contentValues.put("content",addContentEditView.text.toString())
                contentValues.put("date", addDateView.text.toString())
                contentValues.put("completed", 0)

                db.insert("tb_todo", null,contentValues)
                db.close()

                setResult(Activity.RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
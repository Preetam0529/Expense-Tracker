package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import java.util.Calendar

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var selectedDateTextView: TextView
    private lateinit var categorySpinner: Spinner
    private val categories = arrayOf("Food", "Transport", "Shopping", "Bills", "Other")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        dbHelper = ExpenseDatabaseHelper(this)
        val amountEditText = findViewById<EditText>(R.id.amountEditText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)
        selectedDateTextView = findViewById(R.id.selectedDateTextView)
        categorySpinner = findViewById(R.id.categorySpinner)
        val selectDateButton = findViewById<Button>(R.id.selectDateButton)
        val saveButton = findViewById<Button>(R.id.saveButton)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        selectDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear)
                    selectedDateTextView.text = formattedDate
                }, year, month, day)

            datePickerDialog.show()
        }

        saveButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDouble()
            val description = descriptionEditText.text.toString()
            val date = selectedDateTextView.text.toString()
            val category = categorySpinner.selectedItem.toString()

            if (amount != null && date.isNotEmpty() && category.isNotEmpty()){
                val expense = Expense(amount = amount, description = description, date = date, category = category)
                dbHelper.insertExpense(expense)

                Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                selectedDateTextView.error = "Please select a valid date"
            }
        }
    }
}

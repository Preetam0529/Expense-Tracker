package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var chart: PieChart
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = ExpenseDatabaseHelper(this)
        recyclerView = findViewById(R.id.expenseRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadExpenses()
        val fab = findViewById<FloatingActionButton>(R.id.addExpenseFab)
        fab.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadExpenses()
        displayChart()
    }

    private fun loadExpenses() {
        val expenses = dbHelper.getAllExpenses()
        expenseAdapter = ExpenseAdapter(expenses, dbHelper, this){displayChart()}
        recyclerView.adapter = expenseAdapter
    }

    private fun displayChart() {
        val expenses = dbHelper.getAllExpenses()
        val categoryMap = mutableMapOf<String, Double>()

        for (expense in expenses) {
            categoryMap[expense.category] = categoryMap.getOrDefault(expense.category, 0.0) + expense.amount
        }

        val entries = ArrayList<PieEntry>()
        for ((category, amount) in categoryMap) {
            entries.add(PieEntry(amount.toFloat(), category))
        }

        val dataSet = PieDataSet(entries, "Expenses by Category")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = PieData(dataSet)
        chart = findViewById(R.id.expenseChart)
        chart.data = data
        chart.invalidate()
    }
}

package com.example.expensetracker

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private var expenses: List<Expense>,
    private val dbHelper: ExpenseDatabaseHelper,
    private val context: Context,
    private val onExpenseDeleted:()->Unit
):RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amountTextView = itemView.findViewById<TextView>(R.id.amountTextView)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)
        val dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)
        val categoryTextView = itemView.findViewById<TextView>(R.id.categoryTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]

        holder.amountTextView.text = "Amount: ₹${expense.amount}"
        holder.descriptionTextView.text = "Description: ${expense.description}"
        holder.dateTextView.text = "Date: ${expense.date}"
        holder.categoryTextView.text = "Category: ${expense.category}"

        holder.itemView.setOnLongClickListener {
            showDeleteDialog(expense)
            true
        }
    }

    override fun getItemCount():Int = expenses.size

    private fun showDeleteDialog(expense: Expense) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Expense")
        builder.setMessage("Are you sure you want to delete this expense?")
        builder.setPositiveButton("Yes") { _, _ ->
            dbHelper.deleteExpense(expense.id)
            expenses = dbHelper.getAllExpenses()
            notifyDataSetChanged()
            onExpenseDeleted()
            Toast.makeText(context, "Expense Deleted", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}

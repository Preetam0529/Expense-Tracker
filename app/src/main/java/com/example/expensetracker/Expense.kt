package com.example.expensetracker

data class Expense(
    val id: Int = 0,
    val amount: Double,
    val description: String,
    val date: String,
    val category: String
)

package com.example.expensetracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "expenses.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "expenses"
        private const val COLUMN_ID = "id"
        private const val COLUMN_AMOUNT = "amount"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_AMOUNT REAL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_DATE TEXT,
                $COLUMN_CATEGORY TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertExpense(expense: Expense): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_DESCRIPTION, expense.description)
            put(COLUMN_DATE, expense.date)
            put(COLUMN_CATEGORY, expense.category)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllExpenses(): List<Expense> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val expenses = mutableListOf<Expense>()
        if (cursor.moveToFirst()) {
            do {
                val expense = Expense(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                )
                expenses.add(expense)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return expenses
    }

    fun deleteExpense(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}

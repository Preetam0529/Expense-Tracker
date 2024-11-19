# Expense Tracker
The ***Expense Tracker*** app is a simple and efficient tool for managing personal finances. Users can add, view, and delete expenses while categorizing them for better organization. The app uses SQLite for offline data storage, a RecyclerView for displaying a scrollable list of expenses, and a dynamic Pie Chart for visualizing spending patterns by category. It features a clean, user-friendly interface with a date picker and dropdown for easy input. With its modular design, the app is scalable for future enhancements like budget tracking, cloud sync, and advanced analytics.

## Overview

This ***Expense Tracker*** App is a comprehensive solution designed to help users manage their personal finances efficiently. The app enables users to track their daily expenses, categorize them, and visualize spending patterns through intuitive chart representations. This project leverages Android app development best practices and core components to deliver a seamless user experience while incorporating a lightweight and reliable SQLite database for persistent data storage.

The app addresses the growing need for financial awareness and budgeting tools by offering a simple yet effective interface that is easy to navigate. It features functionalities such as adding, viewing, and categorizing expenses, coupled with a visual representation of spending trends through a pie chart. By providing these capabilities, the app empowers users to make informed financial decisions.  

## Features

**1. Expense Management:**
- Add expenses with details like amount, description, date (via DatePicker), and category (via dropdown).
- Delete expenses with a long-press and confirmation dialog.
  
**2. Expense List:**
- View all expenses in a scrollable, categorized list using RecyclerView.
  
**3. Chart Visualization:**
- Real-time Pie Chart displays expenses by category.
- Instantly updates after adding or deleting expenses.
  
**4. Local Data Storage:**
- SQLite integration for offline data persistence.
  
**5. User-Friendly Interface:**
- Clean design with intuitive navigation and input validations.

## Activity Flow
**1.	App Launch:**
-	The app launches and opens MainActivity.
-	MainActivity fetches all expense data from the database and populates the RecyclerView and the Pie Chart.
  
**2.	Adding a New Expense:**
- The user clicks the FloatingActionButton in MainActivity, which opens AddExpenseActivity.
- In AddExpenseActivity, the user fills in the details of the expense and clicks Save.
- The expense is saved to the SQLite database, and the app navigates back to MainActivity.

**3.	Viewing Expenses:**
-	The user views the list of expenses in the RecyclerView.
-	The user also sees a Pie Chart summarizing expenses by category.

**4.	Deleting an Expense:**
-	The user long-presses on a list item in the RecyclerView.
-	A confirmation dialog appears, asking the user to confirm the deletion.
-	On confirmation, the expense is deleted from the SQLite database, and the list and the chart are refreshed.

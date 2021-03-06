package com.example.ace.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ace.todolist.domain.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoListManager
{
    private DatabaseHelper dbHelper;

    public ToDoListManager(Context context)
    {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public List<ToDoItem> getItems()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_NAME,
                null
        );

        List<ToDoItem> items = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                ToDoItem item = new ToDoItem(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COMPLETED)) != 0,
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelper.ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.TIME_STAMP))
                );
                items.add(item);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return items;
    }

    public void addItem(ToDoItem item)
    {
        ContentValues newItem = new ContentValues();
        newItem.put(DatabaseHelper.DESCRIPTION, item.getDescription());
        newItem.put(DatabaseHelper.COMPLETED, item.isComplete());
        newItem.put(DatabaseHelper.TIME_STAMP, item.getTiming());

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.insert(DatabaseHelper.TABLE_NAME, null, newItem);
    }

    public void updateItem(ToDoItem item)
    {
        // UPDATE items
        // Set Description = "", Completed = ""
        // Where _id = ""

        ContentValues updateItem = new ContentValues();
        updateItem.put(DatabaseHelper.DESCRIPTION, item.getDescription());
        updateItem.put(DatabaseHelper.COMPLETED, item.isComplete());
        updateItem.put(DatabaseHelper.TIME_STAMP, item.getTiming());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = new String[]
                {
                        String.valueOf(item.getId())
                };

        db.update(
                DatabaseHelper.TABLE_NAME,
                updateItem,
                DatabaseHelper.ID + "=?",
                args
        );
    }

    public void deleteItem(ToDoItem item)
    {
        ContentValues deleteItem = new ContentValues();
        deleteItem.remove(DatabaseHelper.DESCRIPTION);
        deleteItem.remove(DatabaseHelper.COMPLETED);
        deleteItem.remove(DatabaseHelper.ID);
        deleteItem.remove(DatabaseHelper.TIME_STAMP);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = new String[]
                {
                      String.valueOf(item.getId())
                };

        db.delete(
                DatabaseHelper.TABLE_NAME,
                DatabaseHelper.ID + "=?",
                args
        );
    }
}

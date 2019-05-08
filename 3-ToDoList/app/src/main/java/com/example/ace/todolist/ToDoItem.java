package com.example.ace.todolist;

public class ToDoItem {
    private String description;
    private boolean isComplete;
    private long id;
    private String time;


    public ToDoItem(String description, boolean isComplete, String time)
    {
        this(description, isComplete, -1, time);
    }

    public ToDoItem(String description, boolean isComplete, long id, String time)
    {
        this.description = description;
        this.isComplete = isComplete;
        this.id = id;
        this.time = time;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isComplete()
    {
        return isComplete;
    }

    public void toggleComplete()
    {
        isComplete = !isComplete;
    }

    public long getId()
    {
        return id;
    }

    public String getTiming()
    {
        return time;
    }

    @Override
    public String toString()
    {
        return getDescription();
    }
}

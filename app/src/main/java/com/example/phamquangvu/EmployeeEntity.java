package com.example.phamquangvu;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Employee")
public class EmployeeEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "designation")
    public String designation;

    @ColumnInfo(name = "salary")
    public String salary;
}

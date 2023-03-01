package com.example.phamquangvu;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Insert
    long insertEmployee(EmployeeEntity employee);

    @Delete
    int deleteEmployee(EmployeeEntity employee);

    @Update
    int updateUser(EmployeeEntity employee);

    @Query("Select * from Employee where id =:id")
    EmployeeEntity findEmployee(int id);

    @Query("Select * from Employee")
    List<EmployeeEntity> getAllEmployee();
}

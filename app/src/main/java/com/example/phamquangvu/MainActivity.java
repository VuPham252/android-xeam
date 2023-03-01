package com.example.phamquangvu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvEmployee;
    AppDatabase db;

    EditText edName, edDes, edSalary;

    Button btAdd, btUpdate, btDelete;

    EmployeeAdapter adapter;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVIew();

        db = AppDatabase.getAppDatabase(this);
        List<EmployeeEntity> list = db.employeeDao().getAllEmployee();

        adapter = new EmployeeAdapter(this,this, list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvEmployee = findViewById(R.id.rvEmployee);
        rvEmployee.setLayoutManager(layoutManager);
        rvEmployee.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }

        EmployeeEntity employee = (EmployeeEntity) bundle.get("employee");
        edName.setText(employee.name.toString());
        edDes.setText(employee.designation.toString());
        edSalary.setText(employee.salary.toString());
        id = employee.id;
    }

    private void initVIew() {
        edName = findViewById(R.id.edName);
        edDes = findViewById(R.id.edDes);
        edSalary = findViewById(R.id.edSalary);
        btAdd = findViewById(R.id.btAdd);
        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btDelete);
        btAdd.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btDelete.setOnClickListener(this);
    }

    private boolean validate() {
        String mes = null;
        if(edName.getText().toString().trim().isEmpty()){
            mes = "Employee name can not blank";
        }else if (edDes.getText().toString().trim().isEmpty()) {
            mes = "Designation can not blank";
        }else if (edSalary.getText().toString().trim().isEmpty()) {
            mes = "Salary can not blank";
        }
        if(mes != null) {
            Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void onAddEmployee() {
        if(!validate()) {
            return;
        }
        EmployeeEntity employee = new EmployeeEntity();
        employee.name = edName.getText().toString();
        employee.designation = edDes.getText().toString();
        employee.salary = edSalary.getText().toString();
        long id = db.employeeDao().insertEmployee(employee);
        if(id > 0) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        List<EmployeeEntity> list = db.employeeDao().getAllEmployee();
        Log.d("TAG", "name " + list.get(0).name );
        adapter.reloadData(list);
    }

    private void onUpdateEmployee() {
        EmployeeEntity employee = db.employeeDao().findEmployee(id);
        employee.name = edName.getText().toString();
        employee.designation = edDes.getText().toString();
        employee.salary = edSalary.getText().toString();
        if(employee != null){
            db.employeeDao().updateUser(employee);
            Toast.makeText(this, "Update employee successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
    }

    private void onDeleteEmployee() {
        EmployeeEntity employee =  db.employeeDao().findEmployee(id);
        if(employee != null){
            db.employeeDao().deleteEmployee(employee);
            List<EmployeeEntity> list = db.employeeDao().getAllEmployee();
            adapter.reloadData(list);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btAdd:
                onAddEmployee();
                break;
            case R.id.btDelete:
                onDeleteEmployee();
                break;
            case R.id.btUpdate:
                onUpdateEmployee();
                break;
            default:
                break;
        }
    }
}
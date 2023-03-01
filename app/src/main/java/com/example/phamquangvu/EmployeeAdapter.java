package com.example.phamquangvu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter {

    Activity activity;
    List<EmployeeEntity> listEmployee;

    Context context;

    public EmployeeAdapter(Context context, Activity activity, List<EmployeeEntity> listEmployee) {
        this.activity = activity;
        this.listEmployee = listEmployee;
        this.context = context;
    }

    public void reloadData(List<EmployeeEntity> listEmployee) {
        this.listEmployee = listEmployee;
        this.notifyDataSetChanged();
    }

    private void onSelectEmployee(EmployeeEntity employee) {
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("employee", employee);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.item_employee, parent, false);
        EmployeeHolder holder = new EmployeeHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EmployeeHolder vh = (EmployeeHolder) holder;
        EmployeeEntity model = listEmployee.get(position);
        vh.tvName.setText(model.name);
        vh.tvDes.setText(model.designation);
        vh.tvSalary.setText(model.salary);

        ((EmployeeHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectEmployee(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEmployee.size();
    }

    public class EmployeeHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDes, tvSalary;
        LinearLayout linearLayout;

        public EmployeeHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.item);
            tvName = itemView.findViewById(R.id.tvName);
            tvDes = itemView.findViewById(R.id.tvDes);
            tvSalary = itemView.findViewById(R.id.tvSalary);
        }
    }
}

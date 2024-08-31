package com.example.wastepickerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastepickerapp.models.PickupSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{
    private List<PickupSchedule> pickupSchedules;
    public ScheduleAdapter(List<PickupSchedule> pickupSchedules) {
        this.pickupSchedules = pickupSchedules;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        PickupSchedule pickupSchedule = pickupSchedules.get(position);
        String formattedDate = formatDate(pickupSchedule.getDate());

        holder.idTextView.setText(String.valueOf(pickupSchedule.getId()));
        holder.arrivalTimeTextView.setText(pickupSchedule.getArrivalTime());
        holder.areaIdTextView.setText(String.valueOf(pickupSchedule.getAreaId()));
        holder.dateTextView.setText(formattedDate);
    }
    private String formatDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm");
        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    @Override
    public int getItemCount() {
        return pickupSchedules.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, arrivalTimeTextView, areaIdTextView,dateTextView;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.schedule_id);
            arrivalTimeTextView = itemView.findViewById(R.id.arrival_time);
            areaIdTextView = itemView.findViewById(R.id.area_id);
            dateTextView = itemView.findViewById(R.id.schedule_date);
        }
    }
}

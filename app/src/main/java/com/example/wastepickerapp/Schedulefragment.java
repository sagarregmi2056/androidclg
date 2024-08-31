package com.example.wastepickerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wastepickerapp.models.PickupSchedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Schedulefragment extends Fragment {

    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;
    private List<PickupSchedule> pickupScheduleList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_schedulefragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the pickup schedule list
        pickupScheduleList = new ArrayList<>();

        // Example JSON response (replace this with actual response)
        String jsonResponse = "{ \"title\": \"success\", \"data\": { \"schedules\": [ { \"id\": 1, \"date\": \"2024-08-21T18:15:00.000000Z\", \"arrival_time\": \"11:52:00\", \"area_id\": 1 }, { \"id\": 6, \"date\": \"2024-08-16T18:15:00.000000Z\", \"arrival_time\": \"00:00:00\", \"area_id\": 2 }, { \"id\": 7, \"date\": \"2024-08-15T18:15:00.000000Z\", \"arrival_time\": \"10:00:00\", \"area_id\": 2 } ] } }";

        // Parse JSON and add to list
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray schedulesArray = jsonObject.getJSONObject("data").getJSONArray("schedules");

            for (int i = 0; i < schedulesArray.length(); i++) {
                JSONObject scheduleObject = schedulesArray.getJSONObject(i);
                int id = scheduleObject.getInt("id");
                String date = scheduleObject.getString("date");
                String arrivalTime = scheduleObject.getString("arrival_time");
                int areaId = scheduleObject.getInt("area_id");

                PickupSchedule pickupSchedule = new PickupSchedule(id, date, arrivalTime, areaId);
                pickupScheduleList.add(pickupSchedule);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            // If there's an error in parsing, log it or show a message
            Toast.makeText(getContext(), "Error parsing schedule data", Toast.LENGTH_SHORT).show();
        }

        // Set up adapter
        scheduleAdapter = new ScheduleAdapter(pickupScheduleList);
        recyclerView.setAdapter(scheduleAdapter);
    }
}
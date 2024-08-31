package com.example.wastepickerapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


public class Discoveryfragment extends Fragment {


     VideoView videoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discoveryfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize VideoView
        videoView = view.findViewById(R.id.video);

       // String videoUrl = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video;
  String videoUrl = "https://drive.google.com/uc?export=download&id=1TeKDXVdcknh0U0bQ9Kz_sW7JoCvVjZu3";
        // Set the video URL
//        String videoUrl = "android.resource://"+ getActivity().getPackageName()+"/"+R.raw.video; // Replace with your video URL


        // Add media controller
        MediaController mediaController = new MediaController(getContext());

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(Uri.parse(videoUrl));

        // Start the video
        videoView.start();

        // Handle video playback errors
        videoView.setOnErrorListener((mp, what, extra) -> {
            // Handle the error here
            // For example, show a Toast or a dialog
            Toast.makeText(getContext(), "Error playing video", Toast.LENGTH_SHORT).show();
            return true; // Return true if the error is handled
        });
    }
}



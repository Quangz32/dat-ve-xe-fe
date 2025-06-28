package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.datvexe.databinding.FragmentChatWithAdminBinding;


public class ChatWithAdminFragment extends Fragment {
    private FragmentChatWithAdminBinding binding;

    public ChatWithAdminFragment() {
        // Required empty public constructor
    }


    public static ChatWithAdminFragment newInstance() {
        return new ChatWithAdminFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatWithAdminBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}
package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
import com.example.datvexe.databinding.FragmentNotificationBinding;
import com.example.datvexe.domain.model.Notification;
import com.example.datvexe.presentation.adapter.NotificationAdapter;
import com.example.datvexe.presentation.viewmodel.NotificationViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationFragment extends Fragment {
    // Tạm thời hardcode userId - trong thực tế sẽ lấy từ SharedPreferences hoặc Session
    private static final String USER_ID = "67a5a8bc040810b61bb8e672";

    FragmentNotificationBinding view;
    TabLayout tabLayout;
    Toolbar toolbar;

    RecyclerView recyclerView;
    NotificationAdapter promotionAdapter;
    NotificationAdapter eventAdapter;

    NotificationViewModel viewModel;


    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = FragmentNotificationBinding.inflate(inflater, container, false);

        setupTabLayout();

        // Khởi tạo Toolbar
        toolbar = view.toolbar;
        ((AppCompatActivity) Objects.requireNonNull(getActivity()))
                .setSupportActionBar(toolbar);
        toolbar.setTitle("Thông báo");

        // Khởi tạo ViewModel
        viewModel.loadNotifications(USER_ID);
        viewModel.notifications.observe(this, notifications -> {
            promotionAdapter.setNotifications(findNotifications(notifications, "promotion"));
            eventAdapter.setNotifications(findNotifications(
                    notifications, Notification.NotificationTab.EVENTS.getValue()));

            Log.d("event", eventAdapter.getNotifications().toString());
        });

        //khởi tạo cho RecyclerView
        recyclerView = view.rvNotification;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        promotionAdapter = new NotificationAdapter();
        eventAdapter = new NotificationAdapter();

        return view.getRoot();
    }

    void setupTabLayout() {
        tabLayout = view.tabLayout;
        tabLayout.addTab(tabLayout.newTab().setText("Khuyến Mãi"));
        tabLayout.addTab(tabLayout.newTab().setText("Sự kiện"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    recyclerView.setAdapter(promotionAdapter);
                } else {
                    recyclerView.setAdapter(eventAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private List<Notification> findNotifications(List<Notification> notifications, String tab) {
        return notifications.stream()
                .filter(notification -> notification.getTab().equals(tab))
                .toList();
    }
}
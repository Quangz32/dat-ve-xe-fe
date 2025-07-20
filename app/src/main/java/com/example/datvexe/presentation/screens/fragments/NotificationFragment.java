package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.data.local.SharedPreferencesManager;
import com.example.datvexe.databinding.FragmentNotificationBinding;
import com.example.datvexe.domain.model.Notification;
import com.example.datvexe.presentation.adapter.DiscountAdapter;
import com.example.datvexe.presentation.adapter.NotificationAdapter;
import com.example.datvexe.presentation.viewmodel.BookingViewModel;
import com.example.datvexe.presentation.viewmodel.DiscountViewModel;
import com.example.datvexe.presentation.viewmodel.NotificationViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationFragment extends Fragment {


    FragmentNotificationBinding view;
    TabLayout tabLayout;

    RecyclerView recyclerView;
    NotificationAdapter promotionAdapter;
    NotificationAdapter eventAdapter;
    private DiscountAdapter discountAdapter;
    private BookingViewModel bookingViewModel;
    private DiscountViewModel discountViewModel;

    NotificationViewModel viewModel;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

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
        bookingViewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = FragmentNotificationBinding.inflate(inflater, container, false);

        // Khởi tạo adapter trước
        promotionAdapter = new NotificationAdapter();
        eventAdapter = new NotificationAdapter();
        discountAdapter = new DiscountAdapter();

        // Setup RecyclerView
        recyclerView = view.rvNotification;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(discountAdapter); // Set discountAdapter mặc định

        // Load discounts ngay lập tức
        discountViewModel.loadDiscounts();

        // Observe discounts
        discountViewModel.discounts.observe(getViewLifecycleOwner(), discounts -> {
            Log.d("Discounts", "Received discounts: " + (discounts != null ? discounts.size() : "null"));
            discountAdapter.setDiscounts(discounts);
        });

        discountViewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Log.e("Discounts", "Error loading discounts: " + error);
            }
        });

        // Setup TabLayout
        setupTabLayout();

        // Load notifications
        viewModel.loadNotifications(sharedPreferencesManager.getUserId());
        viewModel.notifications.observe(getViewLifecycleOwner(), notifications -> {
            promotionAdapter.setNotifications(findNotifications(notifications, "promotion"));
            eventAdapter.setNotifications(findNotifications(
                    notifications, Notification.NotificationTab.EVENTS.getValue()));
        });

        return view.getRoot();
    }

    void setupTabLayout() {
        tabLayout = view.tabLayout;
        tabLayout.addTab(tabLayout.newTab().setText("Khuyến Mãi"));
        tabLayout.addTab(tabLayout.newTab().setText("Sự kiện"));
        
        // Select first tab by default
        tabLayout.selectTab(tabLayout.getTabAt(0));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    recyclerView.setAdapter(discountAdapter);
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
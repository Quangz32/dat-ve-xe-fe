package com.example.datvexe.presentation.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.datvexe.presentation.screens.fragments.AccountFragment;
import com.example.datvexe.presentation.screens.fragments.BookingFragment;
import com.example.datvexe.presentation.screens.fragments.HomeFragment;
import com.example.datvexe.presentation.screens.fragments.NotificationFragment;

public class MainPagerAdapter extends FragmentStateAdapter {
    public MainPagerAdapter(FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment(); // Home
            case 1:
                return new BookingFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

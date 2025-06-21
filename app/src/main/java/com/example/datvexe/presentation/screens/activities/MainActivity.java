package com.example.datvexe.presentation.screens.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.datvexe.R;
import com.example.datvexe.databinding.ActivityMainBinding;
import com.example.datvexe.presentation.adapter.MainPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding viewBinding;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = viewBinding.pager;
        tabLayout = viewBinding.tabLayout;

        MainPagerAdapter adapter = new MainPagerAdapter(this);
        viewPager.setAdapter(adapter);

        int[] tabIcons = {
                R.drawable.baseline_home_24,
                R.drawable.baseline_menu_book_24,
                R.drawable.baseline_notifications_24,
                R.drawable.baseline_person_pin_24
        };
        String[] tabTitles = {
                "Trang chủ",
                "Booking",
                "Thông báo",
                "Tài khoản"
        };

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setIcon(tabIcons[position]);
            tab.setText(tabTitles[position]);
        }).attach();
    }
}

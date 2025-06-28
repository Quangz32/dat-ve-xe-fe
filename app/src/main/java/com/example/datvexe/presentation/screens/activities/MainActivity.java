package com.example.datvexe.presentation.screens.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.datvexe.R;
import com.example.datvexe.databinding.ActivityMainBinding;
import com.example.datvexe.presentation.adapter.MainPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding viewBinding;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

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
        toolbar = viewBinding.toolbar;

        setupTabLayout();
        setupToolbar();


    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("DatVeXe");
//        toolbar.setBackgroundColor(R.color.white);
        toolbar.setVisibility(View.GONE);
    }

    private void setupTabLayout() {
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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar == null) return;

                if (tab.getPosition() == 0) {
                    actionBar.hide();
                } else if (tab.getPosition() == 1) {
                    actionBar.show();
                    actionBar.setTitle("Booking");
                } else if (tab.getPosition() == 2) {
                    actionBar.show();
                    actionBar.setTitle("Thông báo");
                } else if (tab.getPosition() == 3){
                    actionBar.show();
                    actionBar.setTitle("Tài khoản");
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

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        Objects.requireNonNull(getActionBar()).hide();
        return super.onCreateView(parent, name, context, attrs);
    }
}

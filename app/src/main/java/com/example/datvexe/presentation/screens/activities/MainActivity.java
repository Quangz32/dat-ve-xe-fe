package com.example.datvexe.presentation.screens.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.datvexe.R;
import com.example.datvexe.databinding.ActivityMainBinding;
import com.example.datvexe.presentation.adapter.MainPagerAdapter;
import com.example.datvexe.presentation.viewmodel.MainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding viewBinding;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private FrameLayout frameLayout;

    private MainViewModel viewModel;
    private List<TabNavigateBackCallback> tabNavigateCallbacks;

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
        frameLayout = viewBinding.frameLayout;

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupTabLayout();
        setupToolbar();
        observeViewModel();

        tabNavigateCallbacks = new ArrayList<>(Collections.nCopies(4, null));

        //Bấm back ở Tab nào sẽ gọi tới Callback của tab đó
        toolbar.setNavigationOnClickListener(v -> {
            TabNavigateBackCallback callback = tabNavigateCallbacks.get(viewModel.getCurrentTabIndex());
            if (callback != null) {
                callback.onNavigateBack();
            }
        });
    }

    private void observeViewModel() {
        viewModel.showNavigateBack.observe(this, booleans -> {
            doShowOrHideNavigateBack(viewModel
                    .getShowNavigateBack(tabLayout.getSelectedTabPosition()));
        });

        viewModel.tabTitles.observe(this, strings -> {
            doChangeActionBarTitle(viewModel.getTabTitle(tabLayout.getSelectedTabPosition()));
        });

        viewModel.currentTabIndex.observe(this,
                tabIndex -> {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar == null) return;

                    if (tabIndex == 0) {
                        actionBar.hide();
                    } else {
                        actionBar.show();
                        actionBar.setTitle(viewModel.getTabTitle(tabIndex));
                        doShowOrHideNavigateBack(
                                viewModel.getShowNavigateBack(tabIndex));
                    }
                });
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
                viewModel.setCurrentTabIndex(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void doShowOrHideNavigateBack(boolean show) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(show);
    }

    public void doChangeActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    public void setShowOrHideNavigateBack(int tabIndex, boolean show) {
        viewModel.setShowNavigateBack(tabIndex, show);
    }

    public void setActionBarTitle(int tabIndex, String title) {
        viewModel.setTabTitles(tabIndex, title);
    }

    public int getCurrentTabIndex() {
        return viewModel.getCurrentTabIndex();
    }

    public void setTabNavigateBackCallback(int tabIndex, TabNavigateBackCallback callback) {
        tabNavigateCallbacks.set(tabIndex, callback);
    }

    public void navigateToMain() {
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }

    public void navigateToFragment(Fragment fragment) {
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);

        frameLayout.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    public interface TabNavigateBackCallback {
        void onNavigateBack();
    }
}

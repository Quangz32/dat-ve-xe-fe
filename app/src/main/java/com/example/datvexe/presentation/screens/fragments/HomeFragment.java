package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Rect;

import com.example.datvexe.databinding.FragmentHomeBinding;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.HomeViewModel;
import com.example.datvexe.presentation.adapter.LocationAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding viewBinding;
    private HomeViewModel homeViewModel;
    private LocationAdapter locationAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerView();
        setupObservers();
        setupClickListeners();
        setupSearchBox();

        homeViewModel.fetchPopularRoutes();
        return viewBinding.getRoot();
    }

    private void setupRecyclerView() {
        locationAdapter = new LocationAdapter();
        // Đổi sang GridLayoutManager 2 cột
        viewBinding.rvPopularRoutes.setLayoutManager(
            new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false)
        );
        // Thêm ItemDecoration căn đều
        int spacing = 32; // px, tăng spacing cho rõ ràng
        int spanCount = 2;
        viewBinding.rvPopularRoutes.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int column = position % spanCount;
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;
                outRect.top = spacing / 2;
                outRect.bottom = spacing / 2;
            }
        });
        viewBinding.rvPopularRoutes.setAdapter(locationAdapter);
        locationAdapter.setOnStationClickListener(stationName -> {
            // TODO: Xử lý khi click vào tuyến phổ biến
        });
    }

    private void setupObservers() {
        homeViewModel.getPopularRoutes().observe(getViewLifecycleOwner(), locations -> {
            locationAdapter.setLocations(locations);
        });
        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            viewBinding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }

    private void setupClickListeners() {
        viewBinding.btnSearchTrip.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) requireActivity();
            activity.navigateToFragment(new LocationTripFragment());
        });
    }

    private void setupSearchBox() {
        viewBinding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // TODO: Gửi query tìm kiếm sang ViewModel nếu cần
                return true;
            }
            return false;
        });
        viewBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO: Gửi query tìm kiếm sang ViewModel nếu cần
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }
}

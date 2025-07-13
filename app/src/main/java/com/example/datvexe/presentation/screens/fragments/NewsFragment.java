package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datvexe.databinding.FragmentNewsBinding;
import com.example.datvexe.domain.model.News;
import com.example.datvexe.presentation.adapter.NewsAdapter;
import com.example.datvexe.R;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        setupRecyclerView();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        List<News> newsList = new ArrayList<>();
        newsList.add(new News(
                R.drawable.news1, // ảnh local
                "Khuyến mãi hè 2024: Giảm giá 30% tất cả các tuyến",
                "Đặt vé ngay hôm nay để nhận ưu đãi lớn nhất mùa hè! Áp dụng cho tất cả các tuyến đường.",
                "12/06/2024"
        ));
        newsList.add(new News(
                R.drawable.news2, // ảnh local
                "Khai trương tuyến mới Hà Nội - Quảng Ninh",
                "Nhà xe Sao Việt chính thức khai trương tuyến Hà Nội - Quảng Ninh với nhiều ưu đãi hấp dẫn.",
                "10/06/2024"
        ));
        newsList.add(new News(
                R.drawable.news3, // ảnh local
                "Tăng cường xe dịp lễ 2/9",
                "Đáp ứng nhu cầu đi lại tăng cao, nhà xe tăng cường thêm nhiều chuyến dịp lễ.",
                "05/06/2024"
        ));
        adapter = new NewsAdapter(newsList);
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvNews.setAdapter(adapter);
    }
} 
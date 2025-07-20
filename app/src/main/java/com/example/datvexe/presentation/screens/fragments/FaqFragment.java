package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.datvexe.R;
import com.example.datvexe.presentation.adapter.FaqAdapter;
import com.example.datvexe.presentation.viewmodel.FaqViewModel;

public class FaqFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (requireActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Câu hỏi thường gặp");
        }
        // Đã xóa Toolbar riêng, không thao tác với Toolbar ở đây nữa
        RecyclerView rvFaq = view.findViewById(R.id.rv_faq);
        rvFaq.setLayoutManager(new LinearLayoutManager(getContext()));
        FaqViewModel viewModel = new ViewModelProvider(this).get(FaqViewModel.class);
        viewModel.getFaqList().observe(getViewLifecycleOwner(), faqList -> {
            rvFaq.setAdapter(new FaqAdapter(faqList));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Không cần hiện lại ActionBar nữa, AccountFragment sẽ tự setTitle
    }
} 
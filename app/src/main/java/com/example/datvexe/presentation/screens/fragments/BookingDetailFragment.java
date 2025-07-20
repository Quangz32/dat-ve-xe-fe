package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.widget.Toolbar;
import com.example.datvexe.R;
import com.example.datvexe.presentation.viewmodel.BookingDetailViewModel;
import com.example.datvexe.domain.model.BookingDetail;

public class BookingDetailFragment extends Fragment {
    private BookingDetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ẩn Toolbar Booking và tab bar khi vào màn chi tiết vé
        requireActivity().findViewById(com.example.datvexe.R.id.toolbar).setVisibility(android.view.View.GONE);
        requireActivity().findViewById(com.example.datvexe.R.id.tab_layout).setVisibility(android.view.View.GONE);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        viewModel = new ViewModelProvider(this).get(BookingDetailViewModel.class);

        String bookingCode = null;
        if (getArguments() != null) {
            bookingCode = getArguments().getString("booking_code");
        }
        if (bookingCode != null) {
            viewModel.loadBookingDetail(bookingCode);
        }

        TextView tvCode = view.findViewById(R.id.tvCode);
        TextView tvRoute = view.findViewById(R.id.tvRoute);
        TextView tvCustomerName = view.findViewById(R.id.tvCustomerName);
        TextView tvSeat = view.findViewById(R.id.tvSeat);
        TextView tvDepartureTime = view.findViewById(R.id.tvDepartureTime);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvStatus = view.findViewById(R.id.tvStatus);
        TextView tvPaymentMethod = view.findViewById(R.id.tvPaymentMethod);

        viewModel.getBookingDetail().observe(getViewLifecycleOwner(), detail -> {
            if (detail != null) {
                tvCode.setText("Mã đặt: " + detail.getCode());
                tvRoute.setText(detail.getRoute());
                tvCustomerName.setText("Khách hàng: " + detail.getCustomerName());
                tvSeat.setText("Ghế: " + detail.getSeat());
                tvDepartureTime.setText("Khởi hành: " + detail.getDepartureTime());
                tvPrice.setText("Giá: " + detail.getPrice());
                tvStatus.setText("Trạng thái: " + detail.getStatus());
                tvPaymentMethod.setText("Thanh toán: " + detail.getPaymentMethod());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hiện lại Toolbar Booking và tab bar khi back
        requireActivity().findViewById(com.example.datvexe.R.id.toolbar).setVisibility(android.view.View.VISIBLE);
        requireActivity().findViewById(com.example.datvexe.R.id.tab_layout).setVisibility(android.view.View.VISIBLE);
        requireActivity().findViewById(com.example.datvexe.R.id.frame_layout).setVisibility(android.view.View.GONE);
        requireActivity().findViewById(com.example.datvexe.R.id.pager).setVisibility(android.view.View.VISIBLE);
    }
} 
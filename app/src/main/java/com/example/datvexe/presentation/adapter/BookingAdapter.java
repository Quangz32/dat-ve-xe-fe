package com.example.datvexe.presentation.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import com.example.datvexe.R;
import com.example.datvexe.databinding.ItemBookingBinding;
import com.example.datvexe.domain.model.BookingTrip;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private List<BookingTrip> bookings = new ArrayList<>();
    private final Fragment fragment;

    public BookingAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setBookings(List<BookingTrip> bookings) {
        this.bookings = bookings != null ? bookings : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingTrip booking = bookings.get(position);
        holder.bind(booking);
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    class BookingViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvBookingCode;
        private final TextView tvRoute;
        private final TextView tvSeats;
        private final TextView tvPrice;
        private final TextView tvStatusSuccess;
        private final TextView tvStatusError;
        private final TextView tvDate;
        private final TextView tvPaymentMethod;
        private final TextView tvUserName;


        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookingCode = itemView.findViewById(R.id.tv_booking_code);
            tvRoute = itemView.findViewById(R.id.tv_route);
            tvSeats = itemView.findViewById(R.id.tv_seats);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStatusSuccess = itemView.findViewById(R.id.tv_status_success);
            tvStatusError = itemView.findViewById(R.id.tv_status_error);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvPaymentMethod = itemView.findViewById(R.id.tv_payment_method);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
        }

        public void bind(BookingTrip booking) {
            tvBookingCode.setText("Mã đặt: " + booking.getCode());

            // Hiển thị thông tin tuyến đường
            if (booking.getBusScheduleDetail() != null) {
                tvRoute.setText(booking.getBusScheduleDetail().getRoute());
            } else {
                tvRoute.setText("N/A");
            }

            // Hiển thị ghế đã đặt
            if (booking.getSeats() != null && !booking.getSeats().isEmpty()) {
                tvSeats.setText("Ghế: " + String.join(", ", booking.getSeats()));
            } else {
                tvSeats.setText("Ghế: N/A");
            }

            // Hiển thị giá tiền
            if (booking.getTotalPrice() != null) {
                tvPrice.setText("Giá: " + currencyFormat.format(booking.getTotalPrice()));
            } else {
                tvPrice.setText("Giá: N/A");
            }

            // Hiển thị trạng thái
            String status = booking.getStatus();
//            Log.d("xxx", "bind: " + status);
            if (status.equals("cancelled")){
                tvStatusSuccess.setVisibility(View.GONE);
                tvStatusError.setVisibility(View.VISIBLE);
                tvStatusError.setText("Trạng thái: " + getStatusText(status));
            } else {
                tvStatusSuccess.setVisibility(View.VISIBLE);
                tvStatusError.setVisibility(View.GONE);
                tvStatusSuccess.setText("Trạng thái: " + getStatusText(status));
            }
//            tv

            // Hiển thị ngày giờ
            if (booking.getDepartureTime() != null) {
                tvDate.setText("Khởi hành: " + dateFormat.format(booking.getDepartureTime()));
            } else {
                tvDate.setText("Khởi hành: N/A");
            }

            // Hiển thị phương thức thanh toán
            tvPaymentMethod.setText("Thanh toán: " + booking.getPaymentMethod());

            // Hiển thị tên khách hàng
            if (booking.getUserDetail() != null) {
                tvUserName.setText("Khách hàng: " + booking.getUserDetail().getFullname());
            } else {
                tvUserName.setText("Khách hàng: N/A");
            }

            // Sự kiện click mở BookingDetailFragment
            itemView.setOnClickListener(v -> {
                com.example.datvexe.presentation.screens.fragments.BookingDetailFragment fragmentDetail = new com.example.datvexe.presentation.screens.fragments.BookingDetailFragment();
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putString("booking_code", booking.getCode());
                fragmentDetail.setArguments(bundle);
                fragment.requireActivity().findViewById(com.example.datvexe.R.id.frame_layout).setVisibility(android.view.View.VISIBLE);
                fragment.requireActivity().findViewById(com.example.datvexe.R.id.pager).setVisibility(android.view.View.GONE);
                fragment.requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(com.example.datvexe.R.id.frame_layout, fragmentDetail)
                        .addToBackStack(null)
                        .commit();
            });
        }

        private String getStatusText(String status) {
            if (status == null) return "N/A";

            switch (status.toLowerCase()) {
                case "pending":
                    return "Chờ xử lý";
                case "confirmed":
                    return "Đã xác nhận";
                case "payed":
                    return "Đã thanh toán";
                case "cancelled":
                    return "Đã hủy";
                case "completed":
                    return "Hoàn thành";
                case "draft":
                    return "Nháp";
                default:
                    return status;
            }
        }
    }
} 
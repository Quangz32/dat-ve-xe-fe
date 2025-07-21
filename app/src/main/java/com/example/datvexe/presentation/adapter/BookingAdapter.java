package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
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
        private final TextView tvStatus;
        private final TextView tvDate;
        private final TextView tvPaymentMethod;
        private final TextView tvUserName;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookingCode = itemView.findViewById(R.id.tv_booking_code);
            tvRoute = itemView.findViewById(R.id.tv_route);
            tvSeats = itemView.findViewById(R.id.tv_seats);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStatus = itemView.findViewById(R.id.tv_status);
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
            tvStatus.setText("Trạng thái: " + getStatusText(booking.getStatus()));

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
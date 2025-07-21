package com.example.datvexe.presentation.screens.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.datvexe.databinding.FragmentPaymentBinding;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.PaymentViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaymentFragment extends Fragment {
    private static final String ARG_CODE = "code";
    private static final String ARG_SCHEDULE = "schedule";
    private static final String ARG_BUS_TYPE = "bus_type";
    private static final String ARG_SELECTED_SEATS = "selected_seats";
    private static final String ARG_TOTAL_PRICE = "total_price";
    private static final String ARG_CURRENT_FLOOR = "current_floor";

    private BookingTrip code;
    private BusSchedule schedule;
    private String busType;
    private ArrayList<String> selectedSeats;
    private int totalPrice;
    private int currentFloor;
    private FragmentPaymentBinding viewBinding;
    private PaymentViewModel viewModel;
    private final int TAB_INDEX = 1;

    private RadioButton rbQr, rbOnline, rbCash, rbBank;
    private Button btnPay;
    private Button btnHome;

    private int selectedMethod = -1;

    private CountDownTimer countDownTimer;
    private String bookingCode; // Nếu cần
    public PaymentFragment() {}


    public static PaymentFragment newInstance( BusSchedule schedule, String busType,
                                               ArrayList<String> selectedSeats, int totalPrice, int currentFloor){
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCHEDULE, schedule);
        args.putString(ARG_BUS_TYPE, busType);
        args.putStringArrayList(ARG_SELECTED_SEATS, selectedSeats);
        args.putInt(ARG_TOTAL_PRICE, totalPrice);
        args.putInt(ARG_CURRENT_FLOOR, currentFloor);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            schedule = getArguments().getParcelable(ARG_SCHEDULE);
            busType = getArguments().getString(ARG_BUS_TYPE);
            selectedSeats = getArguments().getStringArrayList(ARG_SELECTED_SEATS);
            totalPrice = getArguments().getInt(ARG_TOTAL_PRICE, 0);
            currentFloor = getArguments().getInt(ARG_CURRENT_FLOOR, 1);
        }
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        viewBinding = FragmentPaymentBinding.inflate(inflater, container, false);
        viewBinding.tvTotal.setText(String.format("%,d đ", totalPrice));

        // Gán điểm đến
        if (schedule != null) {
            viewBinding.tvRoute.setText(schedule.getRoute());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dateString = sdf.format(schedule.getDate());
            viewBinding.tvTime.setText(dateString);
        }
//        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
//        viewModel.getEndBook();
        // Lấy userId từ token

//       setTextView();
        // Observe bookingsLiveData để hiển thị booking mới nhất


        // Thêm tham chiếu tới btnHome (giả sử đã có trong layout, nếu chưa có thì cần thêm vào XML)
        btnHome = viewBinding.btnHome;
        btnHome.setVisibility(View.GONE); // Ẩn khi bắt đầu
        btnHome.setBackgroundColor(0xFFFFDAB9); // Màu cam nhạt
        btnHome.setOnClickListener(v -> {
            if (requireActivity() instanceof MainActivity) {
                HomeFragment paymentQrFragment = HomeFragment.newInstance(
                );
                ((MainActivity) requireActivity()).navigateToFragment(paymentQrFragment);
            }
        });


        // Gán điểm đến
//        if (schedule != null) {
//            viewBinding.tvRoute.setText(schedule.getRoute());
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
//            String dateString = sdf.format(schedule.getDate());
//            viewBinding.tvTime.setText(dateString);
//        }

        // Lấy các RadioButton từ viewBinding
        rbQr = viewBinding.rbQr;
        rbOnline = viewBinding.rbOnline;
        rbCash = viewBinding.rbCash;
        rbBank = viewBinding.rbBank;
        btnPay = viewBinding.btnPay;
        btnPay.setEnabled(false); // Ban đầu tắt nút

        // --- Thêm lại countdown trực tiếp trong Fragment ---
        if (countDownTimer != null) countDownTimer.cancel();
        countDownTimer = new CountDownTimer(5 * 60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;
                viewBinding.tvCountdown.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
                btnHome.setVisibility(View.GONE); // Ẩn nút Trang chủ khi đang đếm
            }
            public void onFinish() {
                viewBinding.tvCountdown.setText("Hết thời gian");
                btnHome.setVisibility(View.VISIBLE); // Hiện nút Trang chủ khi hết thời gian
                btnHome.setBackgroundColor(0xFFFFDAB9); // Màu cam nhạt
            }
        };
        countDownTimer.start();
        // --- Kết thúc countdown ---
        setTypePayment();

        // Không còn observe countdown từ ViewModel nữa

        // Đổi màu btnPay khi chọn phương thức thanh toán
        View.OnClickListener radioListener = v -> {
            btnPay.setEnabled(true); // Bật nút khi chọn phương thức
            btnPay.setBackgroundColor(0xFFFFDAB9); // Màu cam nhạt
        };

        rbQr.setOnClickListener(radioListener);
        rbOnline.setOnClickListener(radioListener);
        rbCash.setOnClickListener(radioListener);
        rbBank.setOnClickListener(radioListener);

        return viewBinding.getRoot();
    }


//    public void setTextView(){
//        viewModel.bookingTrip.observe(getViewLifecycleOwner(), bookings -> {
//            if (bookings != null) {
//                // Lấy booking mới nhất
//                viewBinding.tvBookingId.setText(bookings.getCode());
//                viewBinding.tvTotal.setText(String.format("%,d đ", totalPrice));
//                viewBinding.tvRoute.setText(schedule.getRoute());
//                viewBinding.tvTime.setText("15:00 22.7.2025");
//                viewBinding.tvCompany.setText("Sao Viet Travel");
//                // Hiển thị thêm các trường khác nếu cần
//            }
//        });
//    }
    
    public void setTypePayment(){
        viewBinding.btnPay.setOnClickListener(v ->{
            if(viewBinding.rbQr.isChecked()|| viewBinding.rbOnline.isChecked() || viewBinding.rbBank.isChecked()||viewBinding.rbQr.isChecked()){
                    if (requireActivity() instanceof MainActivity) {
                        PaymentQrFragment paymentQrFragment = PaymentQrFragment.newInstance(
                                schedule,
                                busType,
                                selectedSeats,
                                totalPrice,
                                currentFloor
                        );
                        ((MainActivity) requireActivity()).navigateToFragment(paymentQrFragment);
                    }
   }
//            else
//                if(viewBinding.rbOnline.isChecked() || viewBinding.rbBank.isChecked()||viewBinding.rbQr.isChecked()){
//                    if (requireActivity() instanceof MainActivity) {
//                        PaymentVnPayFragment paymentQrFragment = PaymentVnPayFragment.newInstance(
//                                schedule,
//                                busType,
//                                selectedSeats,
//                                totalPrice,
//                                currentFloor
//                        );
//                        ((MainActivity) requireActivity()).navigateToFragment(paymentQrFragment);
//                    }
//            } else {
//                {
//                        if (requireActivity() instanceof MainActivity || viewBinding.rbOnline.isChecked() || viewBinding.rbBank.isChecked()||viewBinding.rbQr.isChecked()) {
//                            PaymentSuccessFragment paymentQrFragment = PaymentSuccessFragment.newInstance(
//                                    schedule,
//                                    busType,
//                                    selectedSeats,
//                                    totalPrice,
//                                    currentFloor
//                            );
//                            ((MainActivity) requireActivity()).navigateToFragment(paymentQrFragment);
//                        }
//
//                }
//            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) countDownTimer.cancel();
    }


}
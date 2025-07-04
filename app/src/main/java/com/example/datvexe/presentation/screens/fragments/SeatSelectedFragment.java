package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
import com.example.datvexe.data.local.SharedPreferencesManager;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.presentation.adapter.SeatAdapter;
import com.example.datvexe.presentation.model.Seat;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.screens.fragments.BookingConfirmFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SeatSelectedFragment extends Fragment {

    private static final String TAG = "SeatSelectedFragment";
    private static final String ARG_SCHEDULE = "schedule";
    private static final String ARG_BUS_TYPE = "bus_type";
    private static final String ARG_SELECTED_SEATS = "selected_seats";
    private static final String ARG_TOTAL_PRICE = "total_price";
    private static final String ARG_CURRENT_FLOOR = "current_floor";

    private BusSchedule schedule;
    private String busType;
    private RecyclerView recyclerViewSeats;
    private TextView tvTotalPrice;
    private Button btnContinue;
    private TextView tabFloor1;
    private TextView tabFloor2;
    private TextView tvBusInfo;
    
    private int currentFloor = 1;
    private List<Seat> selectedSeats = new ArrayList<>();
    private Map<String, List<Seat>> seatsByFloor = new HashMap<>();
    private SeatAdapter seatAdapter;
    private int totalPrice = 0;

    private SharedPreferencesManager sharedPreferencesManager;

    public SeatSelectedFragment() {
        // Required empty public constructor
    }

    public static SeatSelectedFragment newInstance(BusSchedule schedule, String busType) {
        return newInstance(schedule, busType, new ArrayList<>(), 0, 1);
    }

    public static SeatSelectedFragment newInstance(BusSchedule schedule, String busType, 
            ArrayList<String> selectedSeatIds, int totalPrice, int currentFloor) {
        SeatSelectedFragment fragment = new SeatSelectedFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCHEDULE, schedule);
        args.putString(ARG_BUS_TYPE, busType);
        args.putStringArrayList(ARG_SELECTED_SEATS, selectedSeatIds);
        args.putInt(ARG_TOTAL_PRICE, totalPrice);
        args.putInt(ARG_CURRENT_FLOOR, currentFloor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        sharedPreferencesManager = new SharedPreferencesManager(requireContext());
        
        // Khởi tạo dữ liệu ghế trước
        if (getArguments() != null) {
            schedule = getArguments().getParcelable(ARG_SCHEDULE);
            busType = getArguments().getString(ARG_BUS_TYPE);
            totalPrice = getArguments().getInt(ARG_TOTAL_PRICE, 0);
            currentFloor = getArguments().getInt(ARG_CURRENT_FLOOR, 1);
        }

        // Khởi tạo seatsByFloor trước khi khôi phục trạng thái ghế đã chọn
        initSeatData();
        
        // Sau đó mới khôi phục trạng thái ghế đã chọn
        if (getArguments() != null) {
            ArrayList<String> selectedSeatIds = getArguments().getStringArrayList(ARG_SELECTED_SEATS);
            selectedSeats = new ArrayList<>();
            if (selectedSeatIds != null && !selectedSeatIds.isEmpty()) {
                // Convert seat IDs back to Seat objects
                for (String seatId : selectedSeatIds) {
                    // Find the seat in seatsByFloor and add it to selectedSeats if found
                    for (List<Seat> floorSeats : seatsByFloor.values()) {
                        for (Seat seat : floorSeats) {
                            if (seat.getId().equals(seatId)) {
                                seat.setSelected(true);
                                selectedSeats.add(seat);
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        // Log để kiểm tra thông tin schedule
        if (schedule != null) {
            Log.d(TAG, "Schedule ID: " + schedule.getId());
            Log.d(TAG, "Bus Type: " + busType);
            Log.d(TAG, "Selected Seats: " + selectedSeats.size());
            Log.d(TAG, "Total Price: " + totalPrice);
            Log.d(TAG, "Current Floor: " + currentFloor);
            
            // Kiểm tra danh sách ghế đã đặt
            List<String> bookedSeats = schedule.getSeatSelected();
            if (bookedSeats != null && !bookedSeats.isEmpty()) {
                Log.d(TAG, "Booked seats: " + bookedSeats.toString());
            } else {
                Log.d(TAG, "No booked seats found");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        setupBusInfo();
        markBookedSeats();
        setupRecyclerView();
        setupListeners();
        
        // Cập nhật UI để hiển thị tổng tiền
        updateTotalPrice();
        updateContinueButton();
    }

    private void initViews(View view) {
        recyclerViewSeats = view.findViewById(R.id.recyclerViewSeats);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        btnContinue = view.findViewById(R.id.btnContinue);
        tabFloor1 = view.findViewById(R.id.tabFloor1);
        tabFloor2 = view.findViewById(R.id.tabFloor2);
        tvBusInfo = view.findViewById(R.id.tvBusInfo);
        
        view.findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (requireActivity() instanceof MainActivity) {
                BusScheduleFragment busScheduleFragment = new BusScheduleFragment();
                ((MainActivity) requireActivity()).navigateToFragment(busScheduleFragment);
            }
        });
    }

    private void setupBusInfo() {
        // Set bus type info
        if ("BUS34".equals(busType)) {
            tvBusInfo.setText(R.string.bus_34_seats);
        } else {
            tvBusInfo.setText(R.string.bus_20_seats);
        }
    }

    private void initSeatData() {
        // Initialize seat data based on bus type
        if ("BUS34".equals(busType)) {
            initBus34Layout();
        } else {
            initBus20Layout();
        }
    }

    private void markBookedSeats() {
        // Đánh dấu các ghế đã được đặt từ schedule
        if (schedule != null && schedule.getSeatSelected() != null) {
            List<String> bookedSeatIds = schedule.getSeatSelected();
            Log.d(TAG, "Processing booked seats: " + bookedSeatIds);
            
            int bookedSeatsCount = 0;
            
            // Duyệt qua tất cả các ghế trong các tầng và đánh dấu ghế đã đặt
            for (Map.Entry<String, List<Seat>> entry : seatsByFloor.entrySet()) {
                String floor = entry.getKey();
                List<Seat> floorSeats = entry.getValue();
                
                for (Seat seat : floorSeats) {
                    if (bookedSeatIds.contains(seat.getId())) {
                        seat.setBooked(true);
                        bookedSeatsCount++;
                        Log.d(TAG, "Marked seat as booked: " + seat.getId() + " on floor " + floor);
                    }
                }
            }
            
            Log.d(TAG, "Total marked booked seats: " + bookedSeatsCount + " out of " + bookedSeatIds.size() + " from schedule");
        } else {
            Log.d(TAG, "No booked seats data available in schedule");
        }
    }

    private void initBus34Layout() {
        // Floor 1
        List<Seat> floor1Seats = new ArrayList<>();
        floor1Seats.add(new Seat("A1", 400000, false));
        floor1Seats.add(new Seat("A2", 400000, false));
        floor1Seats.add(new Seat("A3", 400000, false));
        floor1Seats.add(new Seat("B1", 400000, false));
        floor1Seats.add(new Seat("B2", 400000, false));
        floor1Seats.add(new Seat("B3", 400000, false));
        floor1Seats.add(new Seat("C1", 400000, false));
        floor1Seats.add(new Seat("C2", 400000, false));
        floor1Seats.add(new Seat("C3", 400000, false));
        floor1Seats.add(new Seat("D1", 400000, false));
        floor1Seats.add(new Seat("D2", 400000, false));
        floor1Seats.add(new Seat("D3", 400000, false));
        floor1Seats.add(new Seat("E1", 400000, false));
        floor1Seats.add(new Seat("E2", 400000, false));
        floor1Seats.add(new Seat("E3", 400000, false));
        floor1Seats.add(new Seat("F1", 350000, false));
        floor1Seats.add(new Seat("F2", 350000, false));
        
        // Floor 2
        List<Seat> floor2Seats = new ArrayList<>();
        floor2Seats.add(new Seat("G1", 400000, false));
        floor2Seats.add(new Seat("G2", 400000, false));
        floor2Seats.add(new Seat("G3", 400000, false));
        floor2Seats.add(new Seat("H1", 400000, false));
        floor2Seats.add(new Seat("H2", 400000, false));
        floor2Seats.add(new Seat("H3", 400000, false));
        floor2Seats.add(new Seat("I1", 400000, false));
        floor2Seats.add(new Seat("I2", 400000, false));
        floor2Seats.add(new Seat("I3", 400000, false));
        floor2Seats.add(new Seat("J1", 400000, false));
        floor2Seats.add(new Seat("J2", 400000, false));
        floor2Seats.add(new Seat("J3", 400000, false));
        floor2Seats.add(new Seat("K1", 400000, false));
        floor2Seats.add(new Seat("K2", 400000, false));
        floor2Seats.add(new Seat("K3", 400000, false));
        floor2Seats.add(new Seat("L1", 350000, false));
        floor2Seats.add(new Seat("L2", 350000, false));
        
        seatsByFloor.put("1", floor1Seats);
        seatsByFloor.put("2", floor2Seats);
    }

    private void initBus20Layout() {
        // Floor 1
        List<Seat> floor1Seats = new ArrayList<>();
        floor1Seats.add(new Seat("1", 500000, false));
        floor1Seats.add(new Seat("3", 500000, false));
        floor1Seats.add(new Seat("5", 500000, false));
        floor1Seats.add(new Seat("7", 500000, false));
        floor1Seats.add(new Seat("9", 500000, false));
        floor1Seats.add(new Seat("11", 400000, false));
        floor1Seats.add(new Seat("2", 500000, false));
        floor1Seats.add(new Seat("4", 500000, false));
        floor1Seats.add(new Seat("6", 500000, false));
        floor1Seats.add(new Seat("8", 500000, false));
        floor1Seats.add(new Seat("10", 500000, false));
        floor1Seats.add(new Seat("12", 400000, false));
        
        // Floor 2
        List<Seat> floor2Seats = new ArrayList<>();
        floor2Seats.add(new Seat("13", 500000, false));
        floor2Seats.add(new Seat("15", 500000, false));
        floor2Seats.add(new Seat("17", 500000, false));
        floor2Seats.add(new Seat("19", 500000, false));
        floor2Seats.add(new Seat("21", 500000, false));
        floor2Seats.add(new Seat("23", 400000, false));
        floor2Seats.add(new Seat("14", 500000, false));
        floor2Seats.add(new Seat("16", 500000, false));
        floor2Seats.add(new Seat("18", 500000, false));
        floor2Seats.add(new Seat("20", 500000, false));
        floor2Seats.add(new Seat("22", 500000, false));
        floor2Seats.add(new Seat("24", 400000, false));
        
        seatsByFloor.put("1", floor1Seats);
        seatsByFloor.put("2", floor2Seats);
    }

    private void setupRecyclerView() {
        int spanCount = "BUS34".equals(busType) ? 3 : 2;
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), spanCount);
        recyclerViewSeats.setLayoutManager(layoutManager);
        
        seatAdapter = new SeatAdapter(seatsByFloor.get(String.valueOf(currentFloor)), this::onSeatSelected);
        recyclerViewSeats.setAdapter(seatAdapter);
    }

    private void setupListeners() {
        tabFloor1.setOnClickListener(v -> switchFloor(1));
        tabFloor2.setOnClickListener(v -> switchFloor(2));
        
        // Set up route button click listener
        requireView().findViewById(R.id.routeButton).setOnClickListener(v -> navigateToRouteDetail());
        
        // Set up bus info button click listener
        requireView().findViewById(R.id.busInfoButton).setOnClickListener(v -> {
            if (schedule != null && requireActivity() instanceof MainActivity) {
                // Create list of selected seat IDs
                ArrayList<String> selectedSeatIds = new ArrayList<>();
                for (Seat seat : selectedSeats) {
                    selectedSeatIds.add(seat.getId());
                }
                
                BusInfoFragment busInfoFragment = BusInfoFragment.newInstance(
                    busType, 
                    schedule.getBusOperator(), 
                    schedule,
                    selectedSeatIds,
                    totalPrice,
                    currentFloor
                );
                ((MainActivity) requireActivity()).navigateToFragment(busInfoFragment);
            }
        });
        
        btnContinue.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng chọn ít nhất 1 ghế", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Navigate to BookingConfirmFragment with all required data
            BookingConfirmFragment confirmFragment = BookingConfirmFragment.newInstance(
                schedule,
                new ArrayList<>(selectedSeats),
                totalPrice,
                busType
            );
            
            // Navigate to the next fragment using MainActivity's method
            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).navigateToFragment(confirmFragment);
            }
        });
    }

    private void navigateToRouteDetail() {
        if (schedule != null && requireActivity() instanceof MainActivity) {
            // Create list of selected seat IDs
            ArrayList<String> selectedSeatIds = new ArrayList<>();
            for (Seat seat : selectedSeats) {
                selectedSeatIds.add(seat.getId());
            }
            
            // Create and navigate to the RouteDetailFragment with current state
            RouteDetailFragment routeDetailFragment = RouteDetailFragment.newInstance(
                schedule,
                busType,
                selectedSeatIds,
                totalPrice,
                currentFloor
            );
            ((MainActivity) requireActivity()).navigateToFragment(routeDetailFragment);
        }
    }

    private void switchFloor(int floor) {
        if (currentFloor == floor) return;
        
        currentFloor = floor;
        
        // Update UI
        if (floor == 1) {
            tabFloor1.setBackgroundColor(getResources().getColor(R.color.coral));
            tabFloor1.setTextColor(getResources().getColor(android.R.color.white));
            tabFloor2.setBackgroundColor(getResources().getColor(R.color.whitesmoke));
            tabFloor2.setTextColor(getResources().getColor(android.R.color.black));
        } else {
            tabFloor2.setBackgroundColor(getResources().getColor(R.color.coral));
            tabFloor2.setTextColor(getResources().getColor(android.R.color.white));
            tabFloor1.setBackgroundColor(getResources().getColor(R.color.whitesmoke));
            tabFloor1.setTextColor(getResources().getColor(android.R.color.black));
        }
        
        // Update seats
        seatAdapter.updateSeats(seatsByFloor.get(String.valueOf(currentFloor)));
    }

    private void onSeatSelected(Seat seat) {
        // Kiểm tra xem ghế đã được đặt chưa
        if (seat.isBooked()) {
            // Ghế đã đặt, không cho chọn và thông báo
            Toast.makeText(requireContext(), "Ghế " + seat.getId() + " đã có người đặt", Toast.LENGTH_SHORT).show();
            return;
        }

        if (seat.isSelected()) {
            // Deselect seat
            seat.setSelected(false);
            selectedSeats.remove(seat);
            totalPrice -= seat.getPrice();
        } else {
            // Select seat
            seat.setSelected(true);
            selectedSeats.add(seat);
            totalPrice += seat.getPrice();
        }
        
        // Update UI
        updateTotalPrice();
        seatAdapter.notifyDataSetChanged();
        updateContinueButton();
    }

    private void updateTotalPrice() {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        tvTotalPrice.setText(formatter.format(totalPrice) + "đ");
    }

    private void updateContinueButton() {
        boolean hasSelectedSeats = !selectedSeats.isEmpty();
        btnContinue.setEnabled(hasSelectedSeats);
        if (hasSelectedSeats) {
            btnContinue.setBackgroundColor(getResources().getColor(R.color.coral));
            btnContinue.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            btnContinue.setBackgroundColor(getResources().getColor(R.color.lightgrey));
            btnContinue.setTextColor(getResources().getColor(android.R.color.white));
        }
    }
} 

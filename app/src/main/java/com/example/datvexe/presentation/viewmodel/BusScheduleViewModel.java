package com.example.datvexe.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.data.mapper.ScheduleMapper;
import com.example.datvexe.data.remote.api.callback.ScheduleCallBack;
import com.example.datvexe.data.remote.dto.ScheduleResponseDto;
import com.example.datvexe.data.remote.dto.request.ScheduleReq;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.domain.model.BusStation;
import com.example.datvexe.domain.repository.TripRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BusScheduleViewModel extends ViewModel {
    private static final String TAG = "BusScheduleViewModel";
    private final MutableLiveData<List<BusSchedule>> _schedules = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    private final MutableLiveData<Date> _selectedDate = new MutableLiveData<>();
    private final MutableLiveData<BusStation> _fromStation = new MutableLiveData<>();
    private final MutableLiveData<BusStation> _toStation = new MutableLiveData<>();
    private final MutableLiveData<Integer> _passengerCount = new MutableLiveData<>(1);

    private final TripRepository tripRepository;

    public LiveData<List<BusSchedule>> getSchedules() {
        return _schedules;
    }

    public LiveData<Boolean> getIsLoading() {
        return _isLoading;
    }

    public LiveData<String> getError() {
        return _error;
    }

    public LiveData<Date> getSelectedDate() {
        return _selectedDate;
    }

    public void setSelectedDate(Date date) {
        _selectedDate.setValue(date);
        loadSchedules();
    }

    public void setFromStation(BusStation station) {
        _fromStation.setValue(station);
    }

    public void setToStation(BusStation station) {
        _toStation.setValue(station);
    }

    public void setPassengerCount(int count) {
        _passengerCount.setValue(count);
    }

    @Inject
    public BusScheduleViewModel(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
        // Set default selected date to today
        _selectedDate.setValue(new Date());
    }

    public void loadSchedules() {
        _isLoading.setValue(true);

        // Tạm thời tạo dữ liệu mẫu nếu không có đủ thông tin trạm
        BusStation fromStation = _fromStation.getValue();
        BusStation toStation = _toStation.getValue();
        
        if (fromStation == null || fromStation.getId() == null || 
            toStation == null || toStation.getId() == null) {
            // Hiển thị lỗi
            _error.postValue("Không đủ thông tin trạm để tải lịch trình");
            _isLoading.postValue(false);
            return;
        }

        // Create request with current parameters
        ScheduleReq request = new ScheduleReq(
            fromStation.getId(),  
            toStation.getId(),
            _selectedDate.getValue(),
            _passengerCount.getValue()
        );

        Log.d(TAG, "Loading schedules for: " + 
            "From: " + request.getBenXeKhoiHanh() + 
            ", To: " + request.getBenXeDichDen() + 
            ", Date: " + request.getDate());

        // Call API
        tripRepository.loadDataSchedule(request, new ScheduleCallBack() {
            @Override
            public void onSuccess(List<ScheduleResponseDto> data) {
                try {
                    Log.d(TAG, "API returned " + (data != null ? data.size() : 0) + " schedules");
                    List<BusSchedule> schedules = new ArrayList<>();
                    
                    if (data != null && !data.isEmpty()) {
                        schedules = ScheduleMapper.toDomainModelList(data);
                        Log.d(TAG, "Mapped " + schedules.size() + " schedules successfully");
                    }
                    
                    _schedules.postValue(schedules);
                    _isLoading.postValue(false);
                } catch (Exception e) {
                    Log.e(TAG, "Error mapping schedules", e);
                    _error.postValue("Lỗi xử lý dữ liệu: " + e.getMessage());
                    _isLoading.postValue(false);
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "API error: " + errorMessage);
                _error.postValue(errorMessage);
                _isLoading.postValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "API failure", throwable);
                _error.postValue("Lỗi kết nối: " + (throwable.getMessage() != null ? throwable.getMessage() : "Không thể kết nối đến máy chủ"));
                _isLoading.postValue(false);
                
                // Khởi tạo dữ liệu mẫu nếu API thất bại (để demo)
                if (_schedules.getValue() == null || _schedules.getValue().isEmpty()) {
                    loadDummyData();
                }
            }
        });
    }
    
    // Tạo dữ liệu mẫu để hiển thị khi gặp lỗi kết nối
    private void loadDummyData() {
        List<BusSchedule> dummySchedules = new ArrayList<>();
        
        // Thêm một số mục mẫu
        BusSchedule schedule1 = new BusSchedule(
            "Sao Việt Express", 
            "Hà Nội - Lào Cai", 
            "Giường nằm 34 chỗ",
            450000,
            "19:00",
            "Bến xe Mỹ Đình",
            "7 giờ",
            "02:00",
            "Bến xe Lào Cai"
        );
        
        BusSchedule schedule2 = new BusSchedule(
            "Phương Trang", 
            "Sài Gòn - Đà Lạt", 
            "Giường nằm 40 chỗ",
            300000,
            "20:00",
            "Bến xe Miền Đông",
            "8 giờ",
            "04:00",
            "Bến xe Đà Lạt"
        );
        
        dummySchedules.add(schedule1);
        dummySchedules.add(schedule2);
        
        _schedules.postValue(dummySchedules);
    }

    public void loadSchedulesForDate(Date date) {
        setSelectedDate(date);
    }
} 

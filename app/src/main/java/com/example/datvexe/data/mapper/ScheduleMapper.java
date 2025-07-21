package com.example.datvexe.data.mapper;

import android.util.Log;

import com.example.datvexe.data.remote.dto.BusOperatorDto;
import com.example.datvexe.data.remote.dto.BusStationDto;
import com.example.datvexe.data.remote.dto.ScheduleResponseDto;
import com.example.datvexe.data.remote.dto.TypeBusDto;
import com.example.datvexe.domain.model.BusOperators;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.domain.model.BusStation;
import com.example.datvexe.domain.model.TypeBus;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ScheduleMapper {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private static final Gson gson = new Gson();

    public static BusSchedule toDomainModel(ScheduleResponseDto dto) {
        if (dto == null) return null;

        // Tính toán thời gian di chuyển từ timeRoute (đơn vị giờ)
        String duration = "";
        if (dto.getTimeRoute() != null) {
            int hours = dto.getTimeRoute().intValue();
            duration = hours + " giờ";
        }

        BusOperatorDto busOperatorDto = dto.getBusOperator();
        BusStationDto departureStationDto = dto.getBenXeKhoiHanh();
        BusStationDto arrivalStationDto = dto.getBenXeDichDen();

        // Định dạng giá vé đẹp hơn
        String formattedPrice = currencyFormat.format(dto.getPrice() != null ? dto.getPrice().doubleValue() : 0);
        formattedPrice = formattedPrice.replace("₫", "đ"); // Thay đổi ký hiệu tiền tệ cho đẹp hơn

        // Thông tin loại xe và số ghế
        String busInfo = "";
        String busTypeCode = "BUS34"; // Mã loại xe mặc định
        List<String> busTypes = new ArrayList<>();
        
        if (busOperatorDto != null) {
            try {
                Object typesObj = busOperatorDto.getTypes();
                if (typesObj != null) {
                    if (typesObj instanceof LinkedTreeMap) {
                        // Trường hợp types là đối tượng đơn lẻ (JSON object)
                        LinkedTreeMap<?, ?> typeMap = (LinkedTreeMap<?, ?>) typesObj;
                        
                        // Lấy thông tin từ LinkedTreeMap
                        String typeName = typeMap.containsKey("name") ? typeMap.get("name").toString() : "";
                        String seats = typeMap.containsKey("seats") ? typeMap.get("seats").toString() : "";
                        String code = typeMap.containsKey("code") ? typeMap.get("code").toString() : "BUS34";
                        
                        busInfo = typeName;
                        if (!seats.isEmpty()) {
                            busInfo += " (" + seats + " chỗ)";
                        }
                        
                        busTypeCode = code;
                        busTypes.add(code);
                    } else {
                        // Fallback khi không xử lý được type
                        busInfo = busOperatorDto.getName() + " Bus";
                    }
                } else {
                    busInfo = busOperatorDto.getName() + " Bus";
                }
            } catch (Exception e) {
                // Trong trường hợp có lỗi, sử dụng tên nhà xe
                busInfo = busOperatorDto.getName() + " Bus";
            }
        }

        // Tính toán thời gian đến dựa trên thời gian khởi hành và thời gian di chuyển
        String arrivalTime = "";
        Date departureTime = dto.getTimeStart();

        if (dto.getTimeEnd() != null) {
            arrivalTime = formatDate(dto.getTimeEnd());
        } else if (dto.getTimeStart() != null && dto.getTimeRoute() != null) {
            // Nếu không có timeEnd, tính toán từ timeStart và timeRoute
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dto.getTimeStart());
            calendar.add(Calendar.HOUR_OF_DAY, dto.getTimeRoute().intValue());
            arrivalTime = timeFormat.format(calendar.getTime());
        }

        // Tạo đối tượng BusOperators
        BusOperators busOperator = null;
        if (busOperatorDto != null) {
            busOperator = new BusOperators();
            busOperator.setId(busOperatorDto.getId());
            busOperator.setName(busOperatorDto.getName());
            busOperator.setPhone(busOperatorDto.getPhone());
            busOperator.setBienSoXe(busOperatorDto.getBienSoXe());
            busOperator.setTypes(busTypeCode); // Lưu mã loại xe
            busOperator.setTypesList(busTypes); // Lưu danh sách mã loại xe
            
            // Tạo đối tượng TypeBus nếu có thông tin
            if (busOperatorDto.getTypes() instanceof LinkedTreeMap) {
                LinkedTreeMap<?, ?> typeMap = (LinkedTreeMap<?, ?>) busOperatorDto.getTypes();
                TypeBus typeBus = new TypeBus();
                typeBus.setCode(busTypeCode);
                typeBus.setName(typeMap.containsKey("name") ? typeMap.get("name").toString() : "");
                if (typeMap.containsKey("seats")) {
                    try {
                        typeBus.setSeats(Integer.parseInt(typeMap.get("seats").toString()));
                    } catch (NumberFormatException e) {
                        typeBus.setSeats(34); // Mặc định 34 chỗ
                    }
                }
                busOperator.setTypeBusDetail(typeBus);
            }
        }

        BusStation departureStation = null;
        if (departureStationDto != null) {
            departureStation = new BusStation();
            departureStation.setId(departureStationDto.getId());
            departureStation.setMaBenXe(departureStationDto.getMaBenXe());
            departureStation.setTenBenXe(departureStationDto.getTenBenXe());
            departureStation.setName(departureStationDto.getTenBenXe());
        }

        BusStation arrivalStation = null;
        if (arrivalStationDto != null) {
            arrivalStation = new BusStation();
            arrivalStation.setId(arrivalStationDto.getId());
            arrivalStation.setMaBenXe(arrivalStationDto.getMaBenXe());
            arrivalStation.setTenBenXe(arrivalStationDto.getTenBenXe());
            arrivalStation.setName(arrivalStationDto.getTenBenXe());
        }

        return BusSchedule.builder()
                .id(dto.getId())
                .busOperator(busOperatorDto != null ? busOperatorDto.getId() : null)
                .tripCode(dto.getTripCode())
                .route(dto.getRoute())
                .timeRoute(dto.getTimeRoute() != null ? dto.getTimeRoute().intValue() : null)
                .price(dto.getPrice() != null ? dto.getPrice().doubleValue() : 0)
                .date(dto.getDate())
                .timeStart(dto.getTimeStart())
                .benXeKhoiHanh(departureStationDto != null ? departureStationDto.getMaBenXe() : null)
                .timeEnd(dto.getTimeEnd())
                .benXeDichDen(arrivalStationDto != null ? arrivalStationDto.getMaBenXe() : null)
                .availableSeats(dto.getAvailableSeats() != null ? dto.getAvailableSeats().intValue() : 0)
                .seatSelected(dto.getSeatSelected())
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .busName(busOperatorDto != null ? busOperatorDto.getName() : "")
                .busInfo(busInfo)
                .departureTime(departureTime)
                .departureLocation(departureStationDto != null ? departureStationDto.getTenBenXe() : "")
                .duration(duration)
                .arrivalTime(arrivalTime)
                .arrivalLocation(arrivalStationDto != null ? arrivalStationDto.getTenBenXe() : "")
                .formattedPrice(formattedPrice)
                .busOperatorDetail(busOperator)
                .benXeKhoiHanhDetail(departureStation)
                .benXeDichDenDetail(arrivalStation)
                .build();
    }

    public static List<BusSchedule> toDomainModelList(List<ScheduleResponseDto> dtoList) {
        if (dtoList == null) return new ArrayList<>();

        List<BusSchedule> scheduleList = new ArrayList<>();
        for (ScheduleResponseDto dto : dtoList) {
            try {
                BusSchedule schedule = toDomainModel(dto);
                if (schedule != null) {
                    scheduleList.add(schedule);
                }
            } catch (Exception e) {
                // Bỏ qua các mục có lỗi khi mapping
                e.printStackTrace();
            }
        }
        return scheduleList;
    }
    
    private static String formatDate(Date date) {
        if (date == null) return "";
        return timeFormat.format(date);
    }
} 

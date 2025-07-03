package com.example.datvexe.data.remote.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReq {
    private String benXeKhoiHanh;
    private String benXeDichDen;
    private Date date;
    private Number soNguoi;
}

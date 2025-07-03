package com.example.datvexe.domain.repository;

import com.example.datvexe.data.remote.api.callback.ScheduleCallBack;
import com.example.datvexe.data.remote.api.callback.TripCallBack;
import com.example.datvexe.data.remote.dto.request.ScheduleReq;

public interface TripRepository {
    void getLocationTrip(TripCallBack callBack);

    void loadDataSchedule(ScheduleReq req, ScheduleCallBack callBack);
}

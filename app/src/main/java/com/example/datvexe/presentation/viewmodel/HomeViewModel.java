package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Map.Entry<String, List<String>>>> popularRoutes = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public LiveData<List<Map.Entry<String, List<String>>>> getPopularRoutes() {
        return popularRoutes;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void fetchPopularRoutes() {
        isLoading.setValue(true);
        // Giả lập dữ liệu mẫu với 4 tuyến
        Map<String, List<String>> data = new HashMap<>();
        List<String> haNoiLaoCai = new ArrayList<>();
        haNoiLaoCai.add("Hà Nội - Lào Cai");
        List<String> laoCaiSapa = new ArrayList<>();
        laoCaiSapa.add("Lào Cai - Sapa");
        List<String> haNoiSapa = new ArrayList<>();
        haNoiSapa.add("Hà Nội - Sapa");
        List<String> haNoiYenBai = new ArrayList<>();
        haNoiYenBai.add("Hà Nội - Yên Bái");
        data.put("Hà Nội - Lào Cai", haNoiLaoCai);
        data.put("Lào Cai - Sapa", laoCaiSapa);
        data.put("Hà Nội - Sapa", haNoiSapa);
        data.put("Hà Nội - Yên Bái", haNoiYenBai);
        List<Map.Entry<String, List<String>>> list = new ArrayList<>(data.entrySet());
        popularRoutes.postValue(list);
        isLoading.postValue(false);
    }
}
package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final MutableLiveData<Integer> _currentTabIndex = new MutableLiveData<>(0);
    public final LiveData<Integer> currentTabIndex = _currentTabIndex;

    private final MutableLiveData<List<String>> _tabTitles =
            new MutableLiveData<>(Arrays.asList("Trang chủ", "Booking", "Thông báo", "Tài khoản"));
    public final LiveData<List<String>> tabTitles = _tabTitles;

    private final MutableLiveData<List<Boolean>> _showNavigateBack =
            new MutableLiveData<>(Arrays.asList(false, false, false, false));
    public final LiveData<List<Boolean>> showNavigateBack = _showNavigateBack;

    @Inject
    public MainViewModel() {
    }

    public int getCurrentTabIndex() {
        return _currentTabIndex.getValue() == null ? 0 : _currentTabIndex.getValue();
    }

    public void setCurrentTabIndex(int index) {
        _currentTabIndex.setValue(index);
    }

    public String getTabTitle(int position) {
        return _tabTitles.getValue() == null ? "null" : _tabTitles.getValue().get(position);
    }

    public void setTabTitles(int position, String title) {
        List<String> tabTitles = _tabTitles.getValue();
        if (tabTitles == null) return;
        tabTitles.set(position, title);
        _tabTitles.setValue(tabTitles);
    }

    public boolean getShowNavigateBack(int position) {
        return _showNavigateBack.getValue() != null && _showNavigateBack.getValue().get(position);
    }

    public void setShowNavigateBack(int position, boolean show) {
        List<Boolean> showNavigateBack = _showNavigateBack.getValue();
        if (showNavigateBack == null) return;
        showNavigateBack.set(position, show);
        _showNavigateBack.setValue(showNavigateBack);
    }


}

package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.model.Discount;
import com.example.datvexe.domain.usecase.booking.GetDiscountsUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DiscountViewModel extends ViewModel {
    private final GetDiscountsUseCase getDiscountsUseCase;

    private final MutableLiveData<List<Discount>> _discounts = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> _error = new MutableLiveData<>();

    public final LiveData<List<Discount>> discounts = _discounts;
    public final LiveData<Boolean> isLoading = _isLoading;
    public final LiveData<String> error = _error;

    @Inject
    public DiscountViewModel(GetDiscountsUseCase getDiscountsUseCase) {
        this.getDiscountsUseCase = getDiscountsUseCase;
    }

    public void loadDiscounts() {
        _isLoading.setValue(true);
        _error.setValue(null);

        getDiscountsUseCase.execute(new GetDiscountsUseCase.UseCaseCallback() {
            @Override
            public void onSuccess(List<Discount> discounts) {
                _isLoading.setValue(false);
                _discounts.setValue(discounts);
            }

            @Override
            public void onError(String error) {
                _isLoading.setValue(false);
                _error.setValue(error);
            }
        });
    }
} 
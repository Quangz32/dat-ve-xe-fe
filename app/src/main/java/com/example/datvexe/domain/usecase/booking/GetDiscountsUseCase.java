package com.example.datvexe.domain.usecase.booking;

import com.example.datvexe.domain.model.Discount;
import com.example.datvexe.domain.repository.BookingRepository;

import java.util.List;

import javax.inject.Inject;

public class GetDiscountsUseCase {
    private final BookingRepository bookingRepository;

    @Inject
    public GetDiscountsUseCase(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void execute(UseCaseCallback callback) {
        bookingRepository.getDiscountsByUser(new BookingRepository.DiscountCallback() {
            @Override
            public void onSuccess(List<Discount> discounts) {
                callback.onSuccess(discounts);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public interface UseCaseCallback {
        void onSuccess(List<Discount> discounts);
        void onError(String error);
    }
} 
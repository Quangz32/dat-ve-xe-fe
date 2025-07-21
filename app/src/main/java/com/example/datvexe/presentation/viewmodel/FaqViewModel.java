package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.datvexe.domain.model.FaqItem;
import java.util.Arrays;
import java.util.List;

public class FaqViewModel extends ViewModel {
    private final MutableLiveData<List<FaqItem>> faqList = new MutableLiveData<>();

    public FaqViewModel() {
        // Dữ liệu cứng
        faqList.setValue(Arrays.asList(
            new FaqItem("Làm sao để đặt vé?", "Bạn chọn tuyến, chọn ghế, nhập thông tin và nhấn Đặt vé."),
            new FaqItem("Tôi có thể hủy vé không?", "Bạn có thể hủy vé trong mục Lịch sử đặt vé trước giờ khởi hành."),
            new FaqItem("Thanh toán như thế nào?", "Bạn có thể thanh toán qua VNPay, Momo hoặc tiền mặt khi lên xe."),
            new FaqItem("Làm sao để nhận vé?", "Sau khi đặt vé thành công, mã vé sẽ được gửi về email hoặc hiển thị trong app."),
            new FaqItem("Tôi quên mật khẩu, phải làm sao?", "Bạn chọn Quên mật khẩu ở màn đăng nhập và làm theo hướng dẫn.")
        ));
    }

    public LiveData<List<FaqItem>> getFaqList() {
        return faqList;
    }
} 
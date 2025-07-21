# Hướng Dẫn Tích Hợp API Thông Tin Khách Hàng

## 1. Thay Thế API Call Trong SeatSelectedFragment

### File: `SeatSelectedFragment.java`
Tìm method `callCustomerInfoAPI()` và thay thế bằng API call thực tế:

```java
private String callCustomerInfoAPI() {
    try {
        // TODO: Thay thế bằng API call thực tế của bạn
        // Ví dụ với Retrofit:
        
        // 1. Tạo API Service
        ApiService apiService = RetrofitClient.getInstance().getApiService();
        
        // 2. Gọi API
        Call<CustomerInfoResponse> call = apiService.getCustomerInfo();
        Response<CustomerInfoResponse> response = call.execute();
        
        // 3. Xử lý response
        if (response.isSuccessful() && response.body() != null) {
            CustomerInfoResponse customerInfo = response.body();
            return new Gson().toJson(customerInfo.getData());
        } else {
            Log.e(TAG, "API call failed: " + response.code());
            return null;
        }
        
    } catch (Exception e) {
        Log.e(TAG, "Error calling customer info API: " + e.getMessage());
        return null;
    }
}
```

### 2. Tạo Model Classes

#### CustomerInfoResponse.java
```java
public class CustomerInfoResponse {
    private boolean success;
    private CustomerInfo data;
    private String message;
    
    // Getters and setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public CustomerInfo getData() { return data; }
    public void setData(CustomerInfo data) { this.data = data; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
```

#### CustomerInfo.java
```java
public class CustomerInfo {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private int loyaltyPoints;
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }
}
```

### 3. Cập Nhật Method saveCustomerInfoToSharedPreferences

```java
private void saveCustomerInfoToSharedPreferences(String customerInfo) {
    try {
        // Parse JSON response với Gson
        Gson gson = new Gson();
        CustomerInfo customer = gson.fromJson(customerInfo, CustomerInfo.class);
        
        // Lưu vào SharedPreferences
        sharedPreferencesManager.saveUserProfile(
            customer.getEmail(),
            customer.getName(),
            customer.getPhone(),
            customer.getAddress(),
            customer.getAvatar(),
            customer.getLoyaltyPoints()
        );
        
        Log.d(TAG, "Customer info saved to SharedPreferences");
    } catch (Exception e) {
        Log.e(TAG, "Error saving customer info: " + e.getMessage());
    }
}
```

### 4. Thêm Dependencies (nếu chưa có)

#### build.gradle (app level)
```gradle
dependencies {
    // Retrofit for API calls
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    
    // Gson for JSON parsing
    implementation 'com.google.code.gson:gson:2.10.1'
}
```

### 5. Tạo API Service Interface

#### ApiService.java
```java
public interface ApiService {
    @GET("api/customer/info")
    Call<CustomerInfoResponse> getCustomerInfo();
}
```

### 6. Tạo Retrofit Client

#### RetrofitClient.java
```java
public class RetrofitClient {
    private static final String BASE_URL = "https://your-api-domain.com/";
    private static RetrofitClient instance;
    private Retrofit retrofit;
    
    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
    
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }
    
    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
```

## Flow Hoạt Động

1. **User chọn ghế** trong `SeatSelectedFragment`
2. **Bấm nút "Tiếp tục"** → Hiển thị loading state
3. **Gọi API** lấy thông tin khách hàng trong background
4. **Lưu thông tin** vào SharedPreferences
5. **Chuyển sang** `BookingConfirmFragment`
6. **Hiển thị thông tin** khách hàng từ SharedPreferences

## Lưu Ý

- Đảm bảo xử lý lỗi network
- Thêm timeout cho API calls
- Cache thông tin khách hàng để tránh gọi API nhiều lần
- Hiển thị loading state trong khi gọi API
- Xử lý trường hợp API trả về lỗi 
# Food Ordering Android App

Ứng dụng đặt món ăn trên Android được phát triển bằng Kotlin. Dự án tập trung vào trải nghiệm đặt món nhanh, quản lý giỏ hàng, thanh toán, lưu lịch sử đơn hàng và xác thực người dùng bằng Firebase.

## Tổng quan

Repository này chứa project Android chính trong thư mục `Food_project/`.

```text
project_android_kotlin/
├── README.md
└── Food_project/
    ├── app/
    ├── gradle/
    ├── build.gradle.kts
    ├── settings.gradle.kts
    └── README.md
```

## Tính năng chính

- Đăng ký, đăng nhập và xác thực người dùng với Firebase Authentication.
- Hỗ trợ đăng nhập Google Sign-In.
- Hiển thị danh sách món ăn, món phổ biến và chi tiết món ăn.
- Tìm kiếm món ăn theo nhu cầu người dùng.
- Quản lý giỏ hàng với Room Database.
- Thanh toán và lưu lịch sử đơn hàng.
- Quản lý thông tin cá nhân và đổi mật khẩu.
- Đồng bộ dữ liệu người dùng, menu và đơn hàng với Firebase Realtime Database.

## Công nghệ sử dụng

- Kotlin
- Android SDK
- AndroidX, Material Components
- Firebase Authentication
- Firebase Realtime Database
- Google Sign-In
- Room Database
- Retrofit, Gson
- Glide
- ViewBinding
- Kotlin Coroutines

## Yêu cầu môi trường

- Android Studio phiên bản mới
- JDK 11 trở lên
- Android Gradle Plugin tương thích với project
- Thiết bị hoặc emulator Android API 24 trở lên

## Cách chạy dự án

1. Clone repository:

   ```bash
   git clone https://github.com/phamhao1806/project_android_kotlin.git
   ```

2. Mở thư mục `Food_project/` bằng Android Studio.

3. Kiểm tra file cấu hình Firebase:

   ```text
   Food_project/app/google-services.json
   ```

4. Sync Gradle và chạy app trên emulator hoặc thiết bị thật.

## Ghi chú

- Đây là đồ án Android Kotlin về hệ thống đặt món ăn.
- Project chính nằm trong `Food_project/`, không nằm trực tiếp ở root repository.
- Nếu triển khai thực tế, cần kiểm tra lại Firebase Rules và các thông tin cấu hình trước khi public.

## Tác giả

Pham Hao

GitHub: [phamhao1806](https://github.com/phamhao1806)

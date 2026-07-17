# Food_project

`Food_project` là ứng dụng Android đặt món ăn được xây dựng bằng Kotlin. Ứng dụng cho phép người dùng đăng ký, đăng nhập, xem menu, thêm món vào giỏ hàng, thanh toán và theo dõi lịch sử đơn hàng.

## Thông tin project

- Project name: `Food_project`
- Package name: `com.example.food_project`
- Min SDK: 24
- Target SDK: 35
- Compile SDK: 35
- Ngôn ngữ: Kotlin
- Build system: Gradle Kotlin DSL

## Chức năng

- Splash screen và màn hình bắt đầu.
- Đăng ký tài khoản bằng email/mật khẩu.
- Đăng nhập bằng Firebase Authentication.
- Đăng nhập bằng Google Sign-In.
- Hiển thị trang chủ với danh sách món ăn và món phổ biến.
- Xem chi tiết món ăn.
- Tìm kiếm món ăn.
- Thêm, cập nhật và xóa món trong giỏ hàng.
- Thanh toán đơn hàng.
- Lưu và hiển thị lịch sử đơn hàng.
- Mua lại món từ lịch sử đơn hàng.
- Cập nhật thông tin người dùng.
- Đổi mật khẩu.
- Hiển thị thông báo bằng bottom sheet.

## Kiến trúc thư mục

```text
app/src/main/java/com/example/food_project/
├── activity/              # Các màn hình chính của ứng dụng
├── adapter/               # RecyclerView adapters
├── Api/                   # Retrofit API service và client
├── database/              # Room database, DAO, repository
├── Fragment/              # Các fragment trong màn hình chính
├── model/                 # Data models
├── utils/                 # Tiện ích dùng chung
└── viewmodel/             # ViewModel và ViewModelFactory
```

## Công nghệ và thư viện

- AndroidX Core KTX, AppCompat, Activity, ConstraintLayout
- Material Components
- Firebase Authentication
- Firebase Realtime Database
- Google Play Services Auth
- Android Credentials API
- Room Runtime, Room KTX, Room Compiler
- Retrofit 2
- Gson Converter
- Glide
- CircleImageView
- ImageSlideshow
- Kotlin Coroutines
- ViewBinding

## Cấu hình Firebase

Project sử dụng Firebase cho xác thực và đồng bộ dữ liệu. File cấu hình cần nằm tại:

```text
app/google-services.json
```

Khi tạo Firebase project mới, cần bật các dịch vụ sau:

- Authentication bằng Email/Password.
- Authentication bằng Google.
- Realtime Database.

## Cách chạy

1. Mở thư mục `Food_project/` trong Android Studio.
2. Chờ Android Studio sync Gradle.
3. Kiểm tra `app/google-services.json` đã tồn tại.
4. Chọn emulator hoặc thiết bị thật.
5. Bấm `Run` để chạy ứng dụng.

## Lệnh build

Có thể build bằng Gradle wrapper:

```bash
./gradlew assembleDebug
```

## Ghi chú phát triển

- Dữ liệu giỏ hàng và lịch sử đơn hàng được quản lý bằng Room Database ở local.
- Thông tin tài khoản và dữ liệu đồng bộ được kết nối với Firebase.
- Project đang dùng ViewBinding để thao tác với giao diện XML an toàn hơn.

## Tác giả

Pham Hao

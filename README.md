ToolRevert
This repository contains a Spring Boot tool for converting encrypted files back to their original format. The tool processes a folder containing various encrypted files, restoring each file to its original state based on the user's directory structure.

Features ✨
File Conversion: Converts encrypted files to their original formats.
User-Specific Directories: Organizes converted files into user-specific directories based on the username in the root folder's directory tree.
Technology: Built with Spring Boot and utilizes the Breadth-First Search (BFS) algorithm for efficient file processing.
Usage
Clone the repository: git clone https://github.com/MHuyHoat/ToolRevert.git
Navigate to the project directory: cd ToolRevert
Build the project: ./mvnw clean install
Run the application: java -jar target/ToolRevert-0.0.1-SNAPSHOT.jar
Place encrypted files in the designated input folder.
The tool will automatically process and output the converted files to their respective user directories.
Tiếng Việt:

ToolRevert
Repository này chứa một công cụ Spring Boot để chuyển đổi các file mã hóa trở lại định dạng gốc của chúng. Công cụ xử lý một thư mục chứa nhiều file mã hóa khác nhau, khôi phục mỗi file về trạng thái ban đầu dựa trên cấu trúc thư mục của người dùng.

Tính năng ✨
Chuyển đổi file: Chuyển đổi các file mã hóa về định dạng gốc.
Thư mục riêng của người dùng: Tổ chức các file đã chuyển đổi vào các thư mục riêng của người dùng dựa trên tên người dùng trong sơ đồ cây thư mục gốc.
Công nghệ: Được xây dựng bằng Spring Boot và sử dụng thuật toán tìm kiếm theo chiều rộng (BFS) để xử lý file hiệu quả.
Hướng dẫn sử dụng
Clone repository: git clone https://github.com/MHuyHoat/ToolRevert.git
Di chuyển đến thư mục dự án: cd ToolRevert
Build dự án: ./mvnw clean install
Chạy ứng dụng: java -jar target/ToolRevert-0.0.1-SNAPSHOT.jar
Đặt các file mã hóa vào thư mục đầu vào được chỉ định.
Công cụ sẽ tự động xử lý và xuất các file đã chuyển đổi vào thư mục người dùng tương ứng.
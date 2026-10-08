import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class test1 {
    public static void main(String[] args) {
        String serverHost = "36.50.135.242";
        int serverPort = 2210;

        String studentCode = "B23DCCN351";
        String qCode = "rSCYgYUc";

        try (Socket socket = new Socket(serverHost, serverPort)) {
            System.out.println("Kết nối thành công!");

            // Khởi tạo luồng GZIP (vẫn giữ syncFlush = true)
            GZIPOutputStream gos = new GZIPOutputStream(socket.getOutputStream(), true);
            GZIPInputStream gis = new GZIPInputStream(socket.getInputStream());

            // Chỉ dùng BufferedReader để đọc cho tiện
            BufferedReader in = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));

            // =====================================
            // 1. GỬI REQUEST (Đã sửa lỗi newLine)
            // =====================================
            // Cộng trực tiếp \n vào chuỗi
            String request = studentCode + ";" + qCode + "\n";

            // Ghi trực tiếp mảng byte vào luồng GZIP (an toàn tuyệt đối)
            gos.write(request.getBytes(StandardCharsets.UTF_8));
            gos.flush(); // syncFlush đẩy dữ liệu đi ngay

            // =====================================
            // 2. NHẬN VÀ XỬ LÝ DỮ LIỆU
            // =====================================
            String receivedStr = in.readLine();

            if (receivedStr != null) {
                System.out.println("Nhận từ server: " + receivedStr);

                // c. Đảo ngược chuỗi
                String reversedStr = new StringBuilder(receivedStr).reverse().toString();

                // Mã hóa Base64 chuỗi đảo ngược
                String base64Str = Base64.getEncoder().encodeToString(reversedStr.getBytes(StandardCharsets.UTF_8));

                // Tạo chuỗi kết quả và CỘNG THÊM \n
                String result = reversedStr + "|" + base64Str + "\n";
                System.out.println("Gửi kết quả: " + result.trim());

                // Gửi kết quả lên server
                gos.write(result.getBytes(StandardCharsets.UTF_8));
                gos.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
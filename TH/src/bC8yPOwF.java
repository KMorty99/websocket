import java.io.EOFException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bC8yPOwF {
    public static void main(String[] args) {
        String serverHost = "36.50.135.242";
        int serverPort = 2211;

        // TODO: Thay mã sinh viên và mã câu hỏi của bạn vào đây
        String studentCode = "B23DCCN351";
        String qCode = "bC8yPOwF"; // Thay mã câu hỏi thực tế của bạn

        try (SocketChannel channel = SocketChannel.open()) {
            // Kết nối tới server
            channel.connect(new InetSocketAddress(serverHost, serverPort));
            System.out.println("Kết nối thành công!");

            // a. Gửi mã sinh viên và mã câu hỏi
            String request = studentCode + ";" + qCode;
            writeFrame(channel, request);

            // b. Nhận đúng 2 frame liên tiếp và nối lại thành chuỗi JSON
            StringBuilder jsonBuilder = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                String payload = readFrame(channel);
                jsonBuilder.append(payload);
            }
            String fullJson = jsonBuilder.toString();
            System.out.println("Chuỗi JSON gốc: " + fullJson);

// ========================================================
// c. Trích xuất dữ liệu bằng phương pháp Xử lý chuỗi (Split)
// ========================================================

// Khởi tạo các biến chứa kết quả
            String event = "";
            String user = "";
            String okRaw = "false";

// Bước 1: Dọn dẹp chuỗi
// Ví dụ chuỗi gốc: {"event":"login","user":"admin","ok":true}
// Xóa các dấu ngoặc nhọn {} và dấu ngoặc kép "
            String cleanJson = fullJson.replace("{", "")
                    .replace("}", "")
                    .replace("\"", "")
                    .trim();
// Kết quả sau dọn dẹp: event:login,user:admin,ok:true

// Bước 2: Cắt chuỗi thành các cặp key-value dựa vào dấu phẩy
            String[] pairs = cleanJson.split(",");

// Bước 3: Duyệt qua từng cặp để lấy dữ liệu
            for (String pair : pairs) {
                // Cắt tiếp bằng dấu hai chấm (giới hạn cắt thành 2 phần để tránh lỗi nếu value có dấu :)
                String[] keyValue = pair.split(":", 2);

                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    // Gán giá trị vào đúng biến
                    if (key.equals("event")) {
                        event = value;
                    } else if (key.equals("user")) {
                        user = value;
                    } else if (key.equals("ok")) {
                        okRaw = value;
                    }
                }
            }

// Chuyển đổi boolean: true -> 1, false -> 0
            String okFormat = "true".equalsIgnoreCase(okRaw) ? "1" : "0";

// Tạo chuỗi kết quả và gửi đi
            String result = "event=" + event + ";user=" + user + ";ok=" + okFormat;
            System.out.println("Chuỗi kết quả gửi đi: " + result);

            writeFrame(channel, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // =========================================================================
    // 3 HÀM HỖ TRỢ GIAO THỨC FRAME (GIỮ NGUYÊN TỪ BÀI TRƯỚC)
    // =========================================================================

    private static void writeFrame(SocketChannel channel, String data) throws Exception {
        byte[] payload = data.getBytes(StandardCharsets.UTF_8);
        int length = payload.length;

        ByteBuffer buffer = ByteBuffer.allocate(4 + length);
        buffer.putInt(length);
        buffer.put(payload);
        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    private static String readFrame(SocketChannel channel) throws Exception {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        readFully(channel, lengthBuffer);
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        ByteBuffer payloadBuffer = ByteBuffer.allocate(length);
        readFully(channel, payloadBuffer);
        payloadBuffer.flip();

        return StandardCharsets.UTF_8.decode(payloadBuffer).toString();
    }

    private static void readFully(SocketChannel channel, ByteBuffer buffer) throws Exception {
        while (buffer.hasRemaining()) {
            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                throw new EOFException("Server đã ngắt kết nối đột ngột");
            }
        }
    }
}

/*
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng studentCode;qCode.
    Ví dụ: B15DCCN999;EC4F899B
b. Nhận một chuỗi ngẫu nhiên là danh sách các một số tên miền từ server
    Ví dụ: giHgWHwkLf0Rd0.io, I7jpjuRw13D.io, wXf6GP3KP.vn, MdpIzhxDVtTFTF.edu, TUHuMfn25chmw.vn, HHjE9.com, 4hJld2m2yiweto.vn, y2L4SQwH.vn, s2aUrZGdzS.com, 4hXfJe9giAA.edu
c. Tìm kiếm các tên miền .edu và gửi lên server
    Ví dụ: MdpIzhxDVtTFTF.edu, 4hXfJe9giAA.edu
d. Đóng kết nối và kết thúc chương trình.
 */


import java.io.*;
import java.net.Socket;
public class BasicStream1 {
    public static void main(String[] args) throws IOException {
        String severHost = "36.50.135.242";
        int severPort = 2208;

        String studentCode = "B23DCCN351";
        String qCode = "RLSSbQSN";
        try {
            Socket socket = new Socket(severHost, severPort);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            if (socket != null)
                System.out.println("Connect Successfull");
            System.out.println(socket);
            out.write(studentCode + ";" + qCode);
            out.newLine();
            out.flush();
            
            String data = in.readLine();
            
            System.out.println("Received from sever");
            System.out.println(data);
            
            String[] domains = data.split(",\s*");
            StringBuilder result = new StringBuilder();
            for (String domain : domains) {
                if (domain.endsWith(".edu")) {
                    if (result.length() > 0) {
                        result.append(", ");
                    }
                    result.append(domain);
                }
            }
            out.write(result.toString());
            out.newLine();
            out.flush();
            System.out.println("Sent to sever:");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

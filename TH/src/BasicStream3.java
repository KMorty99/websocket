/*
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
Ví dụ: B16DCCN999;2B3A6510
b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ,.
Ví dụ: 1,3,9,19,33,20
c. Tìm và gửi lên server giá trị lớn thứ hai cùng vị trí xuất hiện của nó trong chuỗi.Ví dụ: 20,5
d. Đóng kết nối và kết thúc chương trình.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BasicStream3 {
    public static void main(String[] args) throws IOException {
        String SeverHost ="36.50.135.242";
        int SeverPort = 2206;

        String StudentCode="B23DCCN351";
        String qCode="WaWVdPGc";
        try{
            Socket socket = new Socket(SeverHost, SeverPort);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String request = StudentCode +";" + qCode+"\n";
            out.write(request.getBytes());
            out.flush();

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            if(bytesRead != -1){
                String response = new String(buffer, 0, bytesRead);
                System.out.println("received response from server: ");
                System.out.println(response);

                String[] parts = response.split(",");
                int[] numbers = new int[parts.length];
                int max =Integer.MIN_VALUE;
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = Integer.parseInt(parts[i]);
                    if(numbers[i] > max){
                        max = numbers[i];
                    }
                }

                int SCmax=Integer.MIN_VALUE;
                int SCmaxIndex=0;
                for (int i = 0; i < numbers.length; i++) {
                    if(numbers[i]>SCmax &&  numbers[i]<max){
                        SCmax=numbers[i];
                        SCmaxIndex=i;
                    }
                }

                String result =SCmax+","+SCmaxIndex;
                System.out.println("sent to sever:");
                System.out.println(result);
                out.write(result.getBytes());
                out.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

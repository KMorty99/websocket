/*
Một chương trình máy chủ cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s), yêu cầu xây dựng chương trình (tạm gọi là client) thực hiện kết nối tới server tại cổng 2207, sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
Ví dụ: B15DCCN999;1D25ED92
b. Nhận lần lượt hai số nguyên a và b từ server
c. Thực hiện tính toán tổng, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server
d. Đóng kết nối và kết thúc
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DataStream {
    static void main(String[] args) throws IOException {
        String svH= "36.50.135.242";
        int svP=2207;

        String stCode="B23DCCN351";
        String qCode= "N5SfKfYM";
        try{
            Socket socket= new Socket(svH,svP);
            DataInputStream dis= new DataInputStream(socket.getInputStream());
            DataOutputStream dos= new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(stCode +";"+qCode);
            dos.flush();

            int a= dis.readInt();
            int b= dis.readInt();

            String result =(a+b) +" "+ a*b;
            System.out.println(result);
            dos.writeInt(a+b);
            dos.writeInt(a*b);
            dos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

/*
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2210
(thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở
trên sử dụng GZIPInputStream/GZIPOutputStream để trao đổi thông tin (mỗi thông điệp là một dòng text UTF-8 kết thúc bằng
‘\n’ và toàn bộ dữ liệu truyền/nhận đều được nén GZIP), theo thứ tự sau:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
Ví dụ: B16DCCN999;GZLEN01
b. Nhận dữ liệu từ server, sau khi giải nén là một chuỗi văn bản.
c. Thực hiện đảo ngược chuỗi nhận được, sau đó mã hóa chuỗi đã đảo ngược sang định dạng Base64. Gửi kết quả lên server theo khuôn dạng: <reversed_string>|<base64_string> Ví dụ: Nhận 123 → Đảo ngược thành 321 → Base64 của 321 là MzIx → Gửi lên: 321|MzIx
d. Đóng kết nối và kết thúc chương trình.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPStream2 {
    static void main(String[] args)throws Exception {
        String svH="36.50.135.242";
        int svP=2210;
        String stCode="B23DCCN351";
        String qCode="rSCYgYUc";

        try{
            Socket socket =new Socket(svH,svP);
            System.out.println(socket);

            GZIPOutputStream out= new GZIPOutputStream(socket.getOutputStream(), true);

            String request=stCode+";"+qCode+"\n";
            out.write(request.getBytes(StandardCharsets.UTF_8));
            out.flush();

            GZIPInputStream gis = new GZIPInputStream(socket.getInputStream());

            byte[] buf=new byte[1024];
            int bytesRead=gis.read(buf);
            System.out.println(bytesRead);
                String ma= new String(buf,0,bytesRead,StandardCharsets.UTF_8).trim();
                String newMa = new StringBuilder(ma).reverse().toString();
                String bashe64 = Base64.getEncoder().encodeToString(newMa.getBytes(StandardCharsets.UTF_8));

                String result = newMa + "|" + bashe64+"\n";
                System.out.println(result);
                out.write(result.getBytes(StandardCharsets.UTF_8));
                out.finish();


        }catch(Exception e){
            e.printStackTrace();
        }

    }
}

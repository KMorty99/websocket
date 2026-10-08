/*
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2210 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng GZIPInputStream/GZIPOutputStream để trao đổi thông tin (mỗi thông điệp là một dòng text UTF-8 kết thúc bằng ‘\n’ và toàn bộ dữ liệu truyền/nhận đều được nén GZIP), theo thứ tự sau:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
Ví dụ: B16DCCN999;GZCRC_LEN03
b. Nhận dữ liệu từ server, sau khi giải nén là một chuỗi văn bản.
c. Sắp xếp các ký tự trong chuỗi nhận được theo thứ tự từ điển (tăng dần theo mã ASCII). Sau đó gửi chuỗi kết quả đã sắp xếp lên server.
Ví dụ: Nhận về dbca1 thì gửi lên server 1abcd
d. Đóng kết nối và kết thúc chương trình.
 */
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPStream {
    static void main(String[] args)throws Exception {
        String svH="36.50.135.242";
        int svP= 2210;
        String stcode="B23DCCN351";
        String qcode="l7nYFelb";
        try {
            Socket socket=new Socket(svH,svP);
            System.out.println(socket);

            GZIPOutputStream out=new GZIPOutputStream(socket.getOutputStream(),true);
            String requete=stcode+";"+qcode+"\n";
            out.write(requete.getBytes(StandardCharsets.UTF_8));
            out.flush();

            GZIPInputStream in=new GZIPInputStream(socket.getInputStream());

            byte[] buffer=new byte[1024];
            int getbytes=in.read(buffer);
            System.out.println(getbytes);

            String ma= new String(buffer,0,getbytes,StandardCharsets.UTF_8).trim();
            char[] chars= ma.toCharArray();
            Arrays.sort(chars);
            String newma=new String(chars);

            System.out.println(newma);

            out.write(newma.getBytes(StandardCharsets.UTF_8));
            out.finish();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

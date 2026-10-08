/*
Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s.
Ví dụ: với s = 3 thì ký tự A sẽ được thay thế bằng ký tự D
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình client tương tác với server trên, sử dụng các luồng byte (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
Ví dụ: B15DCCN999;D68C93F7
b. Nhận lần lượt chuỗi đã bị mã hóa caesar và giá trị dịch chuyển s nguyên
c. Thực hiện giải mã ra thông điệp ban đầu và gửi lên Server
d. Đóng kết nối và kết thúc chương trình.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DataStream2 {
    public static void main(String[] args)throws IOException {
        String svH="36.50.135.242";
        int svP=2207;

        String stCode="B23DCCN351";
        String cCode="KHTdHMWV";
        try{
            Socket socket = new Socket(svH,svP);
            System.out.println(socket);
            DataInputStream dis=new DataInputStream(socket.getInputStream());
            DataOutputStream dos=new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(stCode+";"+cCode);
            dos.flush();

            char[] ma=dis.readUTF().toCharArray();
            int s= dis.readInt();
            StringBuilder result=new StringBuilder();
            for(char c : ma ){
                if(c>='a' && c<='z'){
                     c=(char)((c-'a'-s+26)%26+'a');
                }
                else if(c>='A' && c<='Z'){
                    c=(char)((c-'A'-s+26)%26+'A');
                }
                result.append(c);
            }
            System.out.println(result);
            dos.writeUTF(result.toString());
            dos.flush();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

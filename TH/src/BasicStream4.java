/*
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng studentCode;qCode.
Ví dụ: B15DCCN999;BAA62945
b. Nhận một chuỗi ngẫu nhiên từ server
Ví dụ: dgUOo ch2k22ldsOo
c. Liệt kê các ký tự (là chữ hoặc số) xuất hiện nhiều hơn một lần trong chuỗi và số lần xuất hiện của chúng và gửi lên server
Ví dụ: d:2,O:2,o:2,2:3,
d. Đóng kết nối và kết thúc chương trình.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class BasicStream4 {
    static void main(String[] args)throws Exception {
        String svHost= "36.50.135.242";
        int svPort = 2208;

        String stCode= "B23DCCN351";
        String qCode= "xkAJ8xhL";
        try{
            Socket socket = new Socket(svHost, svPort);
            BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("connect successfull:");
            System.out.println(socket);

            out.write(stCode+";"+qCode);
            out.newLine();
            out.flush();

            String data=in.readLine();
            System.out.println("from server:");
            System.out.println(data);
            if(data!=null && !data.isEmpty()){
                LinkedHashMap<Character,Integer> datas=new LinkedHashMap();
                for(char c : data.toCharArray()){
                    if(Character.isLetterOrDigit(c)){
                        datas.put(c,datas.getOrDefault(c,0)+1);
                    }
                }
                StringBuilder result=new StringBuilder();
                for(Map.Entry<Character,Integer> entry : datas.entrySet()){
                    if(entry.getValue()>1){
                        result.append(entry.getKey())
                                .append(":")
                                .append(entry.getValue())
                                .append(",");
                    }
                }
                System.out.println("sent to sever:");
                System.out.println(result.toString() );
                out.write(result.toString());
                out.newLine();
                out.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

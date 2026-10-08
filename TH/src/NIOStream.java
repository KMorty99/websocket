/*
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2211 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng SocketChannel và ByteBuffer để trao đổi thông tin theo giao thức frame: 4 byte độ dài (int32) + payload (UTF-8).
Lưu ý: server & client đều phải đọc đủ dữ liệu bằng vòng lặp (readFully) do server luôn chia nhỏ dữ liệu khi gửi. Trình tự trao đổi như sau:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
Ví dụ: B16DCCN999;fkdRJYuX
b. Nhận dữ liệu từ server gồm đúng 3 frame liên tiếp. Payload của mỗi frame là một phần của cùng một HTTP request, client phải nối 3 payload theo đúng thứ tự để thu được chuỗi HTTP request hoàn chỉnh (các dòng phân tách bởi \r\n và kết thúc bằng \r\n\r\n).
c. Từ chuỗi HTTP request hoàn chỉnh, trích xuất và gửi lại lên server theo định dạng METHOD;PATH;HOST trong đó PATH luôn bao gồm query-string.
d. Đóng kết nối và kết thúc chương trình.
 */
import java.io.EOFException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NIOStream {
    static void main(String[] args)throws Exception {
        String svH="36.50.135.242";
        int svP= 2211;
        String stcode="B23DCCN351";
        String qcode="arp9ZKow";

        try {
            SocketChannel channel=SocketChannel.open();
            channel.connect(new InetSocketAddress(svH,svP));

            String request=stcode+";"+qcode;
            witeFrame(channel,request);

            StringBuilder httprequest=new StringBuilder();
            for (int i = 0; i < 3; i++) {
                String payload= readFrame(channel);
                httprequest.append(payload);
            }

            String fullRequest=httprequest.toString();
            System.out.println(fullRequest);

            String method="";
            String path="";
            String host="";

            String[] lines=fullRequest.split("\r\n");
            if(lines.length>0) {
                String requestline = lines[0];
                String[] requestlineArray = requestline.split(" ");
                if (requestlineArray.length >= 2) {
                    method = requestlineArray[0].trim();
                    path = requestlineArray[1].trim();
                }

                for (String line : lines) {
                    if (line.toLowerCase().startsWith("host:")) {
                        host = line.substring(5).trim();
                        break;
                    }
                }
                String result = method + ";" + path + ";" + host;
                witeFrame(channel, result);
                System.out.println(result);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void witeFrame(SocketChannel channel, String data)throws Exception{
        byte[] buf=data.getBytes(StandardCharsets.UTF_8);
        int length=buf.length;

        ByteBuffer buffer = ByteBuffer.allocate(4+length);
        buffer.putInt(length);
        buffer.put(buf);
        buffer.flip();

        while(buffer.hasRemaining()){
            channel.write(buffer);
        }
    }
    public static String readFrame(SocketChannel channel)throws Exception{
        ByteBuffer buffer=ByteBuffer.allocate(4);
        readFully(channel,buffer);
        buffer.flip();
        int length=buffer.getInt();

        ByteBuffer payload=ByteBuffer.allocate(length);
        readFully(channel,payload);
        payload.flip();

        return StandardCharsets.UTF_8.decode(payload).toString();
    }
    public static void readFully(SocketChannel channel, java.nio.ByteBuffer buffer)throws Exception{
        while(buffer.hasRemaining()){
            int bytesRead=channel.read(buffer);
            if(bytesRead==-1){
                throw new EOFException("sever ngat ket noi");
            }
        }
    }
}

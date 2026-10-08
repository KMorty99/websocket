import java.io.EOFException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class arp9ZKow {
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

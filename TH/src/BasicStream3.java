import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WaWVdPGc {
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

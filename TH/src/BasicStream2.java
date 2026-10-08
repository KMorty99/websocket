import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class YWNm1euS {
    public static void main(String[] args) throws Exception {
        String severHost = "36.50.135.242";
        int severPort = 2208;

        String studentCode = "B23DCCN351";
        String qCode = "YWNm1euS";

        try {
            Socket socket = new Socket(severHost, severPort);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String request = studentCode + ";" + qCode + "\n";
            out.write(request.getBytes());
            out.flush();

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            if (bytesRead != -1) {
                String received = new String(buffer, 0, bytesRead).trim();
                System.out.println("Received from server: ");
                System.out.println(received);

                String[] parts = received.split(",");
                int[] numbers = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    numbers[i] = Integer.parseInt(parts[i].trim());
                }

                Arrays.sort(numbers);

                int minDis = Integer.MAX_VALUE;
                int num1 = 0;
                int num2 = 0;
                for (int i = 1; i < numbers.length; i++) {
                    int dis = numbers[i] - numbers[i - 1];
                    if (dis <= minDis) {
                        minDis = dis;
                        num1 = numbers[i - 1];
                        num2 = numbers[i];
                    }
                }

                String result = minDis + "," + num1 + "," + num2;
                System.out.println("sent to sever:");
                System.out.println(result);

                out.write(result.getBytes());
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

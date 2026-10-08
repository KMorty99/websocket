import java.io.*;
import java.net.Socket;

public class RLSSbQSN {
    public static void main(String[] args) throws IOException {

        String severHost = "36.50.135.242";

        int severPort = 2208;

        String studentCode = "B23DCCN351";

        String qCode = "RLSSbQSN";

        try {

            Socket socket = new Socket(severHost, severPort);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            if (socket != null)
                System.out.println("Connect Successfull");

            System.out.println(socket);

            out.write(studentCode + ";" + qCode);

            out.newLine();

            out.flush();

            String data = in.readLine();

            System.out.println("Received from sever");

            System.out.println(data);

            String[] domains = data.split(",\s*");

            StringBuilder result = new StringBuilder();

            for (String domain : domains) {

                if (domain.endsWith(".edu")) {

                    if (result.length() > 0) {

                        result.append(", ");

                    }

                    result.append(domain);

                }

            }

            out.write(result.toString());

            out.newLine();

            out.flush();

            System.out.println("Sent to sever:");

            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

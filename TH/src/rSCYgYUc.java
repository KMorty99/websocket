import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class rSCYgYUc {
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

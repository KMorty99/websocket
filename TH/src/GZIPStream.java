import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class l7nYFelb {
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

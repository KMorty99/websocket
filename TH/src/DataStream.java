import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class N5SfKfYM {
    static void main(String[] args) throws IOException {
        String svH= "36.50.135.242";
        int svP=2207;

        String stCode="B23DCCN351";
        String qCode= "N5SfKfYM";
        try{
            Socket socket= new Socket(svH,svP);
            DataInputStream dis= new DataInputStream(socket.getInputStream());
            DataOutputStream dos= new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(stCode +";"+qCode);
            dos.flush();

            int a= dis.readInt();
            int b= dis.readInt();

            String result =(a+b) +" "+ a*b;
            System.out.println(result);
            dos.writeInt(a+b);
            dos.writeInt(a*b);
            dos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

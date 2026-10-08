import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class KHTdHMWV {
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

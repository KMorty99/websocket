import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class xkAJ8xhL {
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

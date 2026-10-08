import TCP.Laptop;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class xfMbfnFt {
    static void main(String[] args)throws Exception {
        String svH= "36.50.135.242";
        int svP = 2209;

        String stCode="B23DCCN351";
        String qCode="xfMbfnFt";
        try {
            Socket socket = new Socket(svH,svP);
            System.out.println(socket);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(stCode+";"+qCode);
            out.flush();

            Laptop laptop =(Laptop) in.readObject();
            System.out.println(laptop);

            String old= laptop.getName();
            String[] words=old.split(" ");
            if(words.length >=2)
                {
                String temp=words[0];
                words[0]=words[words.length-1];
                words[words.length-1]=temp;

                String newname = String.join(" ",words);
                laptop.setName(newname);
                }
            int oldQ=laptop.getQuantity();
            String newQ= new StringBuilder(String.valueOf(oldQ)).reverse().toString();
            int newqq= Integer.parseInt(newQ);
            laptop.setQuantity(newqq);
            out.writeObject(laptop);
            out.flush();
            System.out.println("sent to sever:");
            System.out.println(laptop);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

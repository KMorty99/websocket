import TCP.Customer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class fPfzQqny {
    static void main(String[] args) throws Exception {
        String svH = "36.50.135.242";
        int svP = 2209;

        String stCode = "B23DCCN351";
        String qCode = "fPfzQqny";

        try {
            Socket socket = new Socket(svH, svP);
            System.out.println(socket);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(stCode + ";" + qCode);
            out.flush();

            Customer customer = (Customer) in.readObject();
            System.out.println(customer);

            String oldName = customer.getName().trim().replaceAll("\\s+", " ");
            String[] words = oldName.split(" ");
            String lastName = words[words.length - 1];

            StringBuilder userName = new StringBuilder();
            for (int i = 0; i < words.length - 1; i++) {
                userName.append(words[i].substring(0, 1).toLowerCase());
            }
            userName.append(lastName.toLowerCase());
            customer.setUserName(userName.toString());

            StringBuilder newName = new StringBuilder();
            newName.append(lastName.toUpperCase()).append(", ");
            for (int i = 0; i < words.length - 1; i++) {
                newName.append(words[i].substring(0, 1).toUpperCase())
                        .append(words[i].substring(1).toLowerCase())
                        .append(" ");
            }
            customer.setName(newName.toString().trim());

            String[] dates = customer.getDayOfBirth().split("-");
            String temp = dates[0];
            dates[0] = dates[1];
            dates[1] = temp;
            customer.setDayOfBirth(String.join("/", dates));

            System.out.println(customer);
            out.writeObject(customer);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

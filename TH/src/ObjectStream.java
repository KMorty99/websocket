/*
Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
    a. Tên khách hàng cần được chuẩn hóa theo định dạng mới.
        Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
    b. Ngày sinh của khách hàng hiện đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy.
        Ví dụ: 10-11-2012 -> 11/10/2012
    c. Tài khoản khách hàng là các chữ cái in thường được sinh tự động từ họ tên khách hàng.
        Ví dụ: nguyen van hai duong -> nvhduong
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng khách hàng và chuẩn hóa. Cụ thể:
    a. Đối tượng trao đổi là thể hiện của lớp Customer được mô tả như sau
Tên đầy đủ của lớp: TCP.Customer
Các thuộc tính: id int, code String, name String, dayOfBirth String, userName String
Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
Trường dữ liệu: private static final long serialVersionUID = 20170711L;
    b. Tương tác với server theo kịch bản dưới đây:
    1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng studentCode;qCode.
        Ví dụ: B15DCCN999;F2DA54F3
    2) Nhận một đối tượng là thể hiện của lớp Customer từ server với các thông tin đã được thiết lập
    3) Thay đổi định dạng theo các yêu cầu ở trên và gán vào các thuộc tính tương ứng.
Gửi đối tượng đã được sửa đổi lên server
    4) Đóng socket và kết thúc chương trình.
 */
import TCP.Customer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectStream {
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

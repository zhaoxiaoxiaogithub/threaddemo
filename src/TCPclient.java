import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
           socket= new Socket("127.0.0.1", 8888);
            //想服务器发送数据
            Scanner sc = new Scanner(System.in);
            System.out.println("客户端说：");
            String st = sc.next();
            OutputStream os = socket.getOutputStream();
            DataOutputStream bos = new DataOutputStream(os);
            bos.writeUTF(st);
            bos.flush();
            //接受服务器数据


            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println(dis.readUTF());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

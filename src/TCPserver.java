import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPserver {
    public static void main(String[] args) {
        ServerSocket so=null;
        try {
            so=new ServerSocket(8888);
            //接受客户端发送的消息
            //建立连接
           Socket sc= so.accept();
            DataInputStream di=new DataInputStream(sc.getInputStream());
            System.out.println(di.readUTF());

            //向客户端输出数据
            Scanner sca=new Scanner(System.in);
            System.out.println("服务端说：");
            DataOutputStream dataOutputStream=new DataOutputStream(sc.getOutputStream());
            dataOutputStream.writeUTF(sca.next());

        }catch (Exception e){

        }


    }
}

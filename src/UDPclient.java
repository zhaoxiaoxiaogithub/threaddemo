import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPclient {
    public static void main(String[] args) {
        byte[] bt=(new String("111111111")).getBytes();
        DatagramPacket datagramPacket=null;
        DatagramSocket datagramSocket=null;
        try {
            //将要发送的数据打包
            datagramPacket=new DatagramPacket(bt,bt.length,new InetSocketAddress("localhost",8000));
            datagramSocket=new DatagramSocket(8800);//打开一个udp端口，发送数据包
            datagramSocket.send(datagramPacket);
            datagramSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

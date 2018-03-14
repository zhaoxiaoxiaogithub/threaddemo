import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPserver {
    public static void main(String[] args) {
        byte [] buffer=new byte[1024];
        DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
        DatagramSocket socket=null;
        try{
            socket=new DatagramSocket(8000);

            while (true){
                //接收数据包
                socket.receive(dp);
                System.out.println(new String(buffer,0,dp.getLength()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

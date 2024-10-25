package suma;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class S {
    private static final int PORT = 12345;
    private static Map<String, String> arpTable = new HashMap<>();
    private static Map<String, String> rarpTable = new HashMap<>();

    public static void main(String[] args) {
        try {
            // Initialize ARP and RARP tables
            arpTable.put("192.168.1.2", "00:11:22:33:44:55");       
            arpTable.put("192.168.1.3", "66:77:88:99:AA:BB");
            
            rarpTable.put("00:11:22:33:44:55", "192.168.1.2");
            rarpTable.put("66:77:88:99:AA:BB", "192.168.1.3");

            DatagramSocket socket = new DatagramSocket(PORT);

            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Server is running...");

            while (true) {
                socket.receive(packet);
                String request = new String(packet.getData(), 0, packet.getLength()).trim();
                String response;

                // Check if the request is an ARP or RARP request
                if (request.startsWith("IP:")) {
                    String ipAddress = request.substring(3).trim(); // Remove "IP:" prefix
                    response = arpTable.getOrDefault(ipAddress, "Unknown IP");
                } else if (request.startsWith("MAC:")) {
                    String macAddress = request.substring(4).trim(); // Remove "MAC:" prefix
                    response = rarpTable.getOrDefault(macAddress, "Unknown MAC");
                } else {
                    response = "Invalid request";
                }

                DatagramPacket responsePacket = new DatagramPacket(response.getBytes(), response.length(), packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

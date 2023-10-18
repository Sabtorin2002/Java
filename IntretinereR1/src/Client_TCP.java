import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client_TCP {
    public static void main(String[] args) {

        final int PORT=7070;
        final String serverName="127.0.0.1";
        try(Socket socket=new Socket(serverName,PORT))
        {
            System.out.println("Client conectat");

            BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

            System.out.println("Introduceti numarul apartamentului:");
            String nrApartamentEX5=new Scanner(System.in).nextLine();
            out.println(nrApartamentEX5);

            //primire lista
            String apartamentPrimit=in.readLine();
            System.out.println("Raspuns: "+ apartamentPrimit);
        }catch (UnknownHostException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

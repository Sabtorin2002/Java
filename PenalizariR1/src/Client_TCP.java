import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client_TCP {
    public static void main(String[] args) {
        final int PORT=7070;
        final String serverName="127.0.0.1";

        try(Socket socket= new Socket(serverName,PORT))
        {
            System.out.println("S-a conectat clientul");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

            System.out.println("Introduceti numarul apartamentului: ");
            String nrApartamentEX5=new Scanner(System.in).nextLine();
            out.println(nrApartamentEX5);

            Double vEX5=Double.parseDouble(in.readLine());
            System.out.println("Raspuns: "+ vEX5);

            //primire lista
            String intretineriPrimite=in.readLine();
            System.out.println("Raspuns: "+intretineriPrimite);


        }catch (UnknownHostException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}

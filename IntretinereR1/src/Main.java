import org.json.JSONArray;
import org.json.JSONObject;

import javax.management.openmbean.InvalidOpenTypeException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static List<Apartament>getAparatemente() {
        List<Apartament>apartamente=new ArrayList<>();
        try(Connection conn= DriverManager.getConnection("jdbc:sqlite:Date/intretinere_apartamente.db"))
        {
            Statement statement= conn.createStatement();
            statement.execute("SELECT * FROM Apartamente");

            ResultSet rs= statement.getResultSet();
            while (rs.next())
            {
                Apartament apartament=new Apartament(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
                apartamente.add(apartament);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return apartamente;
    }
    public static List<Factura> getFacturi()
    {
        List<Factura>facturi=new ArrayList<>();
        try(var fisier= new BufferedReader(new InputStreamReader(new FileInputStream("Date/intretinere_facturi.txt"))))
        {
            facturi=fisier.lines().map(Factura::new).collect(Collectors.toList());
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return facturi;
    }
    public static void main(String[] args) throws InterruptedException {

        List<Factura>facturi=getFacturi();
        for(Factura factura: facturi)
        {
            System.out.println(factura.toString());
        }

        List<Apartament>apartamente=getAparatemente();
        for(Apartament apartament:apartamente)
        {
            System.out.println(apartament.toString());
        }

        System.out.println("---------CERINTA 1-------");
        int nrSuprafata=0;
        int nrPersoane=0;
        for(Factura factura:facturi)
        {
            if(factura.getRepartizare().equalsIgnoreCase("suprafata"))
            {
                nrSuprafata++;
            }
            if(factura.getRepartizare().equalsIgnoreCase("persoane"))
            {
                nrPersoane++;
            }
        }

        System.out.println(nrPersoane+" repartizari pe persoana si "+nrSuprafata+" repartizari pe suprafata" );

        System.out.println("--------CERINTA 2-----------");

        double valoarePersoane=facturi.stream().filter(x-> x.getRepartizare().equalsIgnoreCase("persoane"))
                .collect(Collectors.toList()).stream().mapToDouble(Factura::getValoare).sum();
        double valoareSuprafata=facturi.stream().filter(x->x.getRepartizare().equalsIgnoreCase("suprafata"))
                .collect(Collectors.toList()).stream().mapToDouble(Factura::getValoare).sum();

        System.out.println("P-"+valoarePersoane +" S-"+valoareSuprafata);

        System.out.println("--------CERINTA 3----------");
        int suprafataApartament=0;
        for (Apartament apartament:apartamente)
        {
            suprafataApartament+=apartament.getSuprafata();
        }

        System.out.println("Suprafata totala a apartamentelor este: "+ suprafataApartament);

        System.out.println("------CERINTA 4-----------");
        try(var fisier=new FileWriter("Date/fisier.json"))
        {
            JSONArray array=new JSONArray();
            for(Apartament apartament:apartamente)
            {
                double valoareSuprafataEX4=0;
                double valoarePersoaneEX4=0;
                double valoareTotala=0;
                for(Factura factura:facturi)
                {
                    if(factura.getRepartizare().equalsIgnoreCase("suprafata"))
                    {
                        valoareSuprafataEX4+=factura.getValoare();
                    }
                    if(factura.getRepartizare().equalsIgnoreCase("persoane"))
                    {
                        valoarePersoaneEX4+=factura.getValoare();
                    }
                }
                valoareTotala=valoarePersoaneEX4+valoareSuprafataEX4;

                JSONObject object=new JSONObject();
                object.put("Numar apartament: ",apartament.getNr_apartament());
                object.put("Nume: ",apartament.getNume());
                object.put("Suprafata: ",apartament.getSuprafata());
                object.put("Persoane: ",apartament.getNr_persoane());
                object.put("Valoare totala: ",valoareTotala);

                array.put(object);
            }
            fisier.write(array.toString(4));
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Fisierul s-a creat cu succes");

        System.out.println("----------CERINTA 5------------");
//        final int PORT=7070;
//        try(ServerSocket server=new ServerSocket(PORT))
//        {
//            System.out.println("Se asteapta clienti");
//            Socket socket=server.accept();
//            System.out.println("Client conectat");
//
//            BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
//
//            int nrApartamentEX5=Integer.parseInt(in.readLine());
//            System.out.println("S-a primit apartamentul: "+nrApartamentEX5);
//
//            //trimitere lista
//            List<Apartament>apartamentTrimis=new ArrayList<>();
//            for(Apartament apartament:apartamente)
//            {
//                if(apartament.getNr_apartament()==nrApartamentEX5)
//                {
//                    apartamentTrimis.add(apartament);
//                }
//            }
//            out.println(apartamentTrimis);
//        }catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }

        System.out.println("-----CERINTA 5-------");

        Thread server=new Thread(new Runnable() {
            @Override
            public void run() {
                final int PORT=7070;
                try(ServerSocket server=new ServerSocket(PORT))
                {
                    System.out.println("Se asteapta clienti");
                    Socket socket=server.accept();
                    System.out.println("Client conectat");

                    BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                    int nrApartamentEX5=Integer.parseInt(in.readLine());
                    System.out.println("S-a primit apartamentul: "+nrApartamentEX5);

                    //trimitere lista
                    List<Apartament>apartamentTrimis=new ArrayList<>();
                    for(Apartament apartament:apartamente)
                    {
                        if(apartament.getNr_apartament()==nrApartamentEX5)
                        {
                            apartamentTrimis.add(apartament);
                        }
                    }
                    out.println(apartamentTrimis);
                }catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        server.start();

        Thread client=new Thread(new Runnable() {
            @Override
            public void run() {
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
        });

        client.start();
        server.join();
        client.join();
    }
}
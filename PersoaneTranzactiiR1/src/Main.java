import javax.management.openmbean.InvalidOpenTypeException;
import javax.swing.plaf.nimbus.State;
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

    public static List<Persoana>getPersoane()
    {
        List<Persoana>persoane=new ArrayList<>();
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:Date/bursa.db"))
        {
            Statement statement= conn.createStatement();
            statement.execute("SELECT * FROM Persoane");

            ResultSet rs= statement.getResultSet();
            while(rs.next())
            {
                Persoana persoana= new Persoana(rs.getInt(1),rs.getString(2),rs.getString(3));
                persoane.add(persoana);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return persoane;
    }

    public static List<Tranzactie>getTranzactii()
    {
        List<Tranzactie>tranzactii=new ArrayList<>();
        try(var fisier=new BufferedReader(new InputStreamReader(new FileInputStream("Date/bursa_tranzactii.txt")))) {
            tranzactii = fisier.lines().map(Tranzactie::new).collect(Collectors.toList());
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return tranzactii;
    }
    public static void main(String[] args) throws InterruptedException{

        List<Persoana>persoane=getPersoane();
        for(Persoana persoana : persoane)
        {
            System.out.println(persoana.toString());
        }

        List<Tranzactie>tranzactii=getTranzactii();
        for(Tranzactie tranzactie:tranzactii)
        {
            System.out.println(tranzactie.toString());
        }

        System.out.println("-------CERINTA 1--------");
        int nrNerezidenti=0;
        for(Persoana persoana :persoane)
        {
            if(persoana.getCnp().charAt(0)=='8'||persoana.getCnp().charAt(0)=='9')
            {
                nrNerezidenti++;
            }
        }

        System.out.println("Numar nerezidenti: "+nrNerezidenti);

        System.out.println("-------CERINTA 2----------");
        tranzactii.stream().collect(Collectors.groupingBy(Tranzactie::getSimbol)).forEach((x,y)->
        {
            System.out.println(x+"->"+y.stream().mapToInt(Tranzactie::getCantitate).sum());
        });

        System.out.println("-----CERINTA 3---------");
        try(var fisier=new FileWriter("Date/Simboluri.txt"))
        {
            tranzactii.stream().collect(Collectors.groupingBy(Tranzactie::getSimbol)).forEach((x,y)->
            {
                if(y.size()>0)
                    try{
                        fisier.write(x.toString());
                        fisier.write("\n");
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
            });
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("S-a creat fisierul cu succes");

        System.out.println("--------CERINTA 4-----------");
//        final int PORT=7070;
//        try(ServerSocket server = new ServerSocket(PORT))
//        {
//            System.out.println("Se asteapta clienti");
//            Socket socket=server.accept();
//            System.out.println("Client conectat");
//
//            BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
//
//            String simbol=in.readLine();
//            System.out.println("Simbolul este: "+simbol);
//
//            //trimitere lista
//            List<Tranzactie>tranzactiiTrimise=new ArrayList<>();
//            for(Tranzactie tranzactie:tranzactii)
//            {
//                if(tranzactie.getSimbol().equalsIgnoreCase(simbol))
//                {
//                    tranzactiiTrimise.add(tranzactie);
//                }
//            }
//            out.println(tranzactiiTrimise);
//
//            double valoareCumparare=0;
//            double valoareVanzare=0;
//            double valoare=0;
//            for(Tranzactie tranzactie:tranzactii)
//            {
//                if(tranzactie.getSimbol().equalsIgnoreCase(simbol))
//                {
//                    if(tranzactie.getTip().equalsIgnoreCase("vanzare"))
//                    {
//                        valoareVanzare+=tranzactie.getCantitate()*tranzactie.getPret();
//                    }
//                    if(tranzactie.getTip().equalsIgnoreCase("cumparare"))
//                    {
//                        valoareCumparare+=tranzactie.getCantitate()*tranzactie.getPret();
//                    }
//                }
//            }
//            valoare=valoareVanzare-valoareCumparare;
//            out.println((valoare));
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("-------CERINTA 5---------");
        Thread server=new Thread(new Runnable() {
            @Override
            public void run() {
                final int PORT=7070;
                try(ServerSocket server = new ServerSocket(PORT))
                {
                    System.out.println("Se asteapta clienti");
                    Socket socket=server.accept();
                    System.out.println("Client conectat");

                    BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                    String simbol=in.readLine();
                    System.out.println("Simbolul este: "+simbol);

                    //trimitere lista
                    List<Tranzactie>tranzactiiTrimise=new ArrayList<>();
                    for(Tranzactie tranzactie:tranzactii)
                    {
                        if(tranzactie.getSimbol().equalsIgnoreCase(simbol))
                        {
                            tranzactiiTrimise.add(tranzactie);
                        }
                    }
                    out.println(tranzactiiTrimise);

                    double valoareCumparare=0;
                    double valoareVanzare=0;
                    double valoare=0;
                    for(Tranzactie tranzactie:tranzactii)
                    {
                        if(tranzactie.getSimbol().equalsIgnoreCase(simbol))
                        {
                            if(tranzactie.getTip().equalsIgnoreCase("vanzare"))
                            {
                                valoareVanzare+=tranzactie.getCantitate()*tranzactie.getPret();
                            }
                            if(tranzactie.getTip().equalsIgnoreCase("cumparare"))
                            {
                                valoareCumparare+=tranzactie.getCantitate()*tranzactie.getPret();
                            }
                        }
                    }
                    valoare=valoareVanzare-valoareCumparare;
                    out.println((valoare));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        server.start();

        Thread client=new Thread(new Runnable() {
            @Override
            public void run() {
                final int PORT=7070;
                final String serverName="127.0.0.1";

                try(Socket socket = new Socket(serverName,PORT))
                {
                    System.out.println("Client conectat");

                    BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                    System.out.println("Introduceti simbolul: ");
                    String simbol=new Scanner(System.in).nextLine();
                    out.println(simbol);
                    //primire lista
                    String tranzactiiPrimite=in.readLine();
                    System.out.println("Raspuns: "+tranzactiiPrimite);

                    Double valoare=Double.parseDouble(in.readLine());
                    System.out.println("Raspuns: "+valoare);


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
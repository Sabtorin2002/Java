import org.json.JSONArray;
import org.json.JSONObject;

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

    public static List<Inscriere> getInscrieri()
    {
        List<Inscriere>inscrieri=new ArrayList<>();
        try(var fisier= new BufferedReader(new InputStreamReader(new FileInputStream("Date/inscrieri.txt"))))
        {
            inscrieri=fisier.lines().map(Inscriere::new).collect(Collectors.toList());
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return inscrieri;
    }

    public  static List<Specializare> getSpecializari()
    {
        List<Specializare> specializari= new ArrayList<>();
        try(Connection conn= DriverManager.getConnection("jdbc:sqlite:Date/facultate.db"))
        {
            Statement statement= conn.createStatement();
            statement.execute("SELECT * FROM specializari");

            ResultSet rs= statement.getResultSet();
            while(rs.next())
            {
                Specializare specializare=new Specializare(rs.getInt(1), rs.getString(2),rs.getInt(3));
                specializari.add(specializare);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return specializari;
    }
    public static void main(String[] args) throws InterruptedException {

        List<Specializare> specializari = getSpecializari();
        List<Inscriere> inscrieri = getInscrieri();

        for (Specializare i : specializari) {
            System.out.println(i.toString());
        }
        System.out.println("----------------");
        for (Inscriere i : inscrieri) {
            System.out.println(i.toString());
        }

        System.out.println("---------CERINTA 1----------");
        int numar=0;
        for (Specializare specializare : specializari)
        {
            numar=numar+specializare.getNrLocuri();
        }
        System.out.println("Numarul de locuri disponibile la facultate este: "+ numar);

        int numarFaculate=specializari.stream().mapToInt(Specializare::getNrLocuri).sum();
        System.out.println("Numarul de locuri este "+numarFaculate);

        System.out.println("------------CERINTA 2---------------");
        for(Specializare specializare:specializari)
        {
            int numarInscrieri=0;
            for(Inscriere inscriere:inscrieri)
            {
                if(inscriere.getCod()==specializare.getCod())
                {
                    numarInscrieri++;
                }
            }
            if(specializare.getNrLocuri()-numarInscrieri>=10)
            {
                System.out.println(specializare.getCod()+" "+specializare.getDenumire()+" "
                        +(specializare.getNrLocuri()-numarInscrieri));
            }
        }

        System.out.println("-------------------");
        specializari.forEach(x->
        {
            int nrLocuriOcupate=inscrieri.stream().filter(y-> x.getCod()==y.getCod())
                    .collect(Collectors.toList()).size();
            if(x.getNrLocuri()-nrLocuriOcupate>=10)
            {
                System.out.println(x.getCod()+" "+ x.getDenumire()+" "+(x.getNrLocuri()-nrLocuriOcupate));
            }
        });

        System.out.println("--------Cerinta 3------------");
        try(var fisier= new FileWriter("Date/inscrieri_specializari.json"))
        {
            JSONArray array=new JSONArray();
            specializari.forEach(x->
            {
                int nrLocuriOcupate=inscrieri.stream().filter(y->x.getCod()==y.getCod())
                        .collect(Collectors.toList()).size();
                double medie=inscrieri.stream().filter(y-> x.getCod()==x.getCod())
                        .collect(Collectors.toList()).stream().collect(Collectors.averagingDouble(Inscriere::getNota));

                JSONObject object=new JSONObject();
                object.put("cod_specializare:",x.getCod());
                object.put("denumire:",x.getDenumire());
                object.put("numar_inscrieri:",nrLocuriOcupate);
                object.put("medie:",medie);

                array.put(object);
            });

            fisier.write(array.toString(4));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-------CERINTA 4----------");
//        final int PORT=7070;
//        try(ServerSocket server= new ServerSocket(PORT))
//        {
//            System.out.println("Asteptare clienti");
//            Socket socket=server.accept();
//            System.out.println("Client conectat");
//
//            BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out= new PrintWriter(socket.getOutputStream(),true);
//
//            String denumire=in.readLine();
//            System.out.println("Am primite denumirea: "+ denumire);
//
//            int nrLocuriDisponbile=0;
//            for(Specializare specializare:specializari)
//            {
//                if(specializare.getDenumire().equalsIgnoreCase("cibernetica"))
//                {
//                    int numarLocuriInscriereDenumire=0;
//                    for(Inscriere inscriere:inscrieri)
//                    {
//                        if(specializare.getCod()==inscriere.getCod())
//                        {
//                            numarLocuriInscriereDenumire++;
//                        }
//                    }
//                    nrLocuriDisponbile=specializare.getNrLocuri()-numarLocuriInscriereDenumire;
//                }
//            }
//
//            out.println(nrLocuriDisponbile);
//            System.out.println("Serverul a trimis un mesaj catre client");
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("-----------CERINTA 5--------------");

        Thread serverThread= new Thread(new Runnable() {
            @Override
            public void run() {
                final int PORT=7070;
                try(ServerSocket server= new ServerSocket(PORT))
                {
                    System.out.println("Asteptare clienti");
                    Socket socket=server.accept();
                    System.out.println("Client conectat");

                    BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out= new PrintWriter(socket.getOutputStream(),true);

                    String denumire=in.readLine();
                    System.out.println("Am primit denumirea: "+ denumire);

                    int nrLocuriDisponbile=0;
                    for(Specializare specializare:specializari)
                    {
                        if(specializare.getDenumire().equalsIgnoreCase("cibernetica"))
                        {
                            int numarLocuriInscriereDenumire=0;
                            for(Inscriere inscriere:inscrieri)
                            {
                                if(specializare.getCod()==inscriere.getCod())
                                {
                                    numarLocuriInscriereDenumire++;
                                }
                            }
                            nrLocuriDisponbile=specializare.getNrLocuri()-numarLocuriInscriereDenumire;
                        }
                    }

                    out.println(nrLocuriDisponbile);
                    System.out.println("Serverul a trimis un mesaj catre client");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        serverThread.start();

        Thread clientThread= new Thread(new Runnable() {
            @Override
            public void run() {
                final int PORT=7070;
                final String serverName="127.0.0.1";

                try(Socket socket=new Socket(serverName,PORT))
                {
                    System.out.println("Client conectat la server");

                    BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                    System.out.println("Introduceti denumirea:");
                    String denumire= new Scanner(System.in).nextLine();
                    out.println(denumire);

                    int nrLocuriDisponibile=Integer.parseInt(in.readLine());
                    System.out.println("Raspuns: "+ nrLocuriDisponibile);
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        clientThread.start();
        serverThread.join();
        clientThread.join();

    }
}
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    public static List<Penalizare>getPenalizari()
    {
        List<Penalizare>penalizari=new ArrayList<>();
        try(var fisier=new FileReader("Date/penalizari.json"))
        {
            JSONArray arrayPenalizari=new JSONArray(new JSONTokener(fisier));
            penalizari=StreamSupport.stream(arrayPenalizari.spliterator(),false)
                    .map(item->(JSONObject)item)
                    .map(item->new Penalizare(
                            item.getInt("numar_apartament"),
                            item.getDouble("penalizare")
                    )).collect(Collectors.toList());
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return penalizari;
    }
    public static List<Intretinere>getIntretineri()
    {
        List<Intretinere>intretineri=new ArrayList<>();
        try(var fisier=new BufferedReader(new InputStreamReader(new FileInputStream("Date/intretinere_apartamente.txt"))))
        {
            intretineri=fisier.lines().map(Intretinere::new).collect(Collectors.toList());
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return intretineri;
    }
    public static void main(String[] args) throws InterruptedException{
        List<Intretinere>intretineri=getIntretineri();
        for(Intretinere intretinere:intretineri)
        {
            System.out.println(intretinere.toString());
        }

        List<Penalizare>penalizari=getPenalizari();
        for(Penalizare penalizare:penalizari)
        {
            System.out.println(penalizare.toString());
        }

        System.out.println("-----CERINTA 1--------");
        double valoareTotala=0;
        for(Intretinere intretinere:intretineri)
        {
            valoareTotala+=Double.parseDouble(intretinere.getValoare_plata());
        }

        System.out.println("Total intretinere: "+valoareTotala);

        System.out.println("-------CERINTA 2---------");
        List<Intretinere>finalIntretineri=getIntretineri();
        finalIntretineri=finalIntretineri.stream().sorted(Comparator.comparing(Intretinere::getValoare_plata).reversed()).collect(Collectors.toList());
        for(Intretinere intretinere:finalIntretineri)
        {
            System.out.println(intretinere.getValoare_plata()+" "+intretinere.getNr_Apartament()+" "+intretinere.getNume());
        }

        System.out.println("-------CERINTA 3--------------");
        for(Intretinere intretinere:intretineri)
        {
            double valoarePlataTotala=0;
            int penalizareAp=0;
            for (Penalizare penalizare:penalizari)
            {
                if(intretinere.getNr_Apartament()==penalizare.getNr_apartament())
                {
                    penalizareAp++;
                    valoarePlataTotala=Double.parseDouble(intretinere.getValoare_plata())+penalizare.getValoare();
                }
            }
            if(penalizareAp>0)
            {
                System.out.println(intretinere.getValoare_plata()+" "+intretinere.getNr_Apartament()+" "+intretinere.getNume());
            }
        }

        System.out.println("--------CERINTA 3 JSON------");
        try(var fisier=new FileWriter("Date/etc.json"))
        {
            JSONArray array=new JSONArray();
            for(Intretinere intretinere:intretineri)
            {
                int nrPenalizare=0;
                double valoarePlata=0;
                for(Penalizare penalizare:penalizari)
                {
                    if(intretinere.getNr_Apartament()==penalizare.getNr_apartament())
                    {
                        nrPenalizare++;
                        valoarePlata=Double.parseDouble(intretinere.getValoare_plata())+penalizare.getValoare();
                    }
                }
                if(nrPenalizare>0)
                {
                    JSONObject object=new JSONObject();
                    object.put("Penalizare",valoarePlata);
                    object.put("Nr_apartament",intretinere.getNr_Apartament());
                    object.put("Nume",intretinere.getNume());

                    array.put(object);
                }
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

        System.out.println("----CERINTA 4");
//        final int PORT=7070;
//        try(ServerSocket server = new ServerSocket(PORT))
//        {
//            System.out.println("Se asteapta clienti");
//            Socket socket=server.accept();
//            System.out.println("Client conectat");
//
//            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
//
//            int nrApartamentEX5=Integer.parseInt(in.readLine());
//            System.out.println("S-a primit apartamentul: "+nrApartamentEX5);
//
//            double valoareEX5=0;
//            for (Intretinere intretinere:intretineri)
//            {
//                if(intretinere.getNr_Apartament()==nrApartamentEX5)
//                {
//                    for(Penalizare penalizare:penalizari)
//                    {
//                        if (intretinere.getNr_Apartament()==penalizare.getNr_apartament())
//                        {valoareEX5=Double.parseDouble(intretinere.getValoare_plata())+penalizare.getValoare();}
//                    }
//                }
//            }
//
//            out.println(valoareEX5);
//
//            //trimitere lista
//
//            List<Intretinere>intretineriTrimise=new ArrayList<>();
//            for(Intretinere intretinere:intretineri)
//            {
//                if(intretinere.getNr_Apartament()==nrApartamentEX5)
//                {
//                    intretineriTrimise.add(intretinere);
//                }
//            }
//
//            out.println(intretineriTrimise);
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
                try(ServerSocket server = new ServerSocket(PORT))
                {
                    System.out.println("Se asteapta clienti");
                    Socket socket=server.accept();
                    System.out.println("Client conectat");

                    BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                    int nrApartamentEX5=Integer.parseInt(in.readLine());
                    System.out.println("S-a primit apartamentul: "+nrApartamentEX5);

                    double valoareEX5=0;
                    for (Intretinere intretinere:intretineri)
                    {
                        if(intretinere.getNr_Apartament()==nrApartamentEX5)
                        {
                            for(Penalizare penalizare:penalizari)
                            {
                                if (intretinere.getNr_Apartament()==penalizare.getNr_apartament())
                                {valoareEX5=Double.parseDouble(intretinere.getValoare_plata())+penalizare.getValoare();}
                            }
                        }
                    }

                    out.println(valoareEX5);

                    //trimitere lista

                    List<Intretinere>intretineriTrimise=new ArrayList<>();
                    for(Intretinere intretinere:intretineri)
                    {
                        if(intretinere.getNr_Apartament()==nrApartamentEX5)
                        {
                            intretineriTrimise.add(intretinere);
                        }
                    }

                    out.println(intretineriTrimise);
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
        });

        client.start();
        server.join();
        client.join();
    }
}
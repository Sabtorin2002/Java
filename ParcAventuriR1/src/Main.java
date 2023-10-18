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

    public static List<Aventura>getAventuri() {
        List<Aventura>aventuri=new ArrayList<>();
        try(var fisier=new FileReader("Date/aventuri.json"))
        {
            JSONArray array=new JSONArray(new JSONTokener(fisier));
            aventuri= StreamSupport.stream(array.spliterator(), false)
                    .map(item->(JSONObject)item)
                    .map(item->new Aventura(
                            item.getInt("cod_aventura"),
                            item.getString("denumire"),
                            item.getFloat("tarif"),
                            item.getInt("locuri_disponibile")
                    )).collect(Collectors.toList());
        }catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return aventuri;
    }
    public static List<Rezervare>getRezervari()
    {
        List<Rezervare>rezervari=new ArrayList<>();
        try(var fisier=new BufferedReader(new InputStreamReader(new FileInputStream("Date/rezervari.txt"))))
        {
            rezervari=fisier.lines().map(Rezervare::new).collect(Collectors.toList());
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return  rezervari;
    }
    public static void main(String[] args)throws InterruptedException {

        List<Aventura>aventuri=getAventuri();
        for(Aventura aventura: aventuri)
        {
            System.out.println(aventura.toString());
        }

        System.out.println("-----------------------");

        List<Rezervare>rezervari=getRezervari();
        for(Rezervare rezervare:rezervari)
        {
            System.out.println(rezervare.toString());
        }

        System.out.println("--------CERINTA 1-------------");
        for (Aventura aventura:aventuri)
        {
            if(aventura.getNrLocuri()>=20)
            {
                System.out.println(aventura.toString());
            }
        }

        System.out.println("-------CERINTA 2----------");

        for (Aventura aventura:aventuri)
        {
            int numarRezervate=0;
            for(Rezervare rezervare:rezervari)
            {
                if(aventura.getCod()==rezervare.getCod_aventura())
                {
                    numarRezervate=numarRezervate+rezervare.getNr_locuri_rezervate();
                }
            }
            if(aventura.getNrLocuri()-numarRezervate>=5)
            {
                System.out.println(aventura.getCod()+" "+aventura.getDenumire()+" "
                +(aventura.getNrLocuri()-numarRezervate));
            }
        }

        System.out.println("------CERINTA 2------------");
        aventuri.forEach(x->
        {
            int nrEX2=rezervari.stream().filter(y-> x.getCod()==y.getCod_aventura())
                    .collect(Collectors.toList()).stream().mapToInt(Rezervare::getNr_locuri_rezervate).sum();
            if(x.getNrLocuri()-nrEX2>=5)
                System.out.println(x.getCod()+" "+x.getDenumire()+" "+(x.getNrLocuri()-nrEX2));
        });

        System.out.println("-------CERINTA 3----------");
        List<Auxiliar> auxiliari=new ArrayList<>();
        List<Auxiliar>finalAuxiliari=new ArrayList<>();

        try(var fisier= new FileWriter("Date/venituri.txt"))
        {
            aventuri.forEach(x->{
                int nrEX3=rezervari.stream().filter(y-> x.getCod()==y.getCod_aventura())
                        .collect(Collectors.toList()).stream().mapToInt(Rezervare::getNr_locuri_rezervate).sum();
                double valoare=nrEX3* x.getTarif();

                Auxiliar aux= new Auxiliar(x.getDenumire(),nrEX3,valoare);
                auxiliari.add(aux);
            });

            finalAuxiliari=auxiliari;
            finalAuxiliari=finalAuxiliari.stream().sorted(Comparator.comparing(Auxiliar::getDenumire)).collect(Collectors.toList());
            for(Auxiliar auxiliar:finalAuxiliari)
            {
                fisier.write(auxiliar.toString());
                fisier.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Fisierul s-a creat cu succes");

        System.out.println("-----------CERINTA 3---------------");
        List<Auxiliar>auxiliars=new ArrayList<>();
        List<Auxiliar>finalAuxiliars=new ArrayList<>();
        try(var fisier=new FileWriter("Date/venituriNS.txt"))
        {
        for(Aventura aventura:aventuri)
        {
            int numarEX3NS=0;
            double valoareNS=0;
            for(Rezervare rezervare:rezervari)
            {
                if(aventura.getCod()==rezervare.getCod_aventura())
                {
                    numarEX3NS=numarEX3NS+rezervare.getNr_locuri_rezervate();
                }
                valoareNS=numarEX3NS*aventura.getTarif();
            }
            Auxiliar auxiliarNS=new Auxiliar(aventura.getDenumire(),numarEX3NS, valoareNS);
            auxiliars.add(auxiliarNS);
        }

        finalAuxiliars=auxiliars;
        finalAuxiliars=finalAuxiliars.stream().sorted(Comparator.comparing(Auxiliar::getDenumire)).collect(Collectors.toList());

        for(Auxiliar auxiliar:finalAuxiliars)
        {
            fisier.write(auxiliar.toString());
            fisier.write("\n");
        }
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Fisierul s-a creat cu succes");
        System.out.println("--------Cerinta 4----------");

//        final int PORT=7070;
//        try(ServerSocket server= new ServerSocket(PORT))
//        {
//            System.out.println("Se asteapta clienti");
//            Socket socket=server.accept();
//            System.out.println("Client conectat");
//
//            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
//
//            String denumireAventura=in.readLine();
//            System.out.println("Am primit de la client: "+ denumireAventura);
//
//            //trimitere lista
//            List<Aventura>aventuriTrimise=new ArrayList<>();
//            for(Aventura aventura:aventuri)
//            {
//                if(aventura.getDenumire().equalsIgnoreCase(denumireAventura))
//                {
//                    aventuriTrimise.add(aventura);
//                }
//            }
//            out.println(aventuriTrimise);
//
//            int numarLocuriEX4=0;
//            for (Aventura aventura:aventuri)
//            {
//                if(aventura.getDenumire().equalsIgnoreCase(denumireAventura))
//                {
//                    int totalRezervari=0;
//                    for(Rezervare rezervare:rezervari)
//                    {
//                        if(aventura.getCod()==rezervare.getCod_aventura())
//                        {
//                            totalRezervari=totalRezervari+rezervare.getNr_locuri_rezervate();
//                        }
//                    }
//                    numarLocuriEX4=aventura.getNrLocuri()-totalRezervari;
//                }
//            }
//
//            out.println(numarLocuriEX4);
//            System.out.println("Am trimis mesaj catre client");
//
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }

        System.out.println("------------CERINTA 5---------------");
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final int PORT=7070;
                try(ServerSocket server= new ServerSocket(PORT))
                {
                    System.out.println("Se asteapta clienti");
                    Socket socket=server.accept();
                    System.out.println("Client conectat");

                    BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                    String denumireAventura=in.readLine();
                    System.out.println("Am primit de la client: "+ denumireAventura);

                    //trimitere lista
                    List<Aventura>aventuriTrimise=new ArrayList<>();
                    for(Aventura aventura:aventuri)
                    {
                        if(aventura.getDenumire().equalsIgnoreCase(denumireAventura))
                        {
                            aventuriTrimise.add(aventura);
                        }
                    }
                    out.println(aventuriTrimise);

                    int numarLocuriEX4=0;
                    for (Aventura aventura:aventuri)
                    {
                        if(aventura.getDenumire().equalsIgnoreCase(denumireAventura))
                        {
                            int totalRezervari=0;
                            for(Rezervare rezervare:rezervari)
                            {
                                if(aventura.getCod()==rezervare.getCod_aventura())
                                {
                                    totalRezervari=totalRezervari+rezervare.getNr_locuri_rezervate();
                                }
                            }
                            numarLocuriEX4=aventura.getNrLocuri()-totalRezervari;
                        }
                    }

                    out.println(numarLocuriEX4);
                    System.out.println("Am trimis mesaj catre client");

                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();

        Thread serverClient= new Thread(new Runnable() {
            @Override
            public void run() {
                final int PORT=7070;
                final String serverName="127.0.0.1";

                try(Socket socket = new Socket(serverName,PORT))
                {
                    System.out.println("Client conectat");
                    BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out= new PrintWriter(socket.getOutputStream(),true);

                    System.out.println("Introduceti denumirea: ");
                    String denumire= new Scanner(System.in).nextLine();
                    out.println(denumire);

                    //primire lista
                    String aventuriPrimite=in.readLine();
                    System.out.println("Raspuns "+aventuriPrimite);

                    int numarLocuriTotalEX4=Integer.parseInt(in.readLine());
                    System.out.println("Raspuns "+numarLocuriTotalEX4);
                }catch (UnknownHostException e)
                {
                    e.printStackTrace();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        serverClient.start();
        serverThread.join();
        serverClient.join();
    }
}
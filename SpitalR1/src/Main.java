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


    public static List<Pacient> getPacienti()
    {
        List<Pacient>pacienti=new ArrayList<>();
        try(var fisier=new BufferedReader(new InputStreamReader(new FileInputStream("Date/pacienti.txt"))))
        {
            pacienti=fisier.lines().map(Pacient::new).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return pacienti;
    }

    public static List<Sectie>getSectiiJSON()
    {
        List<Sectie> sectii=new ArrayList<>();
        try(var fisier=new FileReader("Date/sectii.json"))
        {
            JSONArray arraySectii=new JSONArray(new JSONTokener(fisier));
            for(int i=0;i<arraySectii.length();i++)
            {
                JSONObject sectieCurenta=arraySectii.getJSONObject(i);
                sectii.add(new Sectie(
                        sectieCurenta.getInt("cod_sectie"),
                        sectieCurenta.getString("denumire"),
                        sectieCurenta.getInt("numar_locuri")
                ));


            }
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return sectii;
    }
    public static List<Sectie>getSectii()  {
        List<Sectie> sectii=new ArrayList<>();
        try(var fisier=new FileReader("Date/sectii.json"))
        {
            JSONArray array=new JSONArray(new JSONTokener(fisier));
            sectii= StreamSupport.stream(array.spliterator(), false)
                    .map(item->(JSONObject)item)
                    .map(item->new Sectie(
                            item.getInt("cod_sectie"),
                            item.getString("denumire"),
                            item.getInt("numar_locuri")
                    )).collect(Collectors.toList());


        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sectii;
    }

    public static void main(String[] args) throws InterruptedException{

        List<Pacient> pacienti=getPacienti();
        List<Sectie>sectii=getSectii();
        List<Auxiliar>auxiliari=new ArrayList<>();
        List<Auxiliar>finalAuxiliari=new ArrayList<>();
        List<Auxiliar>auxiliariNS=new ArrayList<>();
        List<Auxiliar>finalAuxialiarNS=new ArrayList<>();
        List<Sectie> sectieJSON=getSectiiJSON();

        for(Pacient pacient : pacienti)
        {
            System.out.println(pacient.toString());
        }

        System.out.println("-----------------------");

        for(Sectie sectie:sectii)
        {
            System.out.println(sectie.toString());
        }

        System.out.println("-----------------------");
        for(Sectie sectie: sectieJSON)
        {
            System.out.println(sectieJSON.toString());
        }

        System.out.println("--------CERINTA 1----------");

        for (Sectie sectie:sectii)
        {
            if(sectie.getNr_locuri()>=10)
            {
                System.out.println(sectie.toString());
            }
        }

        System.out.println("-------CERINTA 2---------");
        sectii.forEach(x->
        {
            double varsta_medie=pacienti.stream().filter(y-> x.getCod_sectie()==y.getCod_sectie())
                    .collect(Collectors.toList()).stream().collect(Collectors.averagingInt(Pacient::getVarsta));

            Auxiliar auxiliar= new Auxiliar(x.getCod_sectie(),x.getDenumire(),x.getNr_locuri(),varsta_medie);
            auxiliari.add(auxiliar);
        });

        finalAuxiliari=auxiliari;
        finalAuxiliari=finalAuxiliari.stream().sorted(Comparator.comparingDouble(Auxiliar::getVarsta_medie).reversed())
                .collect(Collectors.toList());

        for(Auxiliar auxiliar:finalAuxiliari)
        {
            System.out.println(auxiliar.toString());
        }

        System.out.println("---------Cerinta 2-------------");

        for (Sectie sectie:sectii)
        {
            int varsta=0;
            int nr=0;
            double medie=0;
            for (Pacient pacient:pacienti)
            {
                if(sectie.getCod_sectie()==pacient.getCod_sectie())
                {
                    varsta=varsta+pacient.getVarsta();
                    nr++;
                }
            }
            if(nr!=0)
            {medie=(double)varsta/nr ;
            Auxiliar auxiliarNS= new Auxiliar(sectie.getCod_sectie(),sectie.getDenumire(),sectie.getNr_locuri(),medie);
            auxiliariNS.add(auxiliarNS);}
        }

        finalAuxialiarNS=auxiliariNS;
        finalAuxialiarNS=finalAuxialiarNS.stream().sorted(Comparator.comparingDouble(Auxiliar::getVarsta_medie).reversed())
                .collect(Collectors.toList());
        for(Auxiliar auxiliar:finalAuxialiarNS)
        {
            System.out.println(auxiliar.toString());
        }

        System.out.println("-------CERINTA 3------------");
        try(var fisier=new FileWriter("Date/jurnal.txt"))
        {
            sectii.forEach(x->{
                int nrInternati=pacienti.stream().filter(y->x.getCod_sectie()==y.getCod_sectie())
                        .collect(Collectors.toList()).size();

                try{fisier.write(String.valueOf(x.getCod_sectie()));
                fisier.write(",");
                fisier.write(x.getDenumire());
                fisier.write(",");
                fisier.write(String.valueOf(nrInternati));
                fisier.write("\n");}
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("S-a creat fisierul cu succes");

        System.out.println("-----CERINTA 4-----------");

//        final int PORT=7070;
//        try(ServerSocket server= new ServerSocket(PORT))
//        {
//            System.out.println("Se asteapta clienti");
//            Socket socket=server.accept();
//            System.out.println("Client conectat");
//
//            BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out= new PrintWriter(socket.getOutputStream(),true);
//
//            int cod=Integer.parseInt(in.readLine());
//            System.out.println("S-a primit codul: "+cod );
//
//            int nrLocuriLibere=0;
//            for(Sectie sectie:sectii)
//            {
//                if(sectie.getCod_sectie()==cod)
//                {
//                    int nrLocuriOcupate=0;
//                    for(Pacient pacient:pacienti)
//                    {
//                        if(sectie.getCod_sectie()==pacient.getCod_sectie())
//                        {
//                            nrLocuriOcupate++;
//                        }
//                    }
//                    nrLocuriLibere=sectie.getNr_locuri()-nrLocuriOcupate;
//                }
//            }
//
//            out.println(nrLocuriLibere);
//
//            //trimitere lista
//            List<Pacient>pacientiTrimisi=new ArrayList<>();
//            for(Pacient pacient:pacienti)
//            {
//                if(pacient.getCod_sectie()==cod)
//                {
//                    pacientiTrimisi.add(pacient);
//                }
//            }
//
//            out.println(pacientiTrimisi);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("-----CERINTA 5---------");
        Thread server= new Thread(new Runnable() {
            @Override
            public void run() {
                final int PORT=7070;
                try(ServerSocket server= new ServerSocket(PORT))
                {
                    System.out.println("Se asteapta clienti");
                    Socket socket=server.accept();
                    System.out.println("Client conectat");

                    BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out= new PrintWriter(socket.getOutputStream(),true);

                    int cod=Integer.parseInt(in.readLine());
                    System.out.println("S-a primit codul: "+cod );

                    int nrLocuriLibere=0;
                    for(Sectie sectie:sectii)
                    {
                        if(sectie.getCod_sectie()==cod)
                        {
                            int nrLocuriOcupate=0;
                            for(Pacient pacient:pacienti)
                            {
                                if(sectie.getCod_sectie()==pacient.getCod_sectie())
                                {
                                    nrLocuriOcupate++;
                                }
                            }
                            nrLocuriLibere=sectie.getNr_locuri()-nrLocuriOcupate;
                        }
                    }

                    out.println(nrLocuriLibere);

                    //trimitere lista
                    List<Pacient>pacientiTrimisi=new ArrayList<>();
                    for(Pacient pacient:pacienti)
                    {
                        if(pacient.getCod_sectie()==cod)
                        {
                            pacientiTrimisi.add(pacient);
                        }
                    }

                    out.println(pacientiTrimisi);

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

                try(Socket socket= new Socket(serverName,PORT))
                {
                    System.out.println("Client conectat");

                    BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                    System.out.println("Introduceti codul:");
                    String cod=new Scanner(System.in).nextLine();
                    out.println(cod);

                    int nrLocuriLibere=Integer.parseInt(in.readLine());
                    System.out.println("Raspuns:"+nrLocuriLibere);

                    //primire lista
                    String pacientiPrimiti=in.readLine();
                    System.out.println("Raspuns: "+pacientiPrimiti);
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
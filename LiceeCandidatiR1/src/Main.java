import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static List<Candidat> getCandidati() throws FileNotFoundException {
//        List<Candidat>candidati=new ArrayList<>();
//        try(var fisier= new FileReader("Date/candidati.json"))
//        {
//            JSONArray arrayCandidati =new JSONArray(new JSONTokener(fisier));
//            for(int i=0;i<arrayCandidati.length();i++)
//            {
//                JSONObject candidatCurent=arrayCandidati.getJSONObject(i);
//                JSONArray arrayOptiuni=new JSONArray(new JSONTokener(candidatCurent.getJSONArray("optiuni").toString()));
//
//                Map<Integer, List<Integer>>optiuni=new HashMap<>();
//
//                for(int j=0;j<arrayOptiuni.length();j++)
//                {
//                    JSONObject optiuneCurenta=arrayOptiuni.getJSONObject(j);
//
//                    optiuni.putIfAbsent(optiuneCurenta.getInt("cod_liceu"), new ArrayList<>());
//                    optiuni.get(optiuneCurenta.getInt("cod_liceu")).add(optiuneCurenta.getInt("cod_specializare"));
//                }
//
//                candidati.add(new Candidat(
//                        candidatCurent.getInt("cod_candidat"),
//                        candidatCurent.getString("nume_candidat"),
//                        candidatCurent.getDouble("media"),
//                        optiuni
//                ));
//
//            }
//
//        }catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        return candidati;

        List<Candidat>candidati=new ArrayList<>();
        try(var fisier=new FileReader("Date/candidati.json"))
        {

            JSONArray arrayCandidati=new JSONArray(new JSONTokener(fisier));
            for(int i=0;i<arrayCandidati.length();i++)
            {
                JSONObject candidatCurent=arrayCandidati.getJSONObject(i);
                JSONArray arrayOptiuni=new JSONArray(new JSONTokener(candidatCurent.getJSONArray("optiuni").toString()));

                Map<Integer, List<Integer>>optiuni=new HashMap<>();

                for(int j=0;j<arrayOptiuni.length();j++)
                {
                    JSONObject optiuneCurenta=arrayOptiuni.getJSONObject(j);

                    optiuni.putIfAbsent(optiuneCurenta.getInt("cod_liceu"), new ArrayList<>());
                    optiuni.get(optiuneCurenta.getInt("cod_liceu")).add(optiuneCurenta.getInt("cod_specializare"));

                }

                candidati.add(new Candidat(
                        candidatCurent.getInt("cod_candidat"),
                        candidatCurent.getString("nume_candidat"),
                        candidatCurent.getDouble("media"),
                        optiuni
                ));
            }

        } catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return candidati;
    }

    public static List<Liceu>getLicee()
    {
        List<Liceu>licee=new ArrayList<>();
        try(var fisier=new BufferedReader(new InputStreamReader(new FileInputStream("Date/licee.txt"))))
        {
            licee=fisier.lines().map(Liceu::new).collect(Collectors.toList());
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return licee;
    }
    public static void main(String[] args) throws IOException, InterruptedException {

        List<Candidat>candidati=getCandidati();
        List<Liceu>licee=getLicee();
        List<Liceu>finalLicee=new ArrayList<>();
        List<Auxliar>auxiliari=new ArrayList<>();
        List<Auxliar>finalAuxiliari=new ArrayList<>();
        List<Auxliar>ffAuxiliari=new ArrayList<>();
        for(Candidat candidat:candidati)
        {
            System.out.println(candidat.toString());
        }
        System.out.println("-----------------");

        for (Liceu liceu:licee)
        {
            System.out.println(liceu.toString());
        }

        System.out.println("-------CERINTA 1-----------");
        for(Candidat candidat:candidati)
        {
            if(candidat.getMedie()>=9)
            {
                System.out.println(candidat.toString());
            }
        }

        System.out.println("-------CERINTA 2-------------");
        finalLicee=licee;
        finalLicee=finalLicee.stream().sorted(Comparator.comparingInt(Liceu::getNr_locuri).reversed()).collect(Collectors.toList());
        for(Liceu liceu:finalLicee)
        {
            System.out.println(liceu.toString());
        }

        System.out.println("---------CERINTA 3------------");
        try(var fisier=new FileWriter("Date/jurnal.txt"))
        {
            candidati.forEach(x->
            {
                Auxliar aux=new Auxliar(x.getCod_candidat(),x.getNume(),x.getNrOptiuni(),x.getMedie());
                auxiliari.add(aux);
            });
            finalAuxiliari=auxiliari;
            finalAuxiliari=finalAuxiliari.stream().sorted(Comparator.comparingInt(Auxliar::getNrOptiuni).reversed()
                    .thenComparingDouble(Auxliar::getMedie).reversed()).collect(Collectors.toList());

            ffAuxiliari=auxiliari;
            ffAuxiliari=ffAuxiliari.stream().sorted(Comparator.comparingInt(Auxliar::getNrOptiuni).reversed())
                    .collect(Collectors.toList());

            for(Auxliar auxiliar:ffAuxiliari)
            {
                fisier.write(auxiliar.toString());
                fisier.write("\n");
            }
        }

        System.out.println("Fisierul s-a creat cu succes");

        System.out.println("-------CERINTA 4------------");
//        final int PORT=7070;
//        try(ServerSocket server =new ServerSocket(PORT))
//        {
//            System.out.println("Se asteapta clienti");
//            Socket socket=server.accept();
//            System.out.println("S-a conectat clientul");
//
//            BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
//
//            int cod=Integer.parseInt(in.readLine());
//            System.out.println("S-a primit codul: "+cod);
//
//            //trimitere lista
//            List<Candidat>candidatTrimis=new ArrayList<>();
//            for(Candidat candidat:candidati)
//            {
//                if(candidat.getCod_candidat()==cod)
//                {
//                    candidatTrimis.add(candidat);
//                }
//            }
//            out.println(candidatTrimis);
//
//        }

        System.out.println("---------CERINTA 5---------");
        Thread server=new Thread(new Runnable() {
            @Override
            public void run() {
                final int PORT=7070;
                try(ServerSocket server =new ServerSocket(PORT))
                {
                    System.out.println("Se asteapta clienti");
                    Socket socket=server.accept();
                    System.out.println("S-a conectat clientul");

                    BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                    int cod=Integer.parseInt(in.readLine());
                    System.out.println("S-a primit codul: "+cod);

                    //trimitere lista
                    List<Candidat>candidatTrimis=new ArrayList<>();
                    for(Candidat candidat:candidati)
                    {
                        if(candidat.getCod_candidat()==cod)
                        {
                            candidatTrimis.add(candidat);
                        }
                    }
                    out.println(candidatTrimis);

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
                    System.out.println("S-a conectat clientul");

                    BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                    System.out.println("Introduceti codul:");
                    String cod=new Scanner(System.in).nextLine();
                    out.println(cod);

                    //primire lista
                    String candidatiPrimiti=in.readLine();
                    System.out.println("Raspuns: "+ candidatiPrimiti);
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
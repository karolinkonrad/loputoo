import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Ylesanne yl = new LisamiseYlesanne();
        Labimang labimang = new Labimang(new LisamiseYlesanne());

        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("-----------------------------------------------------------");
            System.out.println(labimang.getPaisktabelid().toString());
            System.out.println(yl.ylesandeKirjeldus());
            System.out.println(labimang.getSisend().toString());
            System.out.println("""
                    l - algoritm lõpetab
                    p <len> loo paisktabel pikkusega len
                    s <x> (t) <i> sisesta x (tabelis t) indeksile i
                    e (t) <i> <x> kustuta (tabelist t) indexilt i arv x
                    u võta samm tagasi""");

            String[] userCommand = sc.nextLine().split(" ");

            switch (userCommand[0]){
                // l algoritm lõpetab
                case "l":
                    labimang.astu(new LopetusSamm(labimang));
                    System.out.println("Hinne:" + labimang.getHinne());
                    return;
                // p <len> loo paisktabel pikkusega len
                case "p":
                    labimang.astu(new PaisktabeliLoomisSamm(labimang, Integer.parseInt(userCommand[1])));
                // s <x> (t) <i> sisesta x (tabelis t) indeksile i
                    break;
                case "s":
                    if (userCommand.length > 3)
                        labimang.astu(new SisestusSamm(labimang, Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[3]), Integer.parseInt(userCommand[1])));
                    else
                        labimang.astu(new SisestusSamm(labimang, 0,Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[1])));
                    break;
                // e (t) <i> <x> kustuta (tabelist t) indexilt i arv x
                case "e":
                    if (userCommand.length > 3)
                        labimang.astu(new EemaldusSamm(labimang, Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[3])));
                    else if (userCommand.length > 2)
                        labimang.astu(new EemaldusSamm(labimang, 0, Integer.parseInt(userCommand[1]), Integer.parseInt(userCommand[2])));
                    break;
                // u võta samm tagasi
                case "u":
                    labimang.tagasi();
                    break;
            }
        }
    }

    private static void alusta(Labimang labimang) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Lisa paisktabelisse:");
        System.out.println(labimang.getSisend().toString());
        System.out.print("Sisesta paisktabeli pikkus: ");

        // küsi paisktabeli pikkust, sest ka paberil nad ei saaks midagi teha, kui pole tabelit
        while (true) {
            try {
                String lenSone = sc.nextLine();
                labimang.astu(new PaisktabeliLoomisSamm(labimang, Integer.parseInt(lenSone)));
                break;
            }catch (RuntimeException e) {
                System.out.println(e);
                System.out.println("Paisktabeli pikkus võiks olla positiivne täisarv.");
            }
        }
    }
}

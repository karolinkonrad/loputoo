import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        LisamiseSisendiLugeja sisendiLugeja = new LisamiseSisendiLugeja();
        Labimang labimang = new Labimang(sisendiLugeja.getSisend());

        alusta(labimang);


        while (true){
            System.out.println(labimang.getPaisktabel().toString());
            System.out.println(labimang.getSisend().toString());
            System.out.println("""
                    l - algoritm lõpetab
                    s <x> <i> - sisesta x indeksile i
                    u - võta tagasi
                    a - alusta otsast""");

            String[] userCommand = sc.nextLine().split(" ");
            switch (userCommand[0]){
                // l algoritm lõpetab
                case "l":
                    labimang.astu(new LopetusSamm(labimang.getPaisktabel()));
                    return;
                // s <x> <i> sisesta x indeksile i
                case "s":
                    labimang.astu(new SisestusSamm(labimang.getPaisktabel(), Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[1])));
                    break;
                case "u":
                    labimang.undo();
                    break;
                case "a":
                    alusta(labimang);
                /* d <i> kustuta indexilt i
                case "d":
                    labimang.astu(new EemaldusSamm(labimang.getPaisktabel(), Integer.parseInt(userCommand[1])));
                    */
            }
        }
    }

    private static void alusta(Labimang labimang) {
        Scanner sc = new Scanner(System.in);

        System.out.println(labimang.getSisend().toString());
        System.out.println("Sisesta paisktabeli pikkus");

        // küsi paisktabeli pikkust, sest ka paberil nad ei saaks midagi teha, kui pole tabelit
        while (true) {
            try {
                String lenSone = sc.nextLine();
                labimang.astu(new PaisktabeliLoomisSamm(labimang.getPaisktabel(), Integer.parseInt(lenSone)));
                break;
            }catch (RuntimeException e) {
                System.out.println("Paisktabeli pikkus võiks olla positiivne täisarv.");
            }
        }
    }
}

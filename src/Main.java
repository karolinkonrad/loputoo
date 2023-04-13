import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] sisend = {0, 13, 14, 3};
        Labimang labimang = new Labimang(sisend);

        System.out.println("lisa paisktabelisse " + Arrays.toString(sisend));

        while (true){

            String[] userCommand = sc.nextLine().split(" ");
            switch (userCommand[0]){
                // v algoritm lõpetab
                case "v":
                    labimang.hinda();
                    return;
                // p <len> loo paisktabel pikkusega len
                case "p":
                    int len = Integer.parseInt(userCommand[1]);
                    labimang.make_tabel(len);
                    break;
                // s <x> <i> sisesta x indeksile i
                case "s":
                    labimang.sisesta(Integer.parseInt(userCommand[2]), userCommand[1]);
                    break;
                // d <i> kustuta indexilt i
                case "d":
                    labimang.eemalda(Integer.parseInt(userCommand[1]));
            }

            System.out.println(labimang.toString());

        }
    }
}

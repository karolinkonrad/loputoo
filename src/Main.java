import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String[] indeksid = null;

        String[] paiskTabel = null;
        System.out.println("lisa paisktabelisse 0, 13, 14, 3");
        while (true){
            String[] userCommand = myObj.nextLine().split(" ");
            switch (userCommand[0]){
                // algoritm lõpetab
                case "v":
                    return;
                // p <len> loo paisktabel pikkusega len
                case "p":
                    int len = Integer.parseInt(userCommand[1]);
                    paiskTabel = new String[len];
                    Arrays.fill(paiskTabel, " ");
                    indeksid = new String[len];
                    for (int i = 0; i < len; i++) {
                        indeksid[i] = String.valueOf(i);
                    }
                    break;
                // s <x> <i> sisesta x indeksile i
                case "s":
                    paiskTabel[Integer.parseInt(userCommand[2])] = userCommand[1];
                    break;
                // d <i> kustuta indexilt i
                case "d":
                    paiskTabel[Integer.parseInt(userCommand[1])] = " ";

            }

            if (paiskTabel != null) {
                System.out.println(Arrays.toString(indeksid));
                System.out.println(Arrays.toString(paiskTabel));
            }

        }
    }
}

package mafia_42.player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Mafia extends Player{
    Scanner scanner = new Scanner(System.in);
    public Mafia(String name) {
        //this.name = name;
        super(name);
    }

    public String selectTarget(ArrayList<String> players, ArrayList<String> deadPlayers, ArrayList<String> mafiaTeam) {
        String mafiaTarget;
        do {
            System.out.print("\n현재 살아있는 인원: [ ");
            for (String value : players) {
                System.out.print(value + " ");
            }

            System.out.println("] ");
            System.out.print("마피아는 누구를 죽이겠습니까? ");
            mafiaTarget = scanner.nextLine();

            if (players.contains(mafiaTarget) && !deadPlayers.contains(mafiaTarget)) {
                if(mafiaTeam.contains(mafiaTarget)){
                    System.out.println("마피아는 마피아를 죽일 수 없습니다. 다시 선택해주세요.");
                }else{
                    break;}

            } else if (deadPlayers.contains(mafiaTarget)) {
                System.out.println("이미 죽은 플레이어입니다. 다시 선택해주세요.");
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        } while (true);
        return mafiaTarget;
    }

}

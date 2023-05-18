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

    public String selectTarget(ArrayList<String> players, ArrayList<String> deadPlayers, ArrayList<String> mafiaTeam){
        String mafiaTarget = null;
        System.out.println("마피아 selectTarget함수가 실행됐습니다.");
        do {
            System.out.print("\n현재 살아있는 인원 : ");
            for (String value : players) {
                System.out.print(value + " ");
            }
            System.out.println(" ");
            System.out.print("마피아는 누구를 죽이겠습니까? ");
            mafiaTarget = scanner.nextLine();

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).equals(mafiaTarget)) {
                    //마피아가 선택한 타겟의 이름과 플레이어
                    return mafiaTarget;
                } else if (deadPlayers.get(i).equals(mafiaTarget)) {
                    System.out.println("이미 죽은 플레이어입니다. 다시 선택해주세요.");
                }
                for (String mafia : mafiaTeam) {
                    if (mafiaTarget.equals(mafia)) {
                        System.out.println("마피아는 마피아팀을 죽일 수 없습니다. 다시 선택해주세요.");
                    }
                }
            }
        } while(true);
    }
}

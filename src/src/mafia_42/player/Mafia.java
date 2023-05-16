package mafia_42.player;

import java.util.ArrayList;
import java.util.Scanner;

public class Mafia extends Player{
    Scanner scanner = new Scanner(System.in);
    public Mafia(String name) {
        //this.name = name;
        super(name);
    }

    public Player selectTarget(ArrayList<Player> players, ArrayList<Player> deadPlayers){
        Player mafiaTarget = null;
        do {
            System.out.print("\n현재 살아있는 인원 : ");
            for (int i = 0; i < players.size() -1; i++) {
                System.out.println(players.get(0).getName());
            }
            System.out.print("마피아는 누구를 죽이겠습니까? ");
            mafiaTarget = new Player(scanner.nextLine());
            for (Player deadPlayer : deadPlayers) {
                if (deadPlayer.getName().equals(mafiaTarget.getName())) {
                    System.out.println("이미 죽은 플레이어입니다. 다시 선택해주세요.");
                } else {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }
            }

        } while (!players.contains(mafiaTarget) || deadPlayers.contains(mafiaTarget));
        return mafiaTarget;
    }
}

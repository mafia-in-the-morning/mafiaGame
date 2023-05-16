package mafia_42.player;

import java.util.ArrayList;
import java.util.Scanner;

public class Police extends Player {
    Scanner scanner = new Scanner(System.in);
    public Police(String name) {
        //this.name = name;
        super(name);
    }

    public Player detectMafia(ArrayList<Player> players, ArrayList<Player> deadPlayers, Police police) {
        Player policeTarget = null;
        if (!deadPlayers.contains(police)) {
            do {
                System.out.print("경찰은 누구를 조사하겠습니까? ");
                for (Player player : players) {
                    System.out.println("\n현재 살아있는 인원 : " + player.getName());
                }
                //경찰의 타겟을 Player 객체로 생성
                policeTarget = new Player(scanner.nextLine());
                if (policeTarget.equals(police)) {
                    System.out.println("경찰은 자신을 조사할 수 없습니다. 다시 선택해주세요.");
                    //return "경찰은 자신을 조사할 수 없습니다. 다시 선택해주세요.";
                } else if (deadPlayers.contains(policeTarget)) {
                    System.out.println(policeTarget + "님은 이미 죽은 상태입니다. 다시 선택해주세요.");
                    //return policeTarget + "님은 이미 죽은 상태입니다. 다시 선택해주세요.";
                } else {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    //return "잘못된 입력입니다. 다시 입력해주세요.";
                }
            } while (!players.contains(policeTarget) || policeTarget.equals(police) || deadPlayers.contains(policeTarget));
        }
        return policeTarget;
    }
}

package mafia_42.player;

import java.util.ArrayList;
import java.util.Scanner;

public class Police extends Player {
    Scanner scanner = new Scanner(System.in);
    public Police(String name) {
        //this.name = name;
        super(name);
    }

    public String detectMafia(ArrayList<String> players, ArrayList<String> deadPlayers, Police police) {
        String policeTarget = null;
        //경찰이 살아있어야 실행됨
        if (!deadPlayers.contains(police.getName())) {
            do {
                System.out.print("경찰은 누구를 조사하겠습니까? ");
                System.out.print("\n현재 살아있는 인원: [ ");
                for (String player : players) {
                    System.out.print(player + " ");
                }
                System.out.println("] ");
                //경찰의 타겟을 Player 객체로 생성
                policeTarget = scanner.nextLine();
                if (players.contains(policeTarget) && !deadPlayers.contains(policeTarget)) {
                    break;
                }
                else if (policeTarget.equals(police.getName())) {
                    System.out.println("경찰은 자신을 조사할 수 없습니다. 다시 선택해주세요.");
                    //return "경찰은 자신을 조사할 수 없습니다. 다시 선택해주세요.";
                } else if (deadPlayers.contains(policeTarget)) {
                    System.out.println(policeTarget + "님은 이미 죽은 상태입니다. 다시 선택해주세요.");
                    //return policeTarget + "님은 이미 죽은 상태입니다. 다시 선택해주세요.";
                } else {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    //return "잘못된 입력입니다. 다시 입력해주세요.";
                }
            } while (!players.contains(policeTarget) || policeTarget.equals(police.getName()) || deadPlayers.contains(policeTarget));
        } else{
                System.out.println("경찰이 사망했으므로 경찰은 역할을 수행할 수 없습니다.");
        }
        return policeTarget;
    }
}

package mafia_42.player;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Scanner;

public class Doctor extends Player{
    Scanner scanner = new Scanner(System.in);
    public Doctor(String name) {
        //this.name = name;
        super(name);
    }

    public Player selectTarget(ArrayList<Player> players, ArrayList<Player> deadPlayers, Doctor doctor) {
        Player doctorTarget = null;
        if (!deadPlayers.contains(doctor)) {
            do {
                System.out.print("의사는 누구를 살리겠습니까? ");
                for (Player player : players) {
                    System.out.println("\n현재 살아있는 인원 : " + player.getName());
                }
                doctorTarget = new Player(scanner.nextLine());
                if (deadPlayers.contains(doctorTarget)) {
                    System.out.println(doctorTarget + "님은 이미 죽은 상태입니다. 다시 선택해주세요.");
                } else {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }
            } while (!players.contains(doctorTarget) || deadPlayers.contains(doctorTarget));
        } else {
            System.out.println("의사가 이미 사망했으므로 의사는 역할을 수행할 수 없습니다.");
        }
        return doctorTarget;
    }
}

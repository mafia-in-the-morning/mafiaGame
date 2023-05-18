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

    public String selectPatient(ArrayList<String> players, ArrayList<String> deadPlayers, Doctor doctor){
        String doctorTarget = "";
        if (!deadPlayers.contains(doctor.getName())) {
            do {
                System.out.print("의사는 누구를 살리겠습니까? ");
                System.out.print("\n현재 살아있는 인원: [ ");
                for (String value : players) {
                    System.out.print(value + " ");
                }

                System.out.println("] ");
                doctorTarget = scanner.nextLine();
                if (players.contains(doctorTarget) && !deadPlayers.contains(doctorTarget)) {
                    break;
                }
                else if (deadPlayers.contains(doctorTarget)) {
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

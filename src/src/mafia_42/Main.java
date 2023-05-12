package mafia_42;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // 참가자 이름 입력 받기
        System.out.println("게임에 참가하는 인원 수를 입력하세요(최소 5명, 최대 8명):");
        int numOfPlayers = scanner.nextInt();
        scanner.nextLine(); // 개행문자 제거

        while (numOfPlayers < 5 || numOfPlayers > 8) {
            System.out.println("잘못된 입력입니다. 최소 5명, 최대 8명까지 입력해주세요.");
            System.out.println("게임에 참가하는 인원 수를 입력하세요(최소 5명, 최대 8명):");
            numOfPlayers = scanner.nextInt();
            scanner.nextLine(); // 개행문자 제거
        }

        ArrayList<String> players = new ArrayList<>();
        ArrayList<String> deadPlayers = new ArrayList<>();

        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("참가자 " + (i + 1) + "의 이름을 입력하세요: ");
            String name = scanner.nextLine();
            players.add(name);
        }


        // 마피아, 의사, 경찰 랜덤 선택
        String mafia = players.get(random.nextInt(numOfPlayers));
        String doctor = players.get(random.nextInt(numOfPlayers));
        String police = players.get(random.nextInt(numOfPlayers));

        // 중복되는 역할이 없도록 while문 추가
        while (mafia.equals(doctor) || mafia.equals(police) || doctor.equals(police)) {
            doctor = players.get(random.nextInt(numOfPlayers));
            police = players.get(random.nextInt(numOfPlayers));
        }

        System.out.println("게임 참가자: " + players);
        System.out.println("마피아: " + mafia);
        System.out.println("의사: " + doctor);
        System.out.println("경찰: " + police);

        while (true) {
            // 밤이 되었습니다.
            System.out.println("\n밤이 되었습니다.");

            // 마피아가 죽일 대상을 선택합니다.
            String mafiaTarget = "";
            while (true) {
                System.out.print("마피아는 누구를 죽이겠습니까? ");
                mafiaTarget = scanner.nextLine();
                if (players.contains(mafiaTarget) && !deadPlayers.contains(mafiaTarget)) {
                    break;
                } else if (deadPlayers.contains(mafiaTarget)) {
                    System.out.println("이미 죽은 플레이어입니다. 다시 선택해주세요.");
                } else {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }
            }
            // 의사가 살릴 대상을 선택합니다.
            String doctorTarget = "";
            while (true) {
                System.out.print("의사는 누구를 살리겠습니까? ");
                doctorTarget = scanner.nextLine();
                if (players.contains(doctorTarget) && !deadPlayers.contains(doctorTarget)) {
                    break;
                } else if (deadPlayers.contains(doctorTarget)) {
                    System.out.println(doctorTarget + "님은 이미 죽은 상태입니다. 다시 선택해주세요.");
                } else {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }
            }
            // 마피아와 의사가 선택한 대상이 같은 경우
            if (mafiaTarget.equals(doctorTarget)) {
                System.out.println("의사의 치료로 인해 " + mafiaTarget + "님이 살아났습니다.");
            } else {
                System.out.println(mafiaTarget + "님이 죽었습니다.");
                deadPlayers.add(mafiaTarget);
            }
        }
    }
}




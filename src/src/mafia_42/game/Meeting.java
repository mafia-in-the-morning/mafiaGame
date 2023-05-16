package mafia_42.game;


import mafia_42.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Meeting {
    //회의
    Scanner scanner = new Scanner(System.in);
    public Meeting(ArrayList<Player> players) {
    }

    //투표
    void doVote(ArrayList<Player> players, ArrayList<Player> deadPlayers, HashMap<String, Integer> votes){
        for (Player player : players) {
            if (!deadPlayers.contains(player)) { // 이미 죽은 참가자는 제외
                boolean voted = false; // 투표 완료 여부를 나타내는 변수
                while (!voted) { // 투표가 완료될 때까지 반복
                    System.out.print(player + "님, 투표하실 분을 입력하세요: ");
                    String vote = scanner.nextLine();
                    if (!deadPlayers.contains(vote)) { // 죽은 참가자에 대한 투표 불가
                        if (votes.containsKey(vote)) { // 이미 투표된 참가자의 득표수 증가
                            votes.put(vote, votes.get(vote) + 1);
                        } else { // 처음 투표된 참가자는 득표수 1로 초기화
                            votes.put(vote, 1);
                        }
                        voted = true; // 투표가 완료됨을 표시
                    } else {
                        System.out.println("죽은 참가자에게는 투표할 수 없습니다. 다시 입력해주세요.");
                    }
                }
            }
        }

    }

}

package mafia_42.game;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Voting {
    public HashMap<String, Integer> conductVoting(List<String> players, List<String> deadPlayers) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== 투표 시간 ===");
        System.out.println(players);
        HashMap<String, Integer> votes = new HashMap<>();

        for (String player : players) {
            if (!deadPlayers.contains(player)) {
                boolean voted = false;
                while (!voted) {
                    System.out.print(player + "님, 투표하실 분을 입력하세요: ");
                    String vote = scanner.nextLine();
                    if (!deadPlayers.contains(vote)) {
                        if (votes.containsKey(vote)) {
                            votes.put(vote, votes.get(vote) + 1);
                        } else {
                            votes.put(vote, 1);
                        }
                        voted = true;
                    } else {
                        System.out.println("죽은 참가자에게는 투표할 수 없습니다. 다시 입력해주세요.");
                    }
                }
            }
        }

        return votes;
    }
}


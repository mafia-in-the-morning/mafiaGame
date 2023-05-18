package mafia_42.game;

import mafia_42.player.Doctor;
import mafia_42.player.Mafia;
import mafia_42.player.Player;
import mafia_42.player.Police;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);

    public int setNumberOfPeople() {
        int numOfPlayers;
        while (true) {
            System.out.print("게임에 참가하는 인원 수를 입력하세요(최소 4명, 최대 8명): ");
            String input = scanner.nextLine();

            try {
                numOfPlayers = Integer.parseInt(input);
                if (numOfPlayers < 4 || numOfPlayers > 8) {
                    System.out.println("잘못된 입력입니다. 최소 4명, 최대 8명까지 입력해주세요.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자로 입력해주세요.");
            }
        }
        //게임에 참여하는 인원수 리턴
        return numOfPlayers;
    }

    public static ArrayList<String> setPlayers(int numOfPlayers) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> players = new ArrayList<>();

        Game game = new Game();

        for (int i = 0; i < numOfPlayers; i++) {
            while (true) {
                System.out.print("참가자 " + (i + 1) + "의 이름을 입력하세요: ");
                String name = scanner.nextLine();

                if (name.trim().isEmpty()) {
                    System.out.println("이름을 확인해주세요.");
                } else {
                    if (players.contains(name)) {
                        int count = 2;
                        String newName = name + count;
                        while (players.contains(newName)) {
                            count++;
                            newName = name + count;
                        }
                        players.add(newName);
                    } else {
                        players.add(name);
                    }
                    break;
                }
            }
        }
        return players;
    }

    public static void assignRoles(List<String> players, List<String> mafiaTeam, int numOfPlayers, Mafia mafia, Mafia mafia2, Doctor doctor, Police police ) {
        Random random = new Random();

        List<Integer> availableNumbers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            availableNumbers.add(i);
        }

        List<Integer> selectedNumbers = new ArrayList<>();
        while (selectedNumbers.size() < 4) {
            int randomNumber = random.nextInt(availableNumbers.size());
            int selectedNumber = availableNumbers.remove(randomNumber);
            selectedNumbers.add(selectedNumber);
        }
        int mafiaNum = selectedNumbers.get(0);
        int doctorNum = selectedNumbers.get(1);
        int policeNum = selectedNumbers.get(2);
        int mafiaNum2 = selectedNumbers.get(3);

        // 마피아, 의사, 경찰 랜덤 선택
        mafia = new Mafia(players.get(mafiaNum));
        mafiaTeam.add(mafia.getName());
        doctor = new Doctor(players.get(doctorNum));
        police = new Police(players.get(policeNum));
        mafia2 = null;
        if (numOfPlayers >= 6) {
            mafia2 = new Mafia(players.get(mafiaNum2));
            mafiaTeam.add(mafia2.getName());
        }

        System.out.println("게임 참가자: " + players);

        System.out.println("마피아: " + mafia.getName());
        if(numOfPlayers>=6){
            System.out.println("마피아2: "+mafia2.getName());
        }
        System.out.println("의사: " + doctor.getName());
        System.out.println("경찰: " + police.getName());
    }




    public void compareNumofMafiaAndCitizen(ArrayList<Player> citizenTeam, ArrayList<Player> mafiaTeam){
        if(citizenTeam.size() <= mafiaTeam.size()){
            System.out.println("마피아의 수가 시민수와 같거나 많습니다. 마피아 팀의 승리입니다!");
        } else if (mafiaTeam.size() == 0) {
            System.out.println("마피아가 모두 사망했습니다. 시민팀의 승리입니다!");
        }
    }

    //public boolean checkRoleRedundancy(Mafia mafia, Doctor doctor, Police police){}
}

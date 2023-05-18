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


    Mafia mafia;
    Mafia mafia2;
    Doctor doctor;
    Police police;
    public void assignRoles(ArrayList<String> players, ArrayList<String> mafiaTeam,
                            ArrayList<String> citizenTeam) {
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
        System.out.println(">> 🔫마피아: " + mafia.getName() + "님");


        mafiaTeam.add(mafia.getName());
        doctor = new Doctor(players.get(doctorNum));
        System.out.println(">> 🩺의사: " + doctor.getName() + "님");


        police = new Police(players.get(policeNum));
        System.out.println(">> 👮🏻‍경찰: " + police.getName() + "님");


        mafia2 = null;
        // 인원이 6인 이상인 경우 마피아 2 생성
        if (players.size() >= 6) {
            mafia2 = new Mafia(players.get(mafiaNum2));
            mafiaTeam.add(mafia2.getName());
            System.out.println("🔪마피아2: " + mafia2.getName() + "님");
        }

        // mafiaTeam에 포함되지 않는 모든 player를 citizenTeam에 소속시킴
        for (String player : players) {
            if (!mafiaTeam.contains(player)) {
                citizenTeam.add(player);
            }
        }
    }

    public Doctor getDoctor() {
        return doctor;
    }
    public Police getPolice() {
        return police;
    }
    public Mafia getMafia() {
        return mafia;
    }
    public Mafia getMafia2() {
        return mafia2;
    }

    public void showDetectResult(ArrayList<String> mafiaTeam, String policeTarget){
        if (mafiaTeam.contains(policeTarget)) {
            System.out.println(">> " + policeTarget + "님은 마피아입니다.");
        }
        else {
            System.out.println(">> " + policeTarget + "님은 마피아가 아닙니다.");
        }
    }

    public void showHealResult(String doctorTarget, String mafiaTarget, ArrayList<String> players, ArrayList<String> deadPlayers, ArrayList<String> citizenTeam){
        if (mafiaTarget.equals(doctorTarget)) {
            System.out.println(">> 👼 의사의 치료로 인해 " + mafiaTarget + "님이 살아났습니다.");
        } else {
            System.out.println(">> 🩸 " +mafiaTarget + "님이 죽었습니다.");
            players.remove(mafiaTarget);
            citizenTeam.remove(mafiaTarget);
            deadPlayers.add(mafiaTarget);
        }
    }




    public void compareNumOfMafiaAndCitizen(ArrayList<String> mafiaTeam, ArrayList<String> citizenTeam ){
        if(citizenTeam.size() <= mafiaTeam.size()){
            System.out.println("마피아의 수가 시민수와 같거나 많습니다. 마피아 팀의 승리입니다!");
            System.exit(0);
        } else if (mafiaTeam.size() == 0) {
            System.out.println("마피아가 모두 사망했습니다. 시민팀의 승리입니다!");
            System.exit(0);
        }
    }
}

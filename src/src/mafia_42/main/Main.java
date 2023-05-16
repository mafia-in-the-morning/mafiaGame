package mafia_42.main;

import mafia_42.game.*;
import mafia_42.player.*;

import java.sql.SQLOutput;
import java.util.*;
import java.lang.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean night = true; //밤으로 시작되는 게임

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> deadPlayers = new ArrayList<>();
        ArrayList<Player> citizenTeam = new ArrayList<>();
        ArrayList<Player> mafiaTeam = new ArrayList<>();

        Game game = new Game();
        //Meeting meeting = new Meeting();
        int numOfPlayers = game.setNumberOfPeople();

        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("참가자 " + (i + 1) + "의 이름을 입력하세요: ");
            String name = scanner.nextLine();
            Player player = new Player(name);
            players.add(player);
        }

        //역할 중복 없도록 미리 숫자 처리
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



        // 마피아, 의사, 경찰 랜덤 선택(Player ArrayList에서 랜덤 번호로 뽑은 후 이름을 얻어 객체 생성 후 역할 지정)
        Mafia mafia = new Mafia(players.get(mafiaNum).getName());
        mafia.setRole("마피아");
        System.out.println(mafia.getRole() + ": " + mafia.getName() + "입니다.");
        mafiaTeam.add(mafia);
        //System.out.println("마피아팀 ArrayList에 추가되었습니다." + mafiaTeam.get(0).getName());

        Mafia mafia2 = null; // 초기화
        // 6명 이상일 경우 추가 마피아 랜덤 선택
        if (numOfPlayers >= 6) {
            mafia2 = new Mafia(players.get(mafiaNum2).getName());
            mafia2.setRole("마피아");
            System.out.println(mafia2.getRole() + ": " + mafia2.getName() + "입니다.");
            mafiaTeam.add(mafia2);
            players.add(mafia2);
        }

        Doctor doctor = new Doctor(players.get(doctorNum).getName());
        doctor.setRole("의사");
        System.out.println(doctor.getRole() + ": " + doctor.getName() + "입니다.");
        citizenTeam.add(doctor);
        //System.out.println("시민팀 ArrayList에 추가되었습니다." + citizenTeam.get(0).getName());

        Police police = new Police(players.get(policeNum).getName());
        police.setRole("경찰");
        System.out.println(police.getRole() + ": " + police.getName() + "입니다.");
        citizenTeam.add(police);
        //System.out.println("시민팀 ArrayList에 추가되었습니다." + citizenTeam.get(1).getName());

        System.out.print("게임 참가자: [");
        for (Player value : players) {
            System.out.print( value.getName() + ", ");
        }
        System.out.println("] ");

        System.out.println("마피아: " + mafia.getName());

        if (numOfPlayers >= 6) {
            System.out.println("마피아2: " + mafia2.getName());
        }
        System.out.println("의사: " + doctor.getName());
        System.out.println("경찰: " + police.getName());


        int round = 1;

        Player mafiaTarget = null;
        Player doctorTarget = null;
        Player policeTarget = null;

        while (night) {
            System.out.println("🌙 " + round + "번째 밤이 되었습니다. 직업을 가진 사람들의 활동이 시작됩니다.");

            // 마피아가 죽일 대상을 선택합니다.(마피아가 선택한 player 객체의 이름(String)을 가져옵니다.
            mafiaTarget = new Player(mafia.selectTarget(players, deadPlayers).getName());
            // 의사가 살릴 대상을 선택합니다.
            doctorTarget = new Player(doctor.selectTarget(players, deadPlayers, doctor).getName());
            // 경찰이 조사할 대상을 선택합니다.
            if (!deadPlayers.contains(police)) {
                policeTarget = new Player(police.detectMafia(players, deadPlayers, police).getName());

                // 경찰이 선택한 대상이 마피아인지 아닌지 확인합니다.
                if (mafia.equals(policeTarget)) {
                    System.out.println(policeTarget + "님은 마피아입니다.");
                } else {
                    System.out.println(policeTarget + "님은 마피아가 아닙니다.");
                }
            } else {
                System.out.println("경찰이 사망했으므로 경찰은 역할을 수행할 수 없습니다.");
            }

            // 마피아와 의사가 선택한 대상이 같은 경우
            if (mafiaTarget.getName().equals(doctorTarget.getName())) {
                System.out.println("의사의 치료로 인해 " + mafiaTarget + "님이 살아났습니다.");
            } else {
                System.out.println(mafiaTarget + "님이 죽었습니다.");
                players.remove(mafiaTarget);
                deadPlayers.add(mafiaTarget);
            }
            game.compareNumofMafiaAndCitizen(citizenTeam, mafiaTeam);
            break;
        }
        night = false;

        while (!night) {
            // 낮이 되었을 때
            System.out.println("☀️ " + round + "번째 낮이 되었습니다. 3분간 회의를 진행합니다.");
            Meeting meeting = new Meeting(players);
            Thread meetingThread = new Thread(meeting.toString());
            meetingThread.start();
            try {
                Thread.sleep(3 * 1000); // 3분 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 투표시간
            System.out.println("\n=== 투표 시간 ===");

            HashMap<String, Integer> votes = new HashMap<>();
            for (Player player : players) {
                if (deadPlayers.contains(player)) {
                    continue; // 이미 죽은 참가자는 건너뜀
                }

                boolean voted = false;
                while (!voted) {
                    System.out.print(player + "님, 투표하실 분을 입력하세요: ");
                    String vote = scanner.nextLine();

                    if (deadPlayers.contains(vote)) {
                        System.out.println("죽은 참가자에게는 투표할 수 없습니다. 다시 입력해주세요.");
                        continue; // 죽은 참가자에 대한 투표 불가
                    }

                    votes.put(vote, votes.getOrDefault(vote, 0) + 1);
                    voted = true;
                }
            }
        }
    }
}




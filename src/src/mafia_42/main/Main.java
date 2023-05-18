package mafia_42.main;

import mafia_42.game.AsciiArtPrinter;
import mafia_42.game.Game;
import mafia_42.game.Meeting;
import mafia_42.player.Doctor;
import mafia_42.player.Mafia;
import mafia_42.player.Police;

import java.util.*;
import java.lang.*;

import static mafia_42.game.Game.setPlayers;

public class Main {
    public static void main(String[] args) {
        //필요한 클래스 로드
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Game game = new Game();
        AsciiArtPrinter.startLogo();


        ArrayList<String> deadPlayers = new ArrayList<>();
        ArrayList<String> mafiaTeam = new ArrayList<>();
        ArrayList<String> players = new ArrayList<>();

        //참가자 인원 수 입력하기
        int numOfPlayers = game.setNumberOfPeople();

        // 참가자 이름 입력 받기
        players = setPlayers(numOfPlayers);

        Mafia mafia = null;
        Mafia mafia2 = null;
        Doctor doctor = null;
        Police police = null;

        //마피아, 경찰, 의사 역할 할당 후 출력
        Game.assignRoles(players, mafiaTeam, numOfPlayers, mafia, mafia2, doctor, police);

        int Round = 1;
        while (true) {
            System.out.println("\nRound " + Round);
            // 밤이 되었습니다.
            System.out.println("밤이 되었습니다.");

            // 마피아가 죽일 대상을 선택합니다.
            //mafia가 null일 경우 대비해 assert false; 추가
            assert false;
            String mafiaTarget = mafia.selectTarget(players, deadPlayers, mafiaTeam);

            // 의사가 살릴 대상을 선택합니다.
            String doctorTarget = doctor.selectPatient(players, deadPlayers, doctor);

            // 경찰이 조사할 대상을 선택합니다.
            String policeTarget = police.detectMafia(players, deadPlayers, police);




            // 마피아와 의사가 선택한 대상이 같은 경우
            if (mafiaTarget.equals(doctorTarget)) {
                System.out.println("의사의 치료로 인해 " + mafiaTarget + "님이 살아났습니다.");
            } else {
                System.out.println(mafiaTarget + "님이 죽었습니다.");
                players.remove(mafiaTarget);
                deadPlayers.add(mafiaTarget);
            }
            if((players.size()<=2&&(players.contains(mafia)||players.contains(mafia2)))||
                    (players.size()<=4&&(players.contains(mafia)&&players.contains(mafia2)))
            ){
                System.out.println("시민의 수보다 마피아의 수가 같거나 많습니다! 마피아의 승리입니다!");
                break;
            }

            // 낮이 되었을 때
            System.out.println("\n낮이 되었습니다.");
            System.out.println("3분간 회의를 진행합니다.");
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
            System.out.println(players);
            HashMap<String, Integer> votes = new HashMap<>(); // 각 참가자별 득표수를 저장할 HashMap

            for (String player : players) {
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


            // 최다 득표수를 얻은 참가자를 구함
            int maxVoteCount = 0;
            String maxVotePlayer = "";
            for (Map.Entry<String, Integer> entry : votes.entrySet()) {
                String player = entry.getKey();
                int voteCount = entry.getValue();
                if (voteCount > maxVoteCount) {
                    maxVoteCount = voteCount;
                    maxVotePlayer = player;
                } else if (voteCount == maxVoteCount) { // 득표수가 같을 경우 무효 표시
                    maxVotePlayer = "무효";
                }
            }

            // 최다 득표수를 얻은 참가자가 있을 경우 해당 참가자 제거 후 결과 출력
            if (!maxVotePlayer.equals("무효")) {
                System.out.println("\n" + maxVotePlayer + "님이 최다 득표수(" + maxVoteCount + "표)를 얻어 처형됩니다.");
                players.remove(maxVotePlayer);
                deadPlayers.add(maxVotePlayer);
                if(deadPlayers.contains(mafia)&&deadPlayers.contains(mafia2)){
                    System.out.println("마피아를 모두 검거했습니다! 시민의 승리입니다!");
                    break;
                }
                else if((players.size()<=2&&(players.contains(mafia)||players.contains(mafia2)))||
                        (players.size()<=4&&(players.contains(mafia)&&players.contains(mafia2)))){
                    System.out.println("시민의 수보다 마피아의 수가 같거나 많습니다! 마피아의 승리입니다!");
                    break;
                }


            } else { // 무효 표시가 된 경우 결과 출력
                System.out.println("\n투표가 무효 처리되었습니다. 동점이거나 모든 참가자가 투표를 거부했습니다.");
            }

            Round++;

        }
    }
}




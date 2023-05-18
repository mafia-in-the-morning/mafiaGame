package mafia_42.main;

import mafia_42.game.AsciiArtPrinter;
import mafia_42.game.Game;
import mafia_42.game.Meeting;
import mafia_42.game.Voting;
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

        //참가자 인원 수 입력하기
        int numOfPlayers = game.setNumberOfPeople();

        // 참가자 이름 입력 받기
        ArrayList<String> players = setPlayers(numOfPlayers);
        ArrayList<String> deadPlayers = new ArrayList<>();
        ArrayList<String> citizenTeam = new ArrayList<>();
        ArrayList<String> mafiaTeam = new ArrayList<>();

        //마피아, 경찰, 의사 객체 미리 생성
        Mafia mafia = null;
        Mafia mafia2 = null;
        Doctor doctor = null;
        Police police = null;

        int round = 1;

        System.out.println(" ");
        System.out.println("☀️" + round + "번째 낮은 직업을 배정합니다.");
        System.out.println("======================================");

        //생성된 마피아, 경찰, 의사 객체에 역할 할당
        game.assignRoles(players, mafiaTeam, citizenTeam);

        mafia = game.getMafia();
        police = game.getPolice();
        doctor = game.getDoctor();

        if(numOfPlayers >= 6){
            mafia2 = game.getMafia2();
        }

        while (true) {
            // 밤이 되었습니다.

            System.out.println("======================================");
            System.out.println("🌙" + round + "번째 밤이 되었습니다.");

            // 마피아가 죽일 대상을 선택합니다.
            String mafiaTarget = mafia.selectTarget(players, deadPlayers, mafiaTeam);

            // 의사가 살릴 대상을 선택합니다.
            String doctorTarget = doctor.selectPatient(players, deadPlayers, doctor);

            // 경찰이 조사할 대상을 선택합니다.
            String policeTarget = police.detectMafia(players, deadPlayers, police);

            //경찰 조사 결과 출력
            game.showDetectResult(mafiaTeam, policeTarget);

            //의사 치료 결과 출력
            game.showHealResult(doctorTarget, mafiaTarget, players, deadPlayers, citizenTeam);

            //시민팀과 마피아팀 수 비교
            game.compareNumOfMafiaAndCitizen(mafiaTeam, citizenTeam);
            round++;
            // 낮이 되었을 때

            System.out.println("======================================");
            System.out.println("☀️" + round + "번째 낮이 되었습니다.");
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

            // 투표 시스템 호출
            Voting voting = new Voting();
            HashMap<String, Integer> votes = voting.conductVoting(players, deadPlayers);

            // 투표 결과 출력
            System.out.println("\n=== 투표 결과 ===");
            for (String player : votes.keySet()) {
                System.out.println(player + ": " + votes.get(player) + "표");
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
                //마피아팀인 경우 마피아 리스트에서 삭제, 시민인 경우 시민 리스트에서 삭제
                if (mafiaTeam.contains(maxVotePlayer)) {
                    mafiaTeam.remove(maxVotePlayer);
                } else {
                    citizenTeam.remove(maxVotePlayer);
                }
                deadPlayers.add(maxVotePlayer);
                game.compareNumOfMafiaAndCitizen(mafiaTeam, citizenTeam);
            }else { // 무효 표시가 된 경우 결과 출력
                System.out.println("\n투표가 무효 처리되었습니다. 동점이거나 모든 참가자가 투표를 거부했습니다.");
            }
        }
    }
}




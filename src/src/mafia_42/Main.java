package mafia_42;

import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        AsciiArtPrinter.startLogo();

        // 참가자 이름 입력 받기
        System.out.println("게임에 참가하는 인원 수를 입력하세요(최소 4명, 최대 8명):");
        int numOfPlayers = scanner.nextInt();
        scanner.nextLine(); // 개행문자 제거


        while (numOfPlayers < 4 || numOfPlayers > 8) {
            System.out.println("잘못된 입력입니다. 최소 5명, 최대 8명까지 입력해주세요.");
            System.out.println("게임에 참가하는 인원 수를 입력하세요(최소 4명, 최대 8명):");
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
        String mafia2="";
        // 중복되는 역할이 없도록 while문 추가
        while (mafia.equals(doctor) || mafia.equals(police) || doctor.equals(police)) {
            doctor = players.get(random.nextInt(numOfPlayers));
            police = players.get(random.nextInt(numOfPlayers));
        }

        // 6명 이상일 경우 추가 마피아 랜덤 선택
        if (numOfPlayers >= 6) {
             mafia2= players.get(random.nextInt(numOfPlayers));
            while (mafia2.equals(mafia) || mafia2.equals(doctor) || mafia2.equals(police)) {
                mafia2 = players.get(random.nextInt(numOfPlayers));
            }

        }
if(numOfPlayers<6) {
    System.out.println("게임 참가자: " + players);
    System.out.println("마피아: " + mafia);
    System.out.println("의사: " + doctor);
    System.out.println("경찰: " + police);
}
if(numOfPlayers>=6){
    System.out.println("게임 참가자: " + players);
    System.out.println("마피아1: " + mafia);
    System.out.println("마피아2: "+mafia2);
    System.out.println("의사: " + doctor);
    System.out.println("경찰: " + police);
}

        int Round = 1;
        while (true) {
            System.out.println("\nRound " + Round);
            // 밤이 되었습니다.
            System.out.println("밤이 되었습니다.");

            // 마피아가 죽일 대상을 선택합니다.
            String mafiaTarget = "";
            while (true) {
                AsciiArtPrinter.mafiaLogo();
                System.out.print("마피아는 누구를 죽이겠습니까? ");
                System.out.println("\n현재 살아있는 인원 : " + players);
                mafiaTarget = scanner.nextLine();
                if (players.contains(mafiaTarget) && !deadPlayers.contains(mafiaTarget)) {
                    if(mafiaTarget.equals(mafia)||mafiaTarget.equals(mafia2)){
                        System.out.println("마피아는 마피아를 죽일 수 없습니다. 다시 선택해주세요.");
                    }else{
                        break;}

                } else if (deadPlayers.contains(mafiaTarget)) {
                    System.out.println("이미 죽은 플레이어입니다. 다시 선택해주세요.");
                } else {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }

            }
            // 의사가 살릴 대상을 선택합니다.
            String doctorTarget = "";
            if (!deadPlayers.contains(doctor)) {
                doctorTarget = "";
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
            } else {
                System.out.println("의사가 이미 사망했으므로 의사는 역할을 수행할 수 없습니다.");
            }
            // 경찰이 조사할 대상을 선택합니다.
            if (!deadPlayers.contains(police)) {
                String policeTarget = "";
                while (true) {
                    System.out.print("경찰은 누구를 조사하겠습니까? ");
                    policeTarget = scanner.nextLine();
                    if (players.contains(policeTarget) && !policeTarget.equals(police) && !deadPlayers.contains(policeTarget)) {
                        break;
                    } else if (policeTarget.equals(police)) {
                        System.out.println("경찰은 자신을 조사할 수 없습니다. 다시 선택해주세요.");
                    } else if (deadPlayers.contains(policeTarget)) {
                        System.out.println(policeTarget + "님은 이미 죽은 상태입니다. 다시 선택해주세요.");
                    } else {
                        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    }
                }
                // 경찰이 선택한 대상이 마피아인지 아닌지 확인합니다.
                if (mafia.equals(policeTarget)) {
                    System.out.println(policeTarget + "님은 마피아입니다.");
                }else if(mafia2.equals(policeTarget)){
                    System.out.println(policeTarget+"님은 마피아입니다.");
                }
                else {
                    System.out.println(policeTarget + "님은 마피아가 아닙니다.");
                }
            } else {
                System.out.println("경찰이 사망했으므로 경찰은 역할을 수행할 수 없습니다.");
            }


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




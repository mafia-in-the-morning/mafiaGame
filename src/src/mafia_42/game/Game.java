package mafia_42.game;

import mafia_42.player.Doctor;
import mafia_42.player.Mafia;
import mafia_42.player.Player;
import mafia_42.player.Police;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);

    public int setNumberOfPeople(){
        // 참가자 이름 입력 받기
        System.out.print("게임에 참가하는 인원 수를 입력하세요(최소 4명, 최대 8명): ");
        int numOfPlayers = scanner.nextInt();

        while (numOfPlayers < 4 || numOfPlayers > 8) {
            System.out.println("잘못된 입력입니다. 최소 5명, 최대 8명까지 입력해주세요.");
            System.out.print("게임에 참가하는 인원 수를 입력하세요(최소 4명, 최대 8명): ");
            numOfPlayers = scanner.nextInt();
        }
        //게임에 참여하는 인원수 리턴
        return numOfPlayers;
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

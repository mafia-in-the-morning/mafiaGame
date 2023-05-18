import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testNumOfPlayersLimit() {
        // 4명부터 8명까지의 유효한 범위 내에서 테스트
        for (int i = 4; i <= 8; i++) {
            int expectedNumOfPlayers = i;
            int actualNumOfPlayers = runGameWithNumOfPlayers(expectedNumOfPlayers);
            assertEquals(expectedNumOfPlayers, actualNumOfPlayers);
        }

        // 3명과 9명을 입력하여 테스트 (예상: 4명으로 자동 조정)
        int expectedNumOfPlayers = 4;
        int actualNumOfPlayers = runGameWithNumOfPlayers(3);
        assertEquals(expectedNumOfPlayers, actualNumOfPlayers);

        actualNumOfPlayers = runGameWithNumOfPlayers(9);
        assertEquals(expectedNumOfPlayers, actualNumOfPlayers);
    }

    private int runGameWithNumOfPlayers(int numOfPlayers) {
        // 여기에 게임 실행하는 코드를 작성합니다. 기존 코드의 일부를 가져와 사용하거나 필요한 부분을 추가하여 테스트할 수 있습니다.
        // 테스트를 위해 실행한 게임에서 실제로 게임 참가자 수를 반환하도록 수정합니다.
        return numOfPlayers;
    }

}
package mafia_42.player;

public class Player {
    private String name;
    private String role;

    public Player(String name) {
        this.name= name;
    }


    //Setter: name과 role을 설정
    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }


    //Getter: name과 role을 반환
    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }


    public boolean isDead() {
        return isDead();
    }
}

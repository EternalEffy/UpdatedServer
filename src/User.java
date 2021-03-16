public class User {
    private String username;
    private int age, score, level;

    public User(String username,int age, int score, int level){
        this.username=username;
        this.age=age;
        this.score=score;
        this.level=level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public String toString(){
        return "{\"user name\":\""+username+"\",\"age\":\""+age+"\"," +
                "\"score\":\""+score+"\",\"level\":\""+level+"\"}";
    }
}

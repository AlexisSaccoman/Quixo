package plateau;

public class Pion {

    private String state = "B";

    public Pion(){}

    public Pion(String s){
        this.state = s;
    }

    public String getState() {
        return state;
    }

    public void setStateB() {
        this.state = "B";
    }

    public void setStateO() {
        this.state = "O";
    }

    public void setStateX() {
        this.state = "X";
    }
}

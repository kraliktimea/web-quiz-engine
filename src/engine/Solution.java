package engine;

import java.util.ArrayList;

public class Solution {
    private ArrayList<Integer> answer;

    public Solution() {
    }

    public Solution(ArrayList<Integer> chosenOptions) {
        this.answer = chosenOptions;
    }

    public ArrayList<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<Integer> answer) {
        this.answer = answer;
    }

}

package engine;

public class Answer {

    public final static Answer CORRECT_ANSWER = new Answer(true, "Congratulations, you're right!");
    public final static Answer WRONG_ANSWER = new Answer(false, "Wrong answer! Please, try again.");

    private boolean success;
    private String feedback;

    public Answer(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

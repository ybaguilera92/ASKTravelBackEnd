package cu.sitrans.asktravel.payload.response;

public class MessageResponse {

    private String code;
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
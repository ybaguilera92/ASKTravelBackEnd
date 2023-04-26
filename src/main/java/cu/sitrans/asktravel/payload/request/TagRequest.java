package cu.sitrans.asktravel.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TagRequest {
    @NotBlank
    @Size(min = 3, message = "Tag should not be less than 3")
    String title;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NotBlank
    String color;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

package cu.sitrans.asktravel.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class StatusDTO {
    @NotBlank
    @Size(min = 3, message = "Status should not be less than 3")
    String title;

    @NotBlank
    String icon;

    Boolean showIcon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getShowIcon() {
        return showIcon;
    }

    public void setShowIcon(Boolean showIcon) {
        this.showIcon = showIcon;
    }
}

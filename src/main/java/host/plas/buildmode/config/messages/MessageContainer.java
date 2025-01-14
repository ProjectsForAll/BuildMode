package host.plas.buildmode.config.messages;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageContainer {
    private MessageString toggleString;
    private MessageString cannotBuildString;

    private MessageString statusEnabledString;
    private MessageString statusDisabledString;

    private MessageString errorNoPermissionString;

    public MessageContainer() {
        this.toggleString = new MessageString();
        this.cannotBuildString = new MessageString();

        this.statusEnabledString = new MessageString();
        this.statusDisabledString = new MessageString();

        this.errorNoPermissionString = new MessageString();
    }

    public void setToggleString(String string) {
        this.toggleString = MessageString.as(string);
    }

    public void setCannotBuildString(String string) {
        this.cannotBuildString = MessageString.as(string);
    }

    public void setStatusEnabledString(String string) {
        this.statusEnabledString = MessageString.as(string);
    }

    public void setStatusDisabledString(String string) {
        this.statusDisabledString = MessageString.as(string);
    }

    public void setErrorNoPermissionString(String string) {
        this.errorNoPermissionString = MessageString.as(string);
    }
}

package host.plas.buildmode.config.messages;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageString {
    private String string;

    public MessageString(String string) {
        this.string = string;
    }

    public MessageString() {
        this("");
    }

    public boolean isEmpty() {
        return string.isEmpty();
    }

    public static MessageString as(String string) {
        return new MessageString(string);
    }
}

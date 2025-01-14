package host.plas.buildmode.data;

import lombok.Getter;
import lombok.Setter;
import tv.quaint.objects.Identifiable;

import java.util.concurrent.ConcurrentSkipListSet;

@Getter @Setter
public class StringedList implements Identifiable {
    private String identifier;

    private boolean blacklist;
    private ConcurrentSkipListSet<String> strings;

    public StringedList(String identifier, boolean blacklist, ConcurrentSkipListSet<String> strings) {
        this.identifier = identifier;

        this.blacklist = blacklist;
        this.strings = strings;
    }

    public StringedList(String identifier) {
        this(identifier, false, new ConcurrentSkipListSet<>());
    }

    public boolean isWhitelist() {
        return ! isBlacklist();
    }
}

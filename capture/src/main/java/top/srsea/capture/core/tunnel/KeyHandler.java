package top.srsea.capture.core.tunnel;

import java.nio.channels.SelectionKey;

public interface KeyHandler {
    void onKeyReady(SelectionKey key);
}

package top.srsea.capture.core.nat;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import top.srsea.capture.core.util.android.PortHostService;
import top.srsea.capture.core.util.common.ACache;
import top.srsea.capture.core.util.common.FileManager;
import top.srsea.capture.core.util.common.TimeFormatter;
import top.srsea.capture.core.vpn.VpnServiceProxy;
import top.srsea.capture.core.util.net.TcpDataSaver;

public class NatSessionHelper {
    public static Collection<NatSession> getAllSessions() {
        File file = new File(TcpDataSaver.CONFIG_DIR
                + TimeFormatter.formatToYYMMDDHHMMSS(VpnServiceProxy.getStartTime()));
        ACache aCache = ACache.get(file);
        String[] list = file.list();
        ArrayList<NatSession> baseNetSessions = new ArrayList<>();
        if (list != null) {
            for (String fileName : list) {
                NatSession netConnection = (NatSession) aCache.getAsObject(fileName);
                baseNetSessions.add(netConnection);
            }
        }
        PortHostService portHostService = PortHostService.getInstance();
        if (portHostService != null) {
            Collection<NatSession> aliveConnInfo = portHostService.getAndRefreshSessionInfo();
            if (aliveConnInfo != null) {
                baseNetSessions.addAll(aliveConnInfo);
            }
        }
        Collections.sort(baseNetSessions, new Comparator<NatSession>() {
            @Override
            public int compare(NatSession o1, NatSession o2) {
                return Long.compare(o2.lastRefreshTime, o1.lastRefreshTime);
            }
        });
        return baseNetSessions;
    }

    public static void clearCache() {
        String data = TcpDataSaver.DATA_DIR;
        String config = TcpDataSaver.CONFIG_DIR;
        File dataDir = new File(data);
        File configDir = new File(config);
        FileManager.deleteUnder(dataDir);
        FileManager.deleteUnder(configDir);
        NatSessionManager.clearAllSession();
    }
}

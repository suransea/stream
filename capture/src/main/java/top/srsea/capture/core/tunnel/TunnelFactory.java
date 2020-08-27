package top.srsea.capture.core.tunnel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import top.srsea.capture.core.nat.NatSessionManager;
import top.srsea.capture.core.nat.NatSession;

public class TunnelFactory {

    public static BaseTcpTunnel wrap(SocketChannel channel, Selector selector) {
        BaseTcpTunnel tunnel = new RawTcpTunnel(channel, selector);
        NatSession session = NatSessionManager.getSession((short) channel.socket().getPort());
        if (session != null) {
            tunnel.setIsHttpsRequest(session.isHttpsSession);
        }
        return tunnel;
    }

    public static BaseTcpTunnel createTunnelByConfig(InetSocketAddress destAddress,
                                                     Selector selector,
                                                     short portKey) throws IOException {
        return new RemoteTcpTunnel(destAddress, selector, portKey);
    }
}

package me.j360.disboot.websocket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.junit.Test;

import java.net.URISyntaxException;

/**
 * @author: min_xu
 * @date: 2019/2/15 10:59 AM
 * 说明：
 */
public class SocketClientTest {

    @Test
    public void testSocketClient() throws URISyntaxException {
        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = false;
        opts.secure = true;

        Socket socket = IO.socket("http://localhost", opts);
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                socket.emit("foo", "hi");
                socket.disconnect();
            }

        }).on("event", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.println(args);
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.println(args);
            }

        });
        socket.connect();

    }
}

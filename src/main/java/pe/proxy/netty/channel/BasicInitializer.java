package pe.proxy.netty.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * BasicInitializer
 *
 * @author Kai
 * @version 1.0, 19/05/2022
 */
public class BasicInitializer extends ChannelInitializer<SocketChannel> {

    private final BasicHandler handler;

    public BasicInitializer(BasicHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast("decoder", new BasicDecoder())
                .addLast("encoder", new BasicEncoder())
                .addLast("idle", new IdleStateHandler(5,5,5, TimeUnit.SECONDS))
                .addLast("handler", handler);
    }
}

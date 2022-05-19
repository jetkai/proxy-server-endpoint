package pe.proxy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pe.proxy.netty.channel.BasicHandler;
import pe.proxy.netty.channel.BasicInitializer;

/**
 * BasicBinder
 *
 * @author Kai
 * @version 1.0, 19/05/2022
 */
@Component
public class BasicBinder implements ApplicationListener<ApplicationReadyEvent> {

    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initialize();
    }

    public void initialize() {
        BasicHandler basicHandler = new BasicHandler();
        BasicInitializer basicInitializer = new BasicInitializer(basicHandler);
        bind(basicInitializer);
    }

    public void bind(ChannelInitializer<SocketChannel> initializer) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(initializer)
                .option(ChannelOption.SO_BACKLOG, 65535)
                .bind(43594);
    }
}

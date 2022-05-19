package pe.proxy.netty.channel;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;

/**
 * BasicHandler
 *
 * @author Kai
 * @version 1.0, 19/05/2022
 */
@ChannelHandler.Sharable
public class BasicHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof Protocol) {
            if(msg == Protocol.GAME_SERVER) {
                ctx.writeAndFlush(msg);
            } else {
                flushAndClose(ctx.channel());
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        flushAndClose(ctx.channel());
    }

    public void flushAndClose(Channel channel) {
        if(channel.isActive()) {
            channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

}

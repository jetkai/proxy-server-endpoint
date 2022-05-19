package pe.proxy.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * BasicEncoder
 *
 * @author Kai
 * @version 1.0, 19/05/2022
 */
public class BasicEncoder extends MessageToMessageEncoder<Object> {

    private final Logger logger = LoggerFactory.getLogger(BasicEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        ByteBuf buffer = Unpooled.buffer(100);

        int[] values = { 0xFFFFFF, 0x000000, 0xFF00FF, 0x00FF00, 0xFFFF00, 0x00FFFF };

        for(int i = 0; i < values.length; i++) {
            buffer.writeInt(values[i]);
        }
        out.add(buffer);

        logger.info("Encoding buffer for " + ctx.channel().remoteAddress());
    }
}

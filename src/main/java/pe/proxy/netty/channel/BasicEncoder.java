package pe.proxy.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
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
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) {
        int[] values = { 0xFFFFFF, 0x000000, 0xFF00FF, 0x00FF00, 0xFFFF00, 0x00FFFF };

        ByteBuf buffer = Unpooled.buffer(1000); //1KB Buffer

        for (int value : values) {
            buffer.writeInt(value);
        }

        //Write Remote Address
        byte[] ipAddress = null;
        SocketAddress remoteAddress = ctx.channel().remoteAddress();
        if(remoteAddress instanceof InetSocketAddress) {
            InetAddress inetAddress = ((InetSocketAddress) remoteAddress).getAddress();
            if(inetAddress instanceof Inet4Address) {
                ipAddress = inetAddress.getAddress();
            } else if(inetAddress instanceof Inet6Address) {
                ipAddress = inetAddress.getAddress();
            }
        }
        if(ipAddress != null) {
            buffer.writeBytes(ipAddress);
        }
        buffer.writeByte((byte) 0);

        out.add(buffer);

        logger.info("Encoding buffer for " + ctx.channel().remoteAddress());
    }
}

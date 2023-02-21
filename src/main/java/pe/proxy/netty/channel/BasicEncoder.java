package pe.proxy.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.nio.charset.StandardCharsets;
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
        ByteBuf buffer = Unpooled.buffer(1000); //1KB Buffer

        //Write a few random values, we will confirm these values match on the client's end
        int[] values = { 0xFFFFFF, 0x000000, 0xFF00FF, 0x00FF00, 0xFFFF00, 0x00FFFF };
        for (int value : values) {
            buffer.writeInt(value);
        }

        //Write remote address, we will confirm that the proxy address matches the one sent from the client
        String ipAddress = null;
        SocketAddress remoteAddress = ctx.channel().remoteAddress();
        if(remoteAddress instanceof InetSocketAddress) {
            InetAddress inetAddress = ((InetSocketAddress) remoteAddress).getAddress();
            if(inetAddress instanceof Inet4Address) {
                ipAddress = inetAddress.getHostAddress();
            } else if(inetAddress instanceof Inet6Address) {
                ipAddress = inetAddress.getHostAddress();
            }
        }
        if(ipAddress != null) {
            buffer.writeBytes(ipAddress.getBytes(StandardCharsets.UTF_8));
            buffer.writeByte((byte) 0);
        }

        out.add(buffer);

        logger.info("Encoding buffer for " + ctx.channel().remoteAddress());
    }

}

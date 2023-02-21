package pe.proxy.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * BasicDecoder
 *
 * @author Kai
 * @version 1.0, 19/05/2022
 */
public class BasicDecoder extends ByteToMessageDecoder {

    private final Logger logger = LoggerFactory.getLogger(BasicDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
       if(buffer.isReadable()) {
           short nextByte = buffer.readUnsignedByte();

           Protocol protocol = Protocol.getById(nextByte);
           out.add(protocol);

           if(buffer.isReadable()) {
               ByteBuf bytes = buffer.readBytes(buffer.readableBytes());
               out.add(bytes);
           } else {
               out.add(nextByte);
           }

           logger.info("Decoding buffer from " + ctx.channel().remoteAddress());
       }
    }
}

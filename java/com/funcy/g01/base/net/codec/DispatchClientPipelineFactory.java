package com.funcy.g01.base.net.codec;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelUpstreamHandler;

import com.ebo.synframework.nettybase.codec.LengthFieldBasedFrameDecoder;
import com.ebo.synframework.nettybase.codec.ProtoDecoder;
import com.ebo.synframework.nettybase.codec.ProtoEncoder;

public class DispatchClientPipelineFactory implements ChannelPipelineFactory {

    private static final int DEFAULT_MAX_FRAME_LENGTH = 81920;

    private static final int DEFAULT_LENGTH_FIELD_LENGTH = 4;
    
    private ChannelUpstreamHandler gameMsgHandler;    
    
    public DispatchClientPipelineFactory(ChannelUpstreamHandler gameMsgHandler) {
        this.gameMsgHandler = gameMsgHandler;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(DEFAULT_MAX_FRAME_LENGTH, 0,
                DEFAULT_LENGTH_FIELD_LENGTH, 0, DEFAULT_LENGTH_FIELD_LENGTH, true, false));
        pipeline.addLast("decoder", new DispatchClientProtoDecoder());
        pipeline.addLast("encoder", new DispatchClientProtoEncoder());
        pipeline.addLast("handler", gameMsgHandler);
        return pipeline;
    }
}

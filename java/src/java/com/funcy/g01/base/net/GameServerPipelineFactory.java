package com.funcy.g01.base.net;

import static org.jboss.netty.channel.Channels.pipeline;

import java.io.File;
import java.net.URL;

import javax.net.ssl.SSLEngine;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.handler.ssl.SslContext;
import org.jboss.netty.handler.ssl.SslHandler;

import com.ebo.synframework.nettybase.codec.LengthFieldBasedFrameDecoder;
import com.ebo.synframework.nettybase.codec.LengthOptimizeConfig;
import com.ebo.synframework.nettybase.codec.ProtoDecoder;
import com.ebo.synframework.nettybase.codec.ProtoEncoder;
import com.funcy.g01.base.global.ServerConfig;

public class GameServerPipelineFactory implements ChannelPipelineFactory {

    private static final int DEFAULT_MAX_FRAME_LENGTH = 81920;

    private static final int DEFAULT_LENGTH_FIELD_LENGTH = 4;
    
    private ChannelUpstreamHandler gameMsgHandler;    
    
    public GameServerPipelineFactory(ChannelUpstreamHandler gameMsgHandler) {
        this.gameMsgHandler = gameMsgHandler;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(DEFAULT_MAX_FRAME_LENGTH, 0,
                DEFAULT_LENGTH_FIELD_LENGTH, 0, DEFAULT_LENGTH_FIELD_LENGTH, true, false));
       
        LengthOptimizeConfig lengthOptimizeConfig = ServerConfig.getLengthOptimizeConfig();
        pipeline.addLast("decoder", new ProtoDecoder(lengthOptimizeConfig));
        pipeline.addLast("encoder", new ProtoEncoder(lengthOptimizeConfig));
        pipeline.addLast("handler", gameMsgHandler);
        return pipeline;
    }
}

package com.funcy.g01.base.net;

import static org.jboss.netty.channel.Channels.pipeline;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.jboss.netty.handler.ssl.SslHandler;

public class WebSocketGameServerPipelineFactory implements ChannelPipelineFactory {

    private static final int DEFAULT_MAX_FRAME_LENGTH = 81920;

    private static final int DEFAULT_LENGTH_FIELD_LENGTH = 4;
    
    private ChannelUpstreamHandler gameMsgHandler;
    
    private SSLContext sslContext;
    
    public WebSocketGameServerPipelineFactory(ChannelUpstreamHandler gameMsgHandler) {
        this.gameMsgHandler = gameMsgHandler;
        KeyStore ks;
		try {
			ks = KeyStore.getInstance("JKS");
			InputStream is = WebSocketGameServerPipelineFactory.class.getClassLoader().getResourceAsStream("server.jks");
	        ks.load(is, "Zfz2018123".toCharArray());
	        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	        kmf.init(ks, "214668282010367".toCharArray());
	        SSLContext sslContext = SSLContext.getInstance("TLS");
	        sslContext.init(kmf.getKeyManagers(), null, null);
	        this.sslContext = sslContext;
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();
        SSLEngine sslEngine = sslContext.createSSLEngine();
        sslEngine.setUseClientMode(false);
        pipeline.addFirst("ssl", new SslHandler(sslEngine));
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("webHandler", new WebSocketServerProtocolHandler("/g01"));
        pipeline.addLast("handler", gameMsgHandler);
        return pipeline;
    }
}

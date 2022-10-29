package org.vinogradov.myserver.serverLogic.ConnectionsService;

import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.mydto.responses.ConnectionLimitResponse;

import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class ConnectionLimit {

    private ChannelHandlerContext context;
    private TimerTask timerTask;

    public ConnectionLimit(ChannelHandlerContext context) {
        this.context = context;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    sleep(10000);
                    closeConnection();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timerTask.run();
    }

    private void closeConnection() {
        context.channel().writeAndFlush(new ConnectionLimitResponse());
        context.channel().close();
    }

    public void stopTimer() {
        timerTask.cancel();
    }


}

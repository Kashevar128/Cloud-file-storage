package org.vinogradov.myserver.serverLogic;

import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.responses.ConnectionLimitResponse;

import static java.lang.Thread.sleep;

public class ConnectionLimit {

    private ChannelHandlerContext context;
    private boolean flagLimit;

    public ConnectionLimit(ChannelHandlerContext context) {
        flagLimit = false;
        this.context = context;
        startTimer();
    }

    private void startTimer() {
        new Thread(()->{
            try {
                sleep(40000);
                cancel();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    private void cancel() {
        if(!flagLimit) {
            context.channel().writeAndFlush(new ConnectionLimitResponse());
            context.channel().close();
        }
        flagLimit = false;
    }

    public void stopTimer(BasicQuery basicQuery) {
        if(basicQuery instanceof AuthClientRequest || basicQuery instanceof RegClientRequest) {
            flagLimit = true;
        }
    }

    public boolean isFlagLimit() {
        return flagLimit;
    }

    public void setFlagLimit(boolean flagLimit) {
        this.flagLimit = flagLimit;
    }


}

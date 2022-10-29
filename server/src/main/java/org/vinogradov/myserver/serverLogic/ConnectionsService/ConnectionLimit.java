package org.vinogradov.myserver.serverLogic.ConnectionsService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.responses.ConnectionLimitResponse;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class ConnectionLimit {

    private Channel channel;
    private TimerTask timerTask;
    private Timer timer;
    private long delay;

    public ConnectionLimit(Channel channel) {
        this.channel = channel;
        this.delay = 180000L;
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                closeConnect();
            }
        };
        timer.schedule(timerTask, delay);
    }

    private void closeConnect() {
        channel.writeAndFlush(new ConnectionLimitResponse());
        channel.close();
    }

    public void stopTimer() {
        timer.cancel();
    }


}

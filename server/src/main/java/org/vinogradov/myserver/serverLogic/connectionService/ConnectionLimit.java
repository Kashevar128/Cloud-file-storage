package org.vinogradov.myserver.serverLogic.connectionService;

import io.netty.channel.Channel;
import org.vinogradov.common.responses.ConnectionLimitResponse;

import java.util.Timer;
import java.util.TimerTask;

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

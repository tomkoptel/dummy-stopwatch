package com.app.stopwatchapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Tom Koptel
 */
public class DummyTimer implements Parcelable {
    private final TimerContainer mContainer;
    private boolean isStarted = false;

    private UpdateListener mUpdateListener;
    private Timer mTimer;

    public DummyTimer() {
        mContainer = new TimerContainer();
    }

    protected DummyTimer(Parcel in) {
        this.mContainer = in.readParcelable(TimerContainer.class.getClassLoader());
        this.isStarted = in.readByte() != 0;
    }

    public void start(UpdateListener updateListener) {
        mUpdateListener = updateListener;
        if (!isStarted) {
            isStarted = true;
            loopTimer();
        }
    }

    public void stop() {
        mTimer.cancel();
    }

    private void loopTimer() {
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(createUpdateTask(), 0, TimeUnit.SECONDS.toMillis(1));
    }

    private TimerTask createUpdateTask() {
        return new TimerTask() {
            @Override
            public void run() {
                mContainer.updateCounter(TimeUnit.SECONDS.toMillis(1));
                dispatchListener();
            }
        };
    }

    private void dispatchListener() {
        if (mUpdateListener != null) {
            mUpdateListener.update(mContainer);
        }
    }

    public interface UpdateListener {
        void update(TimerContainer container);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mContainer, 0);
        dest.writeByte(isStarted ? (byte) 1 : (byte) 0);
    }

    public static final Parcelable.Creator<DummyTimer> CREATOR = new Parcelable.Creator<DummyTimer>() {
        public DummyTimer createFromParcel(Parcel source) {
            return new DummyTimer(source);
        }

        public DummyTimer[] newArray(int size) {
            return new DummyTimer[size];
        }
    };
}

package com.app.stopwatchapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Tom Koptel
 */
public class TimerContainer implements Parcelable {
    private long elapsedTime;
    private long counter;

    public TimerContainer() {
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getCounter() {
        return counter;
    }

    public long updateCounter(long tick) {
        counter += tick;
        return counter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.elapsedTime);
        dest.writeLong(this.counter);
    }

    protected TimerContainer(Parcel in) {
        this.elapsedTime = in.readLong();
        this.counter = in.readLong();
    }

    public static final Parcelable.Creator<TimerContainer> CREATOR = new Parcelable.Creator<TimerContainer>() {
        public TimerContainer createFromParcel(Parcel source) {
            return new TimerContainer(source);
        }

        public TimerContainer[] newArray(int size) {
            return new TimerContainer[size];
        }
    };
}

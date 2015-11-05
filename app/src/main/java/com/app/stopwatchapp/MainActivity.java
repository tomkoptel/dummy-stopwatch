package com.app.stopwatchapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.start)
    View startButton;
    @Bind(R.id.stop)
    View stopButton;
    @Bind(R.id.preview)
    TextView preview;

    private DummyTimer mDummyTimer;

    private final DummyTimer.UpdateListener mUpdateListener = new DummyTimer.UpdateListener() {
        @Override
        public void update(final TimerContainer container) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    preview.setText(String.valueOf(container.getCounter()));
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            mDummyTimer = new DummyTimer();
        } else {
            mDummyTimer = savedInstanceState.getParcelable("timer");
            if (mDummyTimer == null) {
                mDummyTimer = new DummyTimer();
            } else {
                mDummyTimer.start(mUpdateListener);
            }
        }

        exposeCachedValue();
    }

    private void exposeCachedValue() {

    }

    @OnClick(R.id.start)
    void startTimer() {
        mDummyTimer.start(mUpdateListener);
    }

    @OnClick(R.id.stop)
    void stoptTimer() {
        mDummyTimer.stop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("timer", mDummyTimer);
        super.onSaveInstanceState(outState);
    }
}

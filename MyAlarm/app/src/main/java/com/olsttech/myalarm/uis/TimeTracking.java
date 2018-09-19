package com.olsttech.myalarm.uis;

import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class TimeTracking {

    private static final int TIME_THRESHOLD_MS = 200;

    private Subject<NowVisible, NowVisible> mPublishSubject;
    private final Action1<NowVisible> mOnSuccess;
    private Subscription mSubscription;

    public TimeTracking(final Action1<NowVisible> onSuccess, final Action1<Throwable> onError) {
        this.mOnSuccess = onSuccess;
        this.mPublishSubject = PublishSubject.create();
        this.mSubscription = mPublishSubject.distinctUntilChanged()
                .throttleWithTimeout(TIME_THRESHOLD_MS, TimeUnit.MILLISECONDS)
                .subscribe(mOnSuccess, onError);
    }

    public void postViewEvent(final NowVisible visible) {
        mOnSuccess.call(visible);
        mPublishSubject.onNext(visible);
    }

    public void unsubscribe() {
        mSubscription.unsubscribe();
    }

    private void onCallback(NowVisible visible) {
        mOnSuccess.call(visible);
    }

    /*********************************************************************************/
    public static class NowVisible {
        final int firstVisible;
        final int lastVisible;

        public NowVisible(int firstVisible, int lastVisible) {
            this.firstVisible = firstVisible;
            this.lastVisible = lastVisible;
        }

        public int getFirstVisible(){
            return this.firstVisible;
        }

        public int getLastVisible(){
            return this.lastVisible;
        }
        // TODO please implement equals and hashCode, required for the distinction logic
    }
}
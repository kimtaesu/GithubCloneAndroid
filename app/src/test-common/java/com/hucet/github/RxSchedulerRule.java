package com.hucet.github;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;


public class RxSchedulerRule implements TestRule {
    public TestScheduler scheduler = new TestScheduler();

    public void oneSecond() {
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(schedulerCallable -> scheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(schedulerCallable -> scheduler);
                RxJavaPlugins.setComputationSchedulerHandler(schedulerCallable -> scheduler);
                RxJavaPlugins.setSingleSchedulerHandler(schedulerCallable -> scheduler);

                RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}

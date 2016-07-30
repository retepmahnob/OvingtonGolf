package com.peter.ovingtongolf.utilities;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Created by peter on 27/10/15.
 */
@Module
public final class BusProvider {
    private static final Bus BUS = new Bus(ThreadEnforcer.MAIN);

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}

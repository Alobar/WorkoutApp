package alobar.android.dagger;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

import org.apache.commons.lang3.Validate;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for Android system services
 */
@Module
public class SystemModule {

    private final Context applicationContext;

    public SystemModule(Context context) {
        this.applicationContext = Validate.notNull(context).getApplicationContext();
    }

    @Singleton
    @Provides
    Context getApplicationContext() {
        return applicationContext;
    }

    @Singleton
    @Provides
    ContentResolver getContentResolver() {
        return applicationContext.getContentResolver();
    }

    @Singleton
    @Provides
    Resources getResources() {
        return applicationContext.getResources();
    }
}

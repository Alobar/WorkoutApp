package alobar.android.dagger;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger component for Android system services
 */
@Singleton
@Component(modules = SystemModule.class)
public interface SystemComponent {

    Context getApplicationContext();
    ContentResolver getContentResolver();
    Resources getResources();
}

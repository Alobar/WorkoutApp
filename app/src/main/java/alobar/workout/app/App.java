package alobar.workout.app;

import android.app.Activity;
import android.app.Application;

/**
 * App's application class
 */
public class App extends Application {

    public static App from(Activity activity) {
        return (App) activity.getApplication();
    }

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getComponent() {
        return this.component;
    }
}

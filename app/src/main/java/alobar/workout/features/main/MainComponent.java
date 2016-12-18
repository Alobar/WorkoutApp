package alobar.workout.features.main;

import alobar.workout.app.AppComponent;
import alobar.workout.dagger.ActivityScope;
import dagger.Component;

/**
 * Dagger component for {@link MainActivity}
 */
@ActivityScope
@Component(dependencies = {AppComponent.class})
interface MainComponent {
    void inject(MainActivity activity);
}

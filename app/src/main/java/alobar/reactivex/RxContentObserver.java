package alobar.reactivex;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;

import org.apache.commons.lang3.Validate;

import io.reactivex.Observable;

/**
 * Convenience functions for working with {@link ContentObserver}s in a reactive style
 */
public class RxContentObserver {

    /**
     * Create an Observable for content notifications
     *
     * @param resolver             System service to register with for the notifications
     * @param uri                  The URI to watch for changes.
     * @param notifyForDescendants When true, also emit for descendants of the uri.
     * @return Observable that never completes
     */
    public static Observable<Uri> create(ContentResolver resolver, Uri uri, boolean notifyForDescendants) {
        Validate.notNull(resolver);
        Validate.notNull(uri);

        return Observable.create(emitter -> {
            ContentObserver observer = new ContentObserver(null) {
                @Override
                public void onChange(boolean selfChange) {
                    emitter.onNext(uri);
                }
            };
            resolver.registerContentObserver(uri, notifyForDescendants, observer);
            emitter.setCancellable(() -> resolver.unregisterContentObserver(observer));
        });
    }
}

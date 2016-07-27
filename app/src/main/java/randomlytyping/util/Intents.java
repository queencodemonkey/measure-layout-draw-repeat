package randomlytyping.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;

import randomlytyping.mldr.ui.TallyCounterActivity;

/**
 * Helper class for creation of {@link Intent} instances including extras.
 */
public final class Intents {

    public static final String EXTRA_LAYOUT_RES_ID = "layoutResId";
    public static final String EXTRA_TOAST_ON_CLICK = "toastOnClick";

    public static Intent getDemoIntent(Context context, @LayoutRes int layoutResId,
                                       boolean toastOnClick) {
        final Intent intent = new Intent(context, TallyCounterActivity.class);
        intent.putExtra(EXTRA_LAYOUT_RES_ID, layoutResId);
        intent.putExtra(EXTRA_TOAST_ON_CLICK, toastOnClick);
        return intent;
    }

    //
    // Constructors
    //

    /**
     * Private constructor to prevent instantiation.
     */
    private Intents() {
        throw new AssertionError("Cannot instantiate " + getClass().getName());
    }
}

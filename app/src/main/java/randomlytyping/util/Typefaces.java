package randomlytyping.util;

import android.content.Context;
import android.graphics.Typeface;

import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 *
 */
public class Typefaces {

    public static final String BLACKOUT_TWO_AM = "BlackoutTwoAM.ttf";
    public static final String BLACKOUT_MIDNIGHT = "BlackoutMidnight.ttf";
    public static final String HK_GROTESK = "HKGroteskMedium.otf";

    public static Typeface getTypeface(Context context, String typeface) {
        return TypefaceUtils.load(context.getAssets(), "fonts/" + typeface);
    }
}

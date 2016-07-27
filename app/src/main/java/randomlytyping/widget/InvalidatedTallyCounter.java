package randomlytyping.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

import randomlytyping.mldr.R;

/**
 * Version of tally counter component that calls {@link #invalidate()} to refresh the drawing after
 * a state change.
 */
public class InvalidatedTallyCounter extends View implements TallyCounter {

    private static final int MAX_COUNT = 9999;

    // State variables
    private int count;
    private String displayedCount;

    // Drawing variables
    private Paint backgroundPaint;
    private Paint linePaint;
    private TextPaint numberPaint;

    private RectF backgroundRect;

    private float cornerRadius;

    //
    // Constructors/Initialization
    //

    /**
     * Simple constructor to use when creating a view using the {@code new} operator.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public InvalidatedTallyCounter(Context context) {
        this(context, null);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public InvalidatedTallyCounter(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Set up paints for canvas drawing.
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        linePaint.setStrokeWidth(1f);

        numberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setColor(ContextCompat.getColor(context, android.R.color.white));
        numberPaint.setTextSize(Math.round(64f * getResources().getDisplayMetrics().scaledDensity));

        // Allocate objects needed for canvas drawing here.
        backgroundRect = new RectF();

        // Initialize drawing measurements.
        cornerRadius = Math.round(2f * getResources().getDisplayMetrics().density);

        // Do initial count setup.
        setCount(0);
    }

    //
    // TallyCounter interface
    //

    @Override
    public void reset() {
        setCount(0);
    }

    @Override
    public void increment() {
        setCount(count + 1);
    }

    @Override
    public void setCount(int count) {
        count = Math.min(count, MAX_COUNT);
        this.count = count;
        this.displayedCount = String.format(Locale.getDefault(), "%04d", count);
        invalidate();
    }

    @Override
    public int getCount() {
        return count;
    }

    //
    // View overrides
    //

    @Override
    protected void onDraw(Canvas canvas) {

        // Grab canvas dimensions.
        final int canvasWidth = canvas.getWidth();
        final int canvasHeight = canvas.getHeight();

        // Calculate horizontal center.
        final float centerX = canvasWidth * 0.5f;

        // Draw the background.
        backgroundRect.set(0f, 0f, canvasWidth, canvasHeight);
        canvas.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, backgroundPaint);

        // Draw baseline.
        final float baselineY = Math.round(canvasHeight * 0.6f);
        canvas.drawLine(0, baselineY, canvasWidth, baselineY, linePaint);

        // Draw text.
        final float textWidth = numberPaint.measureText(displayedCount);
        final float textX = Math.round(centerX - textWidth * 0.5f);
        canvas.drawText(displayedCount, textX, baselineY, numberPaint);
    }
}

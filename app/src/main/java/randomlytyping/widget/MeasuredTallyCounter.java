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
public class MeasuredTallyCounter extends View implements TallyCounter {

    private static final int MAX_COUNT = 9999;
    private static final String MAX_COUNT_STRING = String.valueOf(MAX_COUNT);

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
    public MeasuredTallyCounter(Context context) {
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
    public MeasuredTallyCounter(Context context, AttributeSet attrs) {
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
    // Counter interface
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

    /**
     * Reconcile a desired size for the view contents with a {@link android.view.View.MeasureSpec}
     * constraint passed by the parent.
     *
     * This is a simplified version of {@link View#resolveSize(int, int)}
     *
     * @param contentSize Size of the view's contents.
     * @param measureSpec A {@link android.view.View.MeasureSpec} passed by the parent.
     * @return A size that best fits {@code contentSize} while respecting the parent's constraints.
     */
    private int reconcileSize(int contentSize, int measureSpec) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return specSize;
            case MeasureSpec.AT_MOST:
                if (contentSize < specSize) {
                    return contentSize;
                } else {
                    return specSize;
                }
            case MeasureSpec.UNSPECIFIED:
            default:
                return contentSize;
        }
    }

    //
    // View overrides
    //

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final Paint.FontMetrics fontMetrics = numberPaint.getFontMetrics();

        // Measure maximum possible width of text.
        final float maxTextWidth = numberPaint.measureText(MAX_COUNT_STRING);
        // Estimate maximum possible height of text.
        final float maxTextHeight = -fontMetrics.top + fontMetrics.bottom;

        // Add padding to maximum width calculation.
        final int desiredWidth = Math.round(maxTextWidth + getPaddingLeft() + getPaddingRight());

        // Add padding to maximum height calculation.
        final int desiredHeight = Math.round(maxTextHeight * 2f + getPaddingTop() +
            getPaddingBottom());

        // Reconcile size that this view wants to be with the size the parent will let it be.
        final int measuredWidth = reconcileSize(desiredWidth, widthMeasureSpec);
        final int measuredHeight = reconcileSize(desiredHeight, heightMeasureSpec);

        // Store the final measured dimensions.
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

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

        // Draw lines that show font top and bottom.
        final Paint.FontMetrics fontMetrics = numberPaint.getFontMetrics();
        final float topY = Math.round(baselineY + fontMetrics.top);
        final float bottomY = Math.round(baselineY + fontMetrics.bottom);
        canvas.drawLine(0, topY, canvasWidth, topY, linePaint);
        canvas.drawLine(0, bottomY, canvasWidth, bottomY, linePaint);

        // Draw text.
        final float textWidth = numberPaint.measureText(displayedCount);
        final float textX = Math.round(centerX - textWidth * 0.5f);
        canvas.drawText(displayedCount, textX, baselineY, numberPaint);
    }
}

/*
 * Copyright (C) 2015 Randomly Typing LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package randomlytyping.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import randomlytyping.mldr.R;

/**
 * Custom {@link ViewGroup} for displaying the example list items from the launch screen but
 * without the extra measure/layout pass from the {@link android.widget.RelativeLayout}.
 */
public class SimpleListItem extends ViewGroup {
    //
    // Fields
    //

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.title)
    TextView titleView;

    @BindView(R.id.subtitle)
    TextView subtitleView;

    //
    // Constructors
    //

    /**
     * Constructor.
     *
     * @param context The current context.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public SimpleListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    //
    // ViewGroup implementation
    //

    /**
     * Validates if a set of layout parameters is valid for a child of this ViewGroup.
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }


    /**
     * @return A set of default layout parameters when given a child with no layout parameters.
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * @return A set of layout parameters created from attributes passed in XML.
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * Called when {@link #checkLayoutParams(LayoutParams)} fails.
     *
     * @return A set of valid layout parameters for this ViewGroup that copies appropriate/valid
     * attributes from the supplied, not-so-good parameters.
     */
    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return generateDefaultLayoutParams();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // Measure icon.
        measureChildWithMargins(icon, widthMeasureSpec, 0, heightMeasureSpec, 0);

        // Figure out how much width the icon used.
        MarginLayoutParams lp = (MarginLayoutParams) icon.getLayoutParams();
        int widthUsed = icon.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

        int heightUsed = 0;

        // Measure title
        measureChildWithMargins(
            titleView,
            // Pass width constraints and width already used.
            widthMeasureSpec, widthUsed,
            // Pass height constraints and height already used.
            heightMeasureSpec, heightUsed
        );

        // Measure the Subtitle.
        measureChildWithMargins(
            subtitleView,
            // Pass width constraints and width already used.
            widthMeasureSpec, widthUsed,
            // Pass height constraints and height already used.
            heightMeasureSpec, titleView.getMeasuredHeight());

        // Calculate this view's measured width and height.

        // Figure out how much total space the icon used.
        int iconWidth = icon.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
        int iconHeight = icon.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
        lp = (MarginLayoutParams) titleView.getLayoutParams();

        // Figure out how much total space the title used.
        int titleWidth = titleView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
        int titleHeight = titleView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
        lp = (MarginLayoutParams) subtitleView.getLayoutParams();

        // Figure out how much total space the subtitle used.
        int subtitleWidth = subtitleView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
        int subtitleHeight = subtitleView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

        // The width taken by the children + padding.
        int width = getPaddingTop() + getPaddingBottom() +
            iconWidth + Math.max(titleWidth, subtitleWidth);
        // The height taken by the children + padding.
        int height = getPaddingTop() + getPaddingBottom() +
            Math.max(iconHeight, titleHeight + subtitleHeight);

        // Reconcile the measured dimensions with the this view's constraints and
        // set the final measured width and height.
        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        MarginLayoutParams layoutParams = (MarginLayoutParams) icon.getLayoutParams();

        // Figure out the x-coordinate and y-coordinate of the icon.
        int x = getPaddingLeft() + layoutParams.leftMargin;
        int y = getPaddingTop() + layoutParams.topMargin;

        // Layout the icon.
        icon.layout(x, y, x + icon.getMeasuredWidth(), y + icon.getMeasuredHeight());

        // Calculate the x-coordinate of the title: icon's right coordinate +
        // the icon's right margin.
        x += icon.getMeasuredWidth() + layoutParams.rightMargin;

        // Add in the title's left margin.
        layoutParams = (MarginLayoutParams) titleView.getLayoutParams();
        x += layoutParams.leftMargin;

        // Calculate the y-coordinate of the title: this ViewGroup's top padding +
        // the title's top margin
        y = getPaddingTop() + layoutParams.topMargin;

        // Layout the title.
        titleView.layout(x, y, x + titleView.getMeasuredWidth(), y + titleView.getMeasuredHeight());

        // The subtitle has the same x-coordinate as the title. So no more calculating there.

        // Calculate the y-coordinate of the subtitle: the title's bottom coordinate +
        // the title's bottom margin.
        y += titleView.getMeasuredHeight() + layoutParams.bottomMargin;
        layoutParams = (MarginLayoutParams) subtitleView.getLayoutParams();

        // Add in the subtitle's top margin.
        y += layoutParams.topMargin;

        // Layout the subtitle.
        subtitleView.layout(x, y,
            x + subtitleView.getMeasuredWidth(), y + subtitleView.getMeasuredHeight());
    }
}







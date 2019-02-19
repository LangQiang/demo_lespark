package com.example.lq.myapplication.retractable;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;


import com.example.lq.myapplication.R;

import java.lang.reflect.Field;


/**
 * ＊创建时间：18/4/3
 * ＊创建者： Sky
 * ＊当前类的作用：
 */

public class ExpandableTextViewNew extends android.support.v7.widget.AppCompatTextView {
    public static final int STATE_SHRINK = 0;
    public static final int STATE_EXPAND = 1;

    private static final String CLASS_NAME_VIEW = "android.view.View";
    private static final String CLASS_NAME_LISTENER_INFO = "android.view.View$ListenerInfo";
    private static final String ELLIPSIS_HINT = "...";
    private static final String GAP_TO_EXPAND_HINT = " ";
    private static final String GAP_TO_SHRINK_HINT = " ";
    private static final int MAX_LINES_ON_SHRINK = 2;
    private static final int TO_EXPAND_HINT_COLOR = 0xFF3498DB;
    private static final int TO_SHRINK_HINT_COLOR = 0xFFE74C3C;
    private static final int TO_EXPAND_HINT_COLOR_BG_PRESSED = 0x55999999;
    private static final int TO_SHRINK_HINT_COLOR_BG_PRESSED = 0x55999999;
    private static final boolean TOGGLE_ENABLE = true;
    private static final boolean SHOW_TO_EXPAND_HINT = true;
    private static final boolean SHOW_TO_SHRINK_HINT = true;

    private String mEllipsisHint;
    private String mToExpandHint;
    private String mToShrinkHint;
    private String mGapToExpandHint = GAP_TO_EXPAND_HINT;
    private String mGapToShrinkHint = GAP_TO_SHRINK_HINT;
    private boolean mToggleEnable = TOGGLE_ENABLE;
    private boolean mShowToExpandHint = SHOW_TO_EXPAND_HINT;
    private boolean mShowToShrinkHint = SHOW_TO_SHRINK_HINT;
    private int mMaxLinesOnShrink = MAX_LINES_ON_SHRINK;
    private int mToExpandHintColor = TO_EXPAND_HINT_COLOR;
    private int mToShrinkHintColor = TO_SHRINK_HINT_COLOR;
    private int mToExpandHintColorBgPressed = TO_EXPAND_HINT_COLOR_BG_PRESSED;
    private int mToShrinkHintColorBgPressed = TO_SHRINK_HINT_COLOR_BG_PRESSED;
    private int mCurrState = STATE_SHRINK;

    //  used to add to the tail of modified text, the "shrink" and "expand" text
    private TouchableSpan mTouchableSpan;
    private BufferType mBufferType = BufferType.NORMAL;
    private TextPaint mTextPaint;
    private Layout mLayout;
    private int mTextLineCount = -1;
    private int mLayoutWidth = 0;
    private int mFutureTextViewWidth = 0;

    //  the original text of this view
    private CharSequence mOrigText;

    private OnExpandListener mOnExpandListener;

    public boolean linkHit;//内部链接是否被点击

    private boolean isCleanTouchSpan = false;

    public ExpandableTextViewNew(Context context) {
        super(context);
        init();
    }

    public ExpandableTextViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context,attrs);
        init();
    }

    public ExpandableTextViewNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context,attrs);
        init();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        if (a == null) {
            return;
        }
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.ExpandableTextView_etv_MaxLinesOnShrink) {
                mMaxLinesOnShrink = a.getInteger(attr, MAX_LINES_ON_SHRINK);
            }else if (attr == R.styleable.ExpandableTextView_etv_EllipsisHint){
                mEllipsisHint = a.getString(attr);
            }else if (attr == R.styleable.ExpandableTextView_etv_ToExpandHint) {
                mToExpandHint = a.getString(attr);
            }else if (attr == R.styleable.ExpandableTextView_etv_ToShrinkHint) {
                mToShrinkHint = a.getString(attr);
            }else if (attr == R.styleable.ExpandableTextView_etv_EnableToggle) {
                mToggleEnable = a.getBoolean(attr, TOGGLE_ENABLE);
            }else if (attr == R.styleable.ExpandableTextView_etv_ToExpandHintShow){
                mShowToExpandHint = a.getBoolean(attr, SHOW_TO_EXPAND_HINT);
            }else if (attr == R.styleable.ExpandableTextView_etv_ToShrinkHintShow){
                mShowToShrinkHint = a.getBoolean(attr, SHOW_TO_SHRINK_HINT);
            }else if (attr == R.styleable.ExpandableTextView_etv_ToExpandHintColor){
                mToExpandHintColor = a.getInteger(attr, TO_EXPAND_HINT_COLOR);
            }else if (attr == R.styleable.ExpandableTextView_etv_ToShrinkHintColor){
                mToShrinkHintColor = a.getInteger(attr, TO_SHRINK_HINT_COLOR);
            }else if (attr == R.styleable.ExpandableTextView_etv_ToExpandHintColorBgPressed){
                mToExpandHintColorBgPressed = a.getInteger(attr, TO_EXPAND_HINT_COLOR_BG_PRESSED);
            }else if (attr == R.styleable.ExpandableTextView_etv_ToShrinkHintColorBgPressed){
                mToShrinkHintColorBgPressed = a.getInteger(attr, TO_SHRINK_HINT_COLOR_BG_PRESSED);
            }else if (attr == R.styleable.ExpandableTextView_etv_InitState){
                mCurrState = a.getInteger(attr, STATE_SHRINK);
            }else if (attr == R.styleable.ExpandableTextView_etv_GapToExpandHint){
                mGapToExpandHint = a.getString(attr);
            }else if (attr == R.styleable.ExpandableTextView_etv_GapToShrinkHint){
                mGapToShrinkHint = a.getString(attr);
            }else if (attr == R.styleable.ExpandableTextView_etv_clean_touch_span){
                isCleanTouchSpan = a.getBoolean(attr,false);
            }
        }
        a.recycle();
    }

    private void init() {
        mTouchableSpan = new TouchableSpan();
        if (!isCleanTouchSpan) {
            setMovementMethod(new LinkTouchMovementMethod());
        }
        if(TextUtils.isEmpty(mEllipsisHint)) {
            mEllipsisHint = ELLIPSIS_HINT;
        }
        if (!isCleanTouchSpan) {
            if (TextUtils.isEmpty(mToExpandHint)) {
                mToExpandHint = "全文";
            }
            if (TextUtils.isEmpty(mToShrinkHint)) {
                mToShrinkHint = "收起";
            }
        }else{
            mToExpandHint = "";
            mToShrinkHint = "";
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
                setTextInternal(getNewTextByConfig(), mBufferType);
            }
        });
    }

    /**
     * used in ListView or RecyclerView to update ExpandableTextView
     * @param text
     *          original text
     * @param futureTextViewWidth
     *          the width of ExpandableTextView in px unit,
     *          used to get max line number of original text by given the width
     * @param expandState
     *          expand or shrink
     */
    public void updateForRecyclerView(CharSequence text, int futureTextViewWidth, int expandState){
        mFutureTextViewWidth = futureTextViewWidth;
        mCurrState = expandState;
        setText(text);
    }

    public void updateForRecyclerView(CharSequence text, BufferType type, int futureTextViewWidth){
        mFutureTextViewWidth = futureTextViewWidth;
        setText(text, type);
    }

    public void updateForRecyclerView(CharSequence text, int futureTextViewWidth){
        mFutureTextViewWidth = futureTextViewWidth;
        setText(text);
    }

    /**
     * get the current state of ExpandableTextView
     * @return
     *      STATE_SHRINK if in shrink state
     *      STATE_EXPAND if in expand state
     */
    public int getExpandState(){
        return mCurrState;
    }

    /**
     * refresh and get a will-be-displayed text by current configuration
     * @return
     *      get a will-be-displayed text
     */
    private CharSequence getNewTextByConfig(){
        //原始文字
        if(TextUtils.isEmpty(mOrigText)){
            return mOrigText;
        }

        mLayout = getLayout();
        if(mLayout != null){
            mLayoutWidth = mLayout.getWidth();
        }

        if(mLayoutWidth <= 0){
            if(getWidth() == 0) {
                if (mFutureTextViewWidth == 0) {
                    return mOrigText;
                } else {
                    mLayoutWidth = mFutureTextViewWidth - getPaddingLeft() - getPaddingRight();
                }
            }else{
                mLayoutWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            }
        }

        mTextPaint = getPaint();

        mTextLineCount = -1;
        switch (mCurrState){
            case STATE_SHRINK: {
                //创建一个随着文本变化而变化的布局
                mLayout = new DynamicLayout(mOrigText, mTextPaint, mLayoutWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                //获取文本的行数
                mTextLineCount = mLayout.getLineCount();
                //如果文本行数小于限制行数，直接返回原始文本
                if (mTextLineCount <= mMaxLinesOnShrink) {
                    return mOrigText;
                }
                //返回指定行数末尾文字之后的文字偏移量
                int indexEnd = getValidLayout().getLineEnd(mMaxLinesOnShrink - 1);
                //返回指定行数文本起始的偏移量，如果指定行数等于文本行数，返回文本长度
                int indexStart = getValidLayout().getLineStart(mMaxLinesOnShrink - 1);
                //裁剪后文字的长度
                int indexEndTrimmed = indexEnd
                        - getLengthOfString(mEllipsisHint)
                        - (mShowToExpandHint ? getLengthOfString(mToExpandHint) + getLengthOfString(mGapToExpandHint) : 0);

                if (indexEndTrimmed <= indexStart) {
                    indexEndTrimmed = indexEnd;
                }

                //指定行数文字的宽度
                int remainWidth = getValidLayout().getWidth() / mTextLineCount * mMaxLinesOnShrink-
                        (int) (mTextPaint.measureText(mOrigText.subSequence(indexStart, indexEndTrimmed).toString()) + 0.5);
                //拼接文字的宽度   .. 全文 / .. 收起
                float widthTailReplaced = mTextPaint.measureText(getContentOfString(mEllipsisHint)
                        + (mShowToExpandHint ? (getContentOfString(mToExpandHint) + getContentOfString(mGapToExpandHint)) : ""));

                int indexEndTrimmedRevised = indexEndTrimmed;
                //判断剩余文字宽度是否大于拼接文字的宽度
                if (remainWidth > widthTailReplaced) {
                    int extraOffset = 0;
                    int extraWidth = 0;
                    while (remainWidth > widthTailReplaced + extraWidth) {
                        extraOffset++;
                        if (indexEndTrimmed + extraOffset <= mOrigText.length()) {
                            extraWidth = (int) (mTextPaint.measureText(
                                    mOrigText.subSequence(indexEndTrimmed, indexEndTrimmed + extraOffset).toString()) + 0.5);
                        } else {
                            break;
                        }
                    }
                    indexEndTrimmedRevised += extraOffset - 1;
                } else {
                    int extraOffset = 0;
                    int extraWidth = 0;
                    while (remainWidth + extraWidth < widthTailReplaced) {
                        extraOffset--;
                        if (indexEndTrimmed + extraOffset > indexStart) {
                            extraWidth = (int) (mTextPaint.measureText(mOrigText.subSequence(indexEndTrimmed + extraOffset, indexEndTrimmed).toString()) + 0.5);
                        } else {
                            break;
                        }
                    }
                    indexEndTrimmedRevised += extraOffset;
                }

                //解决文字  开始位置0  结束位置  indexEndTrimmedRevised
                CharSequence fixText = removeEndLineBreak(mOrigText.subSequence(0, indexEndTrimmedRevised));
                SpannableStringBuilder ssbShrink = new SpannableStringBuilder(fixText)
                        .append(mEllipsisHint);
                //是否展示全文 拼接全文及点击事件
                if (mShowToExpandHint) {
                    ssbShrink.append(getContentOfString(mGapToExpandHint) + getContentOfString(mToExpandHint));
                    ssbShrink.setSpan(mTouchableSpan, ssbShrink.length() - getLengthOfString(mToExpandHint), ssbShrink.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                return ssbShrink;
            }
            case STATE_EXPAND: {
                if (!mShowToShrinkHint) {
                    return mOrigText;
                }
                mLayout = new DynamicLayout(mOrigText, mTextPaint, mLayoutWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                mTextLineCount = mLayout.getLineCount();

                if (mTextLineCount <= mMaxLinesOnShrink) {
                    return mOrigText;
                }

                SpannableStringBuilder ssbExpand = new SpannableStringBuilder(mOrigText)
                        .append(mGapToShrinkHint).append(mToShrinkHint);
                ssbExpand.setSpan(mTouchableSpan, ssbExpand.length() - getLengthOfString(mToShrinkHint), ssbExpand.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return ssbExpand;
            }
        }
        return mOrigText;
    }

    @Override
    public boolean performClick() {
        if(linkHit){
            return true;
        }
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        linkHit = false;
        return super.onTouchEvent(event);
    }

    private CharSequence removeEndLineBreak(CharSequence text) {
        while (text.charAt(text.length() - 1) == '\n') {
            text = text.subSequence(0, text.length() - 1);
        }
        return text;
    }

    public void setExpandListener(OnExpandListener listener){
        mOnExpandListener = listener;
    }

    private Layout getValidLayout(){
        return mLayout != null ? mLayout : getLayout();
    }

    private void toggle(){
        switch (mCurrState){
            case STATE_SHRINK:
                mCurrState = STATE_EXPAND;
                if(mOnExpandListener != null){
                    mOnExpandListener.onExpand(this);
                }
                break;
            case STATE_EXPAND:
                mCurrState = STATE_SHRINK;
                if(mOnExpandListener != null){
                    mOnExpandListener.onShrink(this);
                }
                break;
        }
        setTextInternal(getNewTextByConfig(), mBufferType);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        mOrigText = text;
        mBufferType = type;
        setTextInternal(getNewTextByConfig(), type);
    }

    private void setTextInternal(CharSequence text, BufferType type){
        super.setText(text, type);
    }

    private int getLengthOfString(String string){
        if(string == null)
            return 0;
        return string.length();
    }

    private String getContentOfString(String string){
        if(string == null)
            return "";
        return string;
    }

    public interface OnExpandListener{
        void onExpand(ExpandableTextViewNew view);
        void onShrink(ExpandableTextViewNew view);
    }

    public OnClickListener getOnClickListener(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getOnClickListenerV14(view);
        } else {
            return getOnClickListenerV(view);
        }
    }

    private OnClickListener getOnClickListenerV(View view) {
        OnClickListener retrievedListener = null;
        try {
            Field field = Class.forName(CLASS_NAME_VIEW).getDeclaredField("mOnClickListener");
            field.setAccessible(true);
            retrievedListener = (OnClickListener) field.get(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retrievedListener;
    }

    private OnClickListener getOnClickListenerV14(View view) {
        OnClickListener retrievedListener = null;
        try {
            Field listenerField = Class.forName(CLASS_NAME_VIEW).getDeclaredField("mListenerInfo");
            Object listenerInfo = null;

            if (listenerField != null) {
                listenerField.setAccessible(true);
                listenerInfo = listenerField.get(view);
            }

            Field clickListenerField = Class.forName(CLASS_NAME_LISTENER_INFO).getDeclaredField("mOnClickListener");

            if (clickListenerField != null && listenerInfo != null) {
                clickListenerField.setAccessible(true);
                retrievedListener = (OnClickListener) clickListenerField.get(listenerInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retrievedListener;
    }


    /**
     * Copy from:
     *  http://stackoverflow.com/questions
     *  /20856105/change-the-text-color-of-a-single-clickablespan-when-pressed-without-affecting-o
     * By:
     *  Steven Meliopoulos
     */
    private class TouchableSpan extends ClickableSpan {
        private boolean mIsPressed;
        public void setPressed(boolean isSelected) {
            mIsPressed = isSelected;
        }

        @Override
        public void onClick(View widget) {
            toggle();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            switch (mCurrState){
                case STATE_SHRINK:
                    ds.setColor(mToExpandHintColor);
                    ds.bgColor = mIsPressed ? mToExpandHintColorBgPressed : 0;
                    break;
                case STATE_EXPAND:
                    ds.setColor(mToShrinkHintColor);
                    ds.bgColor = mIsPressed ? mToShrinkHintColorBgPressed : 0;
                    break;
            }
            ds.setUnderlineText(false);
        }
    }

    /**
     * Copy from:
     *  http://stackoverflow.com/questions
     *  /20856105/change-the-text-color-of-a-single-clickablespan-when-pressed-without-affecting-o
     * By:
     *  Steven Meliopoulos
     */
    public static class LinkTouchMovementMethod extends LinkMovementMethod {
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer,
                                    MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);

                    } else if (action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                    }

                    if(widget instanceof ExpandableTextViewNew){
                        ((ExpandableTextViewNew)widget).linkHit = true;
                    }

                    return true;
                } else {
                    Selection.removeSelection(buffer);
                    super.onTouchEvent(widget, buffer, event);
                    return false;
                }
            }

            return Touch.onTouchEvent(widget, buffer, event);
        }

    }
}

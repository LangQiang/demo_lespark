package com.example.lq.myapplication.collapsible;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.utils.ToastUtil2;


/**
 * Created by zhuoying on 14/12/1.
 */
public class CollapsibleTextView extends LinearLayout implements
        View.OnClickListener {

    /**
     * default text show max lines
     */
    public static final int DEFAULT_MAX_LINE_COUNT = 3;
    public static final int COLLAPSIBLE_STATE_NONE = 0;
    /**
     * 收起状态，展示全文
     */
    public static final int COLLAPSIBLE_STATE_SHRINKUP = 1;
    /**
     * 全文状态，展示收起
     */
    public static final int COLLAPSIBLE_STATE_SPREAD = 2;

    public TextView desc;
    private TextView descOp;

    private String shrinkup;
    private String spread;
    private int mState = 1;
    private boolean flag;
    private int mPosition= -1;

    private Runnable mInnerRunnable;
    private String mUrl = "";

    private Context context;
    private String content = "";

    private int availableTextWidth = 0;

    private int textLines = 3;

    //判断是否点击过
    private boolean isClicked = false;

    public void setTextLines(int textLines)
    {
        this.textLines = textLines;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setmState(int mState) {
        this.mState = mState;
    }

    public void setmState(int mState, String url, int position) {
        this.mState = mState;
        mUrl = url;
        mPosition = position;
    }


    public void setTextSize(float textSize) {
        this.textSize = textSize;
        desc.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        descOp.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        requestLayout();
    }

    private float textSize;

    public CollapsibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CollapsibleTextView);
        String text = array.getString(R.styleable.CollapsibleTextView_text);
        textSize = array.getDimensionPixelSize(R.styleable.CollapsibleTextView_textSize, 50);
        //收起
        shrinkup = "收起";
        //全文
        spread = "全文";
        View view = inflate(context, R.layout.collapsible_textview, this);
        view.setPadding(0, -1, 0, 0);
        desc = (TextView) view.findViewById(R.id.desc_tv);
        descOp = (TextView) view.findViewById(R.id.desc_op_tv);
        if (!TextUtils.isEmpty(text)) {
            desc.setText(text);
            content = text;
        }
        desc.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        descOp.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        descOp.setOnClickListener(this);
        mInnerRunnable = new InnerRunnable();
        array.recycle();
    }

    public void clearComposingText() {
        if (desc != null) {
            desc.clearComposingText();
        }
    }

    public final void setMovementMethod(MovementMethod movement) {
        if (desc != null) {
            desc.setMovementMethod(movement);
        }
    }

    public CollapsibleTextView(Context context) {
        this(context, null);
    }

    public final void setText(CharSequence charSequence) {
        desc.setText(charSequence);
        mState = COLLAPSIBLE_STATE_SHRINKUP;
        flag = false;
        isClicked = false;
        requestLayout();
    }
    public final void setText(CharSequence charSequence,boolean isComments) {
        if (!isComments) {
            content = charSequence.toString();
            desc.setText(content);
            mState = COLLAPSIBLE_STATE_SHRINKUP;
            flag = false;
            isClicked = false;
            requestLayout();
        } else
        {
            desc.setMaxLines(Integer.MAX_VALUE);
            mState = COLLAPSIBLE_STATE_SPREAD;
            flag = false;
            isClicked = false;
        }
    }

    @Override
    public void onClick(View v) {
        flag = false;
        isClicked = true;
        if (mState == COLLAPSIBLE_STATE_SPREAD) {
            mState = COLLAPSIBLE_STATE_SHRINKUP;
        } else if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
            mState = COLLAPSIBLE_STATE_SPREAD;
        }
        if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
            requestLayout();
            descOp.setVisibility(View.GONE);
            if (mPosition != -1) {
                ToastUtil2.showToast("FeedsTextPackUpEvent");
            }
        } else if (mState == COLLAPSIBLE_STATE_SPREAD) {
            desc.setText(content);
            requestLayout();
            ToastUtil2.showToast("if bean = null 上报");
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        if (!flag) {
            flag = true;
            if (desc.getLineCount() <= textLines && !isClicked) {
                mState = COLLAPSIBLE_STATE_NONE;
                desc.setVisibility(VISIBLE);
            } else {
                if (mInnerRunnable == null)
                    mInnerRunnable = new InnerRunnable();
                post(mInnerRunnable);
            }
        }
    }

    class InnerRunnable implements Runnable {
        @Override
        public void run() {
            if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
                //必须取消该行代码，如果不取消不显示拼接的文字
//                desc.setMaxLines(DEFAULT_MAX_LINE_COUNT);
                subStringContent(spread);
            } else if (mState == COLLAPSIBLE_STATE_SPREAD) {
                subStringContent(shrinkup);
            }
        }
    }

    private void subStringContent(String contentHint)
    {
        initTvCommentListener(content,contentHint);
    }

    @Override
    public boolean hasFocusable() {
        return false;
    }

    /**
     * 将原有的content进行截取
     * @param content
     * @param contentHint
     */
    private void initTvCommentListener(String content,String contentHint)
    {
        if (TextUtils.isEmpty(content))
            return;
        TextPaint paint  = desc.getPaint();

        int paddingLeft = desc.getPaddingLeft();
        int paddingRight= desc.getPaddingRight();

        int bufferWidth =(int) paint.getTextSize() * 7;//缓冲区长度，空出两个字符的长度来给最后的省略号及图片
        if (spread.equals(contentHint)) {//全文
            int textNum = (int) (desc.getWidth() / textSize);
            if (content.contains("\n")) {
                int index = content.indexOf("\n");
                int firstSpaceWidth = 0, secondSpaceWidth = 0, thirdSpaceWidth = 0;
                if (index < textNum && index >= 0)
                {
                    firstSpaceWidth = (int) (desc.getWidth() - textSize * index);
                }
                int secendIndex = content.indexOf("\n",index + 1);
                if (secendIndex - index < textNum && secendIndex > 0)
                {
                    if (secendIndex - index == 1)
                    {
                        secondSpaceWidth = desc.getWidth();
                    }else {
                        secondSpaceWidth = (int) (desc.getWidth() - textSize * (secendIndex - index - 1));
                    }
                }
                int thirdIndex = content.indexOf("\n",secendIndex + 1);
                if (thirdIndex - secendIndex < textNum && thirdIndex > 0)
                {
                    if (thirdIndex - secendIndex == 1)
                    {
                        thirdSpaceWidth = desc.getWidth();
                    }else
                    {
                        thirdSpaceWidth = (int) (desc.getWidth() - textSize * (thirdIndex - secendIndex));
                    }
                }
                // 计算出2行文字所能显示的长度
                if (secondSpaceWidth == desc.getWidth())
                {//第二行没有输入文字
                    if (thirdSpaceWidth == desc.getWidth())
                    {
                        availableTextWidth = (desc.getWidth() - paddingLeft - paddingRight) - firstSpaceWidth - 0 + bufferWidth;
                    }else
                    {
                        availableTextWidth = (desc.getWidth() - paddingLeft - paddingRight) * textLines - firstSpaceWidth - secondSpaceWidth - thirdSpaceWidth + bufferWidth;
                    }
                }else
                {
                    if (thirdSpaceWidth == desc.getWidth())
                    {
                        availableTextWidth = (desc.getWidth() - paddingLeft - paddingRight) * textLines - firstSpaceWidth - secondSpaceWidth - thirdSpaceWidth +  bufferWidth;
                    }else
                    {
                        availableTextWidth = (desc.getWidth() - paddingLeft - paddingRight) * textLines - firstSpaceWidth - secondSpaceWidth - thirdSpaceWidth - bufferWidth;
                    }
                }
            }else
            {
                // 计算出3行文字所能显示的长度
                if ((desc.getWidth() - paddingLeft - paddingRight) * 3 > bufferWidth) {
                    availableTextWidth = (desc.getWidth() - paddingLeft - paddingRight) * textLines - bufferWidth;
                }
            }
            // 根据长度截取出剪裁后的文字
            String ellipsizeStr = (String) TextUtils.ellipsize(content, (TextPaint) paint, availableTextWidth, TextUtils.TruncateAt.END);
            char[] chars = ellipsizeStr.toCharArray();
            char a = '…';
            if (ellipsizeStr.length() > 1)
            {
                if (a == chars[chars.length - 1]) {
                    ellipsizeStr = ellipsizeStr.substring(0,ellipsizeStr.length() - 1);
                    ellipsizeStr = ellipsizeStr + "...";
                }
            }
            int start = ellipsizeStr.length();
            int end = start + ("  全文").length();
            SpannableStringBuilder builder = new SpannableStringBuilder(ellipsizeStr + "  全文");
            builder.setSpan(new Clickable(this),start,end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(0xff333333),start,end,SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
            desc.setText(builder);
            desc.setMovementMethod(new LinkMovementMethod());
        }else
        {//收起
            SpannableStringBuilder builder = new SpannableStringBuilder(content + " " + contentHint);
            builder.setSpan(new Clickable(this),content.length() + " ".length(),content.length() + " ".length() + contentHint.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(0xff333333),content.length() + " ".length(),content.length() + " ".length() + contentHint.length(),SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
            desc.setText(builder);
            desc.setMovementMethod(new LinkMovementMethod());
        }
    }

    private int getWidth(TextView mTv, String str) {
        TextPaint paint = mTv.getPaint();
        int width = (int) paint.measureText(str);
        return width ;
    }

    private class Clickable extends NoLineClickSpan implements OnClickListener {
        private OnClickListener listener;

        public Clickable(OnClickListener listener)
        {
            this.listener = listener;
        }
        @Override
        public void onClick(View view) {
            listener.onClick(view);
        }
    }

    private int subStringText(String content,int bufferWidth,int textNum,int textLines,int paddingLeft,int paddingRight)
    {
        int i = 1;
        int index = -1,newIndex = 0;
        int spaceWidth = 0,avalbleSpaceWidht = desc.getWidth() * textLines;
        while (i <= textLines)
        {
            if (content.contains("\n") && (desc.getWidth() - paddingLeft - paddingRight) * textLines > bufferWidth){
                newIndex = index;
                if (index < textNum * textLines) {
                    index = content.indexOf("\n", index + 1);
                    avalbleSpaceWidht = (int) (textSize * (index - newIndex));
                }
            }
            i++;
        }
        if (avalbleSpaceWidht > bufferWidth)
            return avalbleSpaceWidht - bufferWidth;
        else
            return avalbleSpaceWidht + bufferWidth;
    }
}
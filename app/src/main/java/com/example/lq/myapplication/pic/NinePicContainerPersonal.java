package com.example.lq.myapplication.pic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.lq.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lq on 2017/7/6.
 */

public class NinePicContainerPersonal extends ViewGroup implements View.OnClickListener {
    List<PersonalImageBean> pics;
    List<View> ivs;
    int width;
    int height;
    public int single_width;
    public int single_height;
    private int intervalPx;
    private OnItemClickListener onItemClickListener;

    public NinePicContainerPersonal(Context context) {
        this(context, null);
    }

    public NinePicContainerPersonal(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePicContainerPersonal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pics = new ArrayList<>();
        ivs = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setIntervalPx(int intervalPx) {
        if (this.intervalPx == intervalPx) {
            return;
        }
        this.intervalPx = intervalPx;
        requestLayout();
    }


    public void addAllPicsUrl(List<PersonalImageBean> urls) {
        pics = urls;
        if (ivs.size() < pics.size()) {
            int count = pics.size() - ivs.size();
            for (int j = 0; j < count; j++) {
                View view = View.inflate(getContext(), R.layout.item_personal_photo,null);
                this.addView(view);
                ivs.add(view);

            }
        }
        for (int i = 0; i < urls.size(); i++) {
            final String url = urls.get(i).imgUrl;
            final View view = ivs.get(i);
            view.setVisibility(VISIBLE);
            view.setOnClickListener(this);
            boolean b = true;
            if (!b) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView iv = view.findViewById(R.id.personal_photo_iv);
                        iv.setImageResource(R.drawable.timg);
                    }
                }, 1000);
            } else {
                post(new Runnable() {
                    @Override
                    public void run() {
                        ImageView iv = view.findViewById(R.id.personal_photo_iv);
                        iv.setImageResource(R.drawable.timg);

                    }
                });

            }
        }

        for (int i = pics.size(); i < ivs.size(); i++) {
            ivs.get(i).setVisibility(GONE);
            ivs.get(i).setOnClickListener(null);
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("aaa","measure");
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        if (pics == null || pics.size() == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else if (pics.size() == 1) {
            if (single_height == 0) {
                height = width;
            } else if (single_width == 0) {
                width = height;
            } else {
                if (single_height > single_width) {
                    height = width;
                } else {
                    height = (int)(width * 1.0 / single_width * single_height);
                }
            }

        } else if (pics.size() == 2) {
            if (width != intervalPx) {
                height = (width - intervalPx) / 6 * 4;
            }
        } else if (pics.size() == 3) {
            if (2 * width != intervalPx) {
                height = (2 * width - intervalPx) / 3;
            }
        } else if (pics.size() == 4) {
            if (3 * width != intervalPx) {
                height = (12 * width - 4 * intervalPx) / 13;
            }
        }  else if (pics.size() == 5) {
            if (5 * width - intervalPx != 0) {
                height = (5 * width - intervalPx) / 6;
            }
        } else if (pics.size() == 6) {
            height = width;
        } else if (pics.size() == 7) {
//            if (49 * width != -intervalPx) {
//                height = (49 * width + intervalPx) / 39;
//            }
            height = width;
        } else if (pics.size() == 8) {
//            if (61 * width != 3 * intervalPx) {
//                height = (61 * width - 3 * intervalPx) / 52;
//            }
//            if (5 * width - intervalPx != 0) {
            if ((width - intervalPx) / 2 + (width - 2 * intervalPx) / 3 * 2 + 2 * intervalPx != 0) {
                height  = (width - intervalPx) / 2 + (width - 2 * intervalPx) / 3 * 2 + 2 * intervalPx;
            }
//                height = width / 2 + width / 3 * 2 + intervalPx * 2;
//                height = (5 * width - intervalPx) / 6;
//            }
        } else if (pics.size() == 9) {
            if (width != 0) {
                height = width;
            }
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        if (pics.size() == 1) {
            getChildAt(0).measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        } else if (pics.size() == 2) {
            getChildAt(0).measure(MeasureSpec.makeMeasureSpec(height / 4 * 3, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            getChildAt(1).measure(MeasureSpec.makeMeasureSpec(height / 4 * 3, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        } else if (pics.size() == 4) {
            int top_height = (12 * width - 4 * intervalPx) / 13;
            getChildAt(0).measure(MeasureSpec.makeMeasureSpec(top_height * 3 / 4, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(top_height, MeasureSpec.EXACTLY));
            for (int i = 1; i < 4; i++) {
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec((top_height - 2 * intervalPx) / 3, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec((top_height - 2 * intervalPx) / 3, MeasureSpec.EXACTLY));
            }
        } else if (pics.size() == 5) {
            int h1 = (width - intervalPx) / 2;
            getChildAt(0).measure(MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY));
            getChildAt(1).measure(MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY));
            int width_small = (width - 2 * intervalPx) / 3;
            for (int i = 2; i < 5; i++) {
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(width_small, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(width_small, MeasureSpec.EXACTLY));
            }
        }else if (pics.size() == 3 || pics.size() == 6) {
            int h1 = (2 * width - intervalPx) / 3;
            getChildAt(0).measure(MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY));
            getChildAt(1).measure(MeasureSpec.makeMeasureSpec((h1 - intervalPx) / 2, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec((h1 - intervalPx) / 2, MeasureSpec.EXACTLY));
            getChildAt(2).measure(MeasureSpec.makeMeasureSpec((h1 - intervalPx) / 2, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec((h1 - intervalPx) / 2, MeasureSpec.EXACTLY));

            // size = 5 || size = 6
            int count = pics.size() - 3;
            int width_small = 0;
            if (count > 0) {
                width_small = (width - (count - 1) * intervalPx) / count;
            }
            for (int i = 3; i < pics.size(); i++) {
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(width_small, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(width_small, MeasureSpec.EXACTLY));
            }

        } else if (pics.size() == 7) {
            int item_wh = (width - 2 * intervalPx) / 3;
            int top_width = (width - intervalPx) / 2;

//            int top_height = (12 * width - 4 * intervalPx) / 13;

            getChildAt(0).measure(MeasureSpec.makeMeasureSpec(top_width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(item_wh, MeasureSpec.EXACTLY));
            getChildAt(1).measure(MeasureSpec.makeMeasureSpec(top_width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(item_wh, MeasureSpec.EXACTLY));

            for (int i = 2; i < 5; i++) {
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(item_wh, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(item_wh, MeasureSpec.EXACTLY));
            }
            getChildAt(5).measure(MeasureSpec.makeMeasureSpec(top_width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(item_wh, MeasureSpec.EXACTLY));
            getChildAt(6).measure(MeasureSpec.makeMeasureSpec(top_width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(item_wh, MeasureSpec.EXACTLY));
        } else if (pics.size() == 8) {
            int h1 = (width - intervalPx) / 2;
            getChildAt(0).measure(MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY));
            getChildAt(1).measure(MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(h1, MeasureSpec.EXACTLY));

            int width_small = (width - 2 * intervalPx) / 3;
            for (int i = 2; i < 5; i++) {
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(width_small, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(width_small, MeasureSpec.EXACTLY));
            }

            for (int i = 5; i < 8; i++) {
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(width_small, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(width_small, MeasureSpec.EXACTLY));
            }
        }
//        else if (pics.size() == 9) {
//            int top_height = (4 * width - intervalPx) / 4;
//            ImageView iv0 = ivs.get(0);
//            ViewGroup.LayoutParams layoutParams = iv0.getLayoutParams();
//            layoutParams.width = top_height * 3 / 4;
//            layoutParams.height = top_height;
//            //LayoutParams params = new ViewGroup.LayoutParams(top_height * 3 / 4, top_height);
//            iv0.setLayoutParams(layoutParams);
//            iv0.layout(0, 0, top_height * 3 / 4, top_height);
//            for (int i = 1; i < 5; i++) {
//                ImageView iv = ivs.get(i);
//                ViewGroup.LayoutParams layoutParams1 = iv.getLayoutParams();
//                layoutParams1.width = (top_height - 3 * intervalPx) / 4;
//                layoutParams1.height = (top_height - 3 * intervalPx) / 4;
//                //LayoutParams params1 = new ViewGroup.LayoutParams((top_height - 3 * intervalPx) / 4, (top_height - 3 * intervalPx) / 4);
//                iv.setLayoutParams(layoutParams1);
//                iv.layout(top_height * 3 / 4 + intervalPx, (i - 1) * (layoutParams1.height + intervalPx), width, (i - 1) * (layoutParams1.height + intervalPx) + layoutParams1.height);
//            }
//            int count = pics.size() - 5;
//            int width_small = 0;
//            if (count > 0) {
//                width_small = (width - (count - 1) * intervalPx) / count;
//            }
//            for (int i = 5; i < pics.size(); i++) {
//                ImageView iv3 = ivs.get(i);
//                ViewGroup.LayoutParams layoutParams3 = iv3.getLayoutParams();
//                layoutParams3.width = width_small;
//                layoutParams3.height = width_small;
//                //LayoutParams params3 = new ViewGroup.LayoutParams(width_small, width_small);
//                iv3.setLayoutParams(layoutParams3);
//                iv3.layout((i - 5) * (layoutParams3.width + intervalPx), top_height + intervalPx, (i - 5) * (width_small + intervalPx) + width_small, height);
//            }
//        }
        else if (pics.size() == 9) {
            int ivHeight = (width - 2 * intervalPx) / 3;
            for (int i = 0; i < 9; i++) {
                getChildAt(i).measure(MeasureSpec.makeMeasureSpec(ivHeight, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(ivHeight, MeasureSpec.EXACTLY));
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("aaa","onLayout");

        if (pics.size() == 1) {
            LayoutParams layoutParams3 = ivs.get(0).getLayoutParams();
            layoutParams3.width = width;
            layoutParams3.height = height;
            //LayoutParams params = new ViewGroup.LayoutParams(width, height);
            ivs.get(0).setLayoutParams(layoutParams3);
            ((ViewGroup)ivs.get(0)).layout(0, 0, width, height);
        } else if (pics.size() == 2) {
            View iv0 = ivs.get(0);
            LayoutParams params = new LayoutParams(height / 4 * 3, height);
            iv0.setLayoutParams(params);
            iv0.layout(0, 0, params.width, height);
            View iv1 = ivs.get(1);
            LayoutParams params1 = new LayoutParams(height / 4 * 3, height);
            iv1.setLayoutParams(params1);
            iv1.layout(params.width + intervalPx, 0, width, height);
        } else if (pics.size() == 4) {
            int top_height = (12 * width - 4 * intervalPx) / 13;
            View iv0 = ivs.get(0);
            LayoutParams params = new LayoutParams(top_height * 3 / 4, top_height);
            iv0.setLayoutParams(params);
            iv0.layout(0, 0, top_height * 3 / 4, top_height);
            for (int i = 1; i < 4; i++) {
                View iv = ivs.get(i);
                LayoutParams params1 = new LayoutParams((top_height - 2 * intervalPx) / 3, (top_height - 2 * intervalPx) / 3);
                iv.setLayoutParams(params1);
                iv.layout(top_height * 3 / 4 + intervalPx, (i - 1) * (params1.height + intervalPx), width, (i - 1) * (params1.height + intervalPx) + params1.height);
            }

            //size = 7 || size = 8
            int count = pics.size() - 4;
            int width_small = 0;
            if (count > 0) {
                width_small = (width - (count - 1) * intervalPx) / count;
            }
            for (int i = 4; i < pics.size(); i++) {
                View iv3 = ivs.get(i);
                LayoutParams params3 = new LayoutParams(width_small, width_small);
                iv3.setLayoutParams(params3);
                iv3.layout((i - 4) * (params3.width + intervalPx), top_height + intervalPx, (i - 4) * (width_small + intervalPx) + width_small, height);
            }
        } else if (pics.size() == 5) {
            int h1 = (width - intervalPx) / 2;
            View iv0 = ivs.get(0);
            LayoutParams params = new LayoutParams(h1, h1);
            iv0.setLayoutParams(params);
            iv0.layout(0, 0, h1, h1);

            View iv1 = ivs.get(1);
            LayoutParams params2 = new LayoutParams(h1, h1);
            iv1.setLayoutParams(params2);
            iv1.layout(h1 + intervalPx, 0, width, h1);

            int width_small = (width - 2 * intervalPx) / 3;
            for (int i = 2; i < 5; i++) {
                View iv3 = ivs.get(i);
                LayoutParams params3 = new LayoutParams(width_small, width_small);
                iv3.setLayoutParams(params3);
                iv3.layout((i - 2) * (params3.width + intervalPx), h1 + intervalPx, (i - 2) * (width_small + intervalPx) + width_small, height);
            }
        }else if (pics.size() == 3 || pics.size() == 6) {
            View iv0 = ivs.get(0);
            int h1 = (2 * width - intervalPx) / 3;
            LayoutParams params = new LayoutParams(h1, h1);
            iv0.setLayoutParams(params);
            iv0.layout(0, 0, h1, h1);
            View iv1 = ivs.get(1);
            LayoutParams params1 = new LayoutParams((h1 - intervalPx) / 2, (h1 - intervalPx) / 2);
            iv1.setLayoutParams(params1);
            iv1.layout(h1 + intervalPx, 0, width, params1.height);
            View iv2 = ivs.get(2);
            LayoutParams params2 = new LayoutParams((h1 - intervalPx) / 2, (h1 - intervalPx) / 2);
            iv2.setLayoutParams(params2);
            iv2.layout(h1 + intervalPx, params2.height + intervalPx, width, h1);

            // size = 5 || size = 6
            int count = pics.size() - 3;
            int width_small = 0;
            if (count > 0) {
                width_small = (width - (count - 1) * intervalPx) / count;
            }
            for (int i = 3; i < pics.size(); i++) {
                View iv3 = ivs.get(i);
                LayoutParams params3 = new LayoutParams(width_small, width_small);
                iv3.setLayoutParams(params3);
                iv3.layout((i - 3) * (params3.width + intervalPx), h1 + intervalPx, (i - 3) * (width_small + intervalPx) + width_small, height);
            }

        } else if (pics.size() == 7) {
            int item_wh = (width - 2 * intervalPx) / 3;
            int top_width = (width - intervalPx) / 2;

//            int top_height = (12 * width - 4 * intervalPx) / 13;
            View iv0 = ivs.get(0);
            LayoutParams params = new LayoutParams(top_width, item_wh);
            iv0.setLayoutParams(params);
            iv0.layout(0, 0, top_width, item_wh);
            View iv1 = ivs.get(1);
            LayoutParams lp = new LayoutParams(top_width, item_wh);
            iv1.setLayoutParams(lp);
            iv1.layout(top_width + intervalPx, 0, width, item_wh);

            for (int i = 2; i < 5; i++) {
                View iv = ivs.get(i);
                LayoutParams params1 = new LayoutParams(item_wh, item_wh);
                iv.setLayoutParams(params1);
                iv.layout((i - 2) * (item_wh + intervalPx), item_wh + intervalPx, (i - 2) * (item_wh + intervalPx) + item_wh, 2 * item_wh + intervalPx);
            }
            View iv5 = ivs.get(5);
            LayoutParams lp1 = new LayoutParams(top_width, item_wh);
            iv5.setLayoutParams(lp1);
            iv5.layout(0, 2 * (item_wh + intervalPx), top_width, height);
            View iv6 = ivs.get(6);
            LayoutParams lp2 = new LayoutParams(top_width, item_wh);
            iv6.setLayoutParams(lp2);
            iv6.layout(top_width + intervalPx, 2 * (item_wh + intervalPx), width, height);
        } else if (pics.size() == 8) {
            int h1 = (width - intervalPx) / 2;
            View iv0 = ivs.get(0);
            LayoutParams params = new LayoutParams(h1, h1);
            iv0.setLayoutParams(params);
            iv0.layout(0, 0, h1, h1);

            View iv1 = ivs.get(1);
            LayoutParams params2 = new LayoutParams(h1, h1);
            iv1.setLayoutParams(params2);
            iv1.layout(h1 + intervalPx, 0, width, h1);

            int width_small = (width - 2 * intervalPx) / 3;
            for (int i = 2; i < 5; i++) {
                View iv3 = ivs.get(i);
                LayoutParams params3 = new LayoutParams(width_small, width_small);
                iv3.setLayoutParams(params3);
                int new_height = h1 + intervalPx;
                int new_height2 = new_height + width_small;
                iv3.layout((i - 2) * (params3.width + intervalPx), new_height, (i - 2) * (width_small + intervalPx) + width_small, new_height2);
            }

            for (int i = 5; i < 8; i++) {
                View iv3 = ivs.get(i);
                LayoutParams params3 = new LayoutParams(width_small, width_small);
                iv3.setLayoutParams(params3);
                int new_height = h1 + 2 * intervalPx + width_small;
                iv3.layout((i - 5) * (params3.width + intervalPx), new_height, (i - 5) * (width_small + intervalPx) + width_small, height);
            }
        }
//        else if (pics.size() == 9) {
//            int top_height = (4 * width - intervalPx) / 4;
//            ImageView iv0 = ivs.get(0);
//            ViewGroup.LayoutParams layoutParams = iv0.getLayoutParams();
//            layoutParams.width = top_height * 3 / 4;
//            layoutParams.height = top_height;
//            //LayoutParams params = new ViewGroup.LayoutParams(top_height * 3 / 4, top_height);
//            iv0.setLayoutParams(layoutParams);
//            iv0.layout(0, 0, top_height * 3 / 4, top_height);
//            for (int i = 1; i < 5; i++) {
//                ImageView iv = ivs.get(i);
//                ViewGroup.LayoutParams layoutParams1 = iv.getLayoutParams();
//                layoutParams1.width = (top_height - 3 * intervalPx) / 4;
//                layoutParams1.height = (top_height - 3 * intervalPx) / 4;
//                //LayoutParams params1 = new ViewGroup.LayoutParams((top_height - 3 * intervalPx) / 4, (top_height - 3 * intervalPx) / 4);
//                iv.setLayoutParams(layoutParams1);
//                iv.layout(top_height * 3 / 4 + intervalPx, (i - 1) * (layoutParams1.height + intervalPx), width, (i - 1) * (layoutParams1.height + intervalPx) + layoutParams1.height);
//            }
//            int count = pics.size() - 5;
//            int width_small = 0;
//            if (count > 0) {
//                width_small = (width - (count - 1) * intervalPx) / count;
//            }
//            for (int i = 5; i < pics.size(); i++) {
//                ImageView iv3 = ivs.get(i);
//                ViewGroup.LayoutParams layoutParams3 = iv3.getLayoutParams();
//                layoutParams3.width = width_small;
//                layoutParams3.height = width_small;
//                //LayoutParams params3 = new ViewGroup.LayoutParams(width_small, width_small);
//                iv3.setLayoutParams(layoutParams3);
//                iv3.layout((i - 5) * (layoutParams3.width + intervalPx), top_height + intervalPx, (i - 5) * (width_small + intervalPx) + width_small, height);
//            }
//        }
        else if (pics.size() == 9) {
            int ivHeight = (width - 2 * intervalPx) / 3;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    View iv = ivs.get(j + i * 3);
                    LayoutParams params1 = new LayoutParams(ivHeight, ivHeight);
                    iv.setLayoutParams(params1);
                    iv.layout((ivHeight + intervalPx) * j, (ivHeight + intervalPx) * i, (ivHeight + intervalPx) * j + ivHeight, (ivHeight + intervalPx) * i + ivHeight);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (ivs == null) {
            return ;
        }
        for (int i = 0; i < ivs.size(); i++) {
            if (ivs.get(i) == v && onItemClickListener != null) {
                onItemClickListener.onItemClick(ivs.get(i),i);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}

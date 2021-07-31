package com.example.e_vaccination;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class PasswordView extends AppCompatEditText {
    private Drawable eye, eyeWithStrike, passwordHint;
    private boolean visible = false;
    private boolean useStrikeThrough = false;
    private boolean drawableClick;

    public PasswordView(Context context) {
        super(context);
        init(null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.PasswordView,
                    0, 0);
            try {
                useStrikeThrough = a.getBoolean(R.styleable.PasswordView_useStrikeThrough, false);
            } finally {
                a.recycle();
            }
        }

        int enabledColor = resolveAttr();

        eye = getToggleDrawable(getContext(), R.drawable.eye, enabledColor);
        eyeWithStrike = getToggleDrawable(getContext(), R.drawable.eye_strike, enabledColor);

        setup();
    }

    private @ColorInt
    int resolveAttr() {
        TypedValue ta = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.textColorPrimary, ta, true);
        return ContextCompat.getColor(getContext(), ta.resourceId);
    }

    private Drawable getToggleDrawable(Context context, @DrawableRes int drawableResId, @ColorInt int tint) {
        Drawable drawable = getVectorDrawableWithIntrinsicBounds(context, drawableResId).mutate();
        DrawableCompat.setTint(drawable, tint);
        return drawable;
    }

    private Drawable getVectorDrawableWithIntrinsicBounds(Context context, @DrawableRes int drawableResId) {
        Drawable drawable = getVectorDrawable(context, drawableResId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }

    private Drawable getVectorDrawable(Context context, @DrawableRes int drawableResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getDrawable(drawableResId);
        } else {
            return VectorDrawableCompat.create(context.getResources(), drawableResId, context.getTheme());
        }
    }

    protected void setup() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        setInputType(InputType.TYPE_CLASS_TEXT | (visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        setSelection(start, end);
        Drawable drawable = useStrikeThrough && !visible ? eyeWithStrike : eye;
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(null, drawables[1], drawable, drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int drawableRightX = getWidth() - getPaddingRight();
        int drawableLeftX = drawableRightX - getCompoundDrawables()[2].getBounds().width();
        boolean eyeClicked = event.getX() >= drawableLeftX && event.getX() <= drawableRightX;

        if (event.getAction() == MotionEvent.ACTION_DOWN && eyeClicked) {
            drawableClick = true;
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (eyeClicked && drawableClick) {
                drawableClick = false;
                visible = !visible;
                setup();
                invalidate();
                return true;
            } else {
                drawableClick = false;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setInputType(int type) {
        super.setInputType(type);
    }

    @Override
    public void setError(CharSequence error) {
        throw new RuntimeException("Please use TextInputLayout.setError() instead!");
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        throw new RuntimeException("Please use TextInputLayout.setError() instead!");
    }
}

package CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by JesperHansen on 2015-10-14.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context) {
        super(context);
        setFont();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Black.ttf");
        setTypeface(font);
    }
}

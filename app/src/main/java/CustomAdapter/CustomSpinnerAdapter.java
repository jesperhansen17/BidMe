package CustomAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jesper Hansen on 2015-10-16.
 */
public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    public CustomSpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }
    @Override
    public boolean isEnabled(int position) {
        if(position == 0)
        {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(setFont());
        TextView tv = view;
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        }
        else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(setFont());
        TextView tv = view;
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        }
        else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }

    private Typeface setFont() {
        return Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Regular.ttf");
    }
}


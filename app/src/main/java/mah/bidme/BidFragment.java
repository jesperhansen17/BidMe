package mah.bidme;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidFragment extends android.support.v4.app.Fragment {

    private int currBid = 0;//Change to value of current bid.
    private int yourBid = 0;//Change to value of current bid.

    public BidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid, container, false);

        FloatingActionButton addButt = (FloatingActionButton) v.findViewById(R.id.addButton);
        FloatingActionButton subButt = (FloatingActionButton) v.findViewById(R.id.subButton);
        FloatingActionButton checkButt = (FloatingActionButton) v.findViewById(R.id.checkButton);

        //Click made to add 5
        addButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addition();
                Log.i("Math:", "" + yourBid + "");
            }
        });

        //Click made to subtract 5
        subButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                subtraction();
                Log.i("Math:", "" + yourBid + "");
            }
        });

        //Click made to place bid
        checkButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                check();
                Log.i("Math:", "Create bid!");
            }
        });

        return v;
    }

    private void addition(){
        yourBid = yourBid + 5;
    }
    private void subtraction(){
        yourBid = yourBid - 5;
    }
    private void check(){
        //Send bid to database
    }
}
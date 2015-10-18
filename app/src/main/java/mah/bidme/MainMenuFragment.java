package mah.bidme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment implements View.OnClickListener {


    public MainMenuFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        //TextView headerMenu = (TextView) v.findViewById(R.id.menuTitle);
        Button addItemButton = (Button) v.findViewById(R.id.menuButtonAddItem);
        Button bidItemButton = (Button) v.findViewById(R.id.menuButtonBidItem);

        addItemButton.setOnClickListener(this);
        bidItemButton.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();

        switch (v.getId()){
            case R.id.menuButtonAddItem:
                fm.beginTransaction()
                        .replace(R.id.fragment_container, new ItemFragment())
                        .addToBackStack("")
                        .commit();
                break;
            case R.id.menuButtonBidItem:
                fm.beginTransaction()
                        .replace(R.id.fragment_container, new BidFragment())
                        .addToBackStack("")
                        .commit();
                break;
        }

    }
}

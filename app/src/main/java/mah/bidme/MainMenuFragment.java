package mah.bidme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
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
        setUpToolbar(v);
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
            case R.id.menuButtonShowItems:
                fm.beginTransaction()
                        .replace(R.id.fragment_container, new ShowItems())
                        .addToBackStack("")
                        .commit();
        }

    }

    /**
     * Method for setting up the custom Toolbar for AddItemFragment
     */
    private void setUpToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarMainMenu);
        //toolbar.inflateMenu(R.menu.add_item_menu);
        toolbar.setTitle("Main Menu");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTextIcons));
    }
}

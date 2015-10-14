package mah.bidme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {


    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Get a reference to the right child in Firebase
        final Firebase firebaseAddItem = Constants.myFirebaseRef.child("User").child("Mario").child("Items").child("Book");

        // Retreive the EditText from the XML
        final EditText itemTitle = (EditText) view.findViewById(R.id.input_item);
        final EditText itemAuthor = (EditText) view.findViewById(R.id.input_author);
        final EditText itemDesc = (EditText) view.findViewById(R.id.input_desc);
        final EditText itemPrice = (EditText) view.findViewById(R.id.input_price);

        // Retrieve the Buttons from the XML
        Button okBtn = (Button) view.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);

        // Add a listener to okBtn
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Convert the input from EditText to a String
                String title = itemTitle.getText().toString();
                String author = itemAuthor.getText().toString();
                String desc = itemDesc.getText().toString();

                // Convert the input from EditText to a String, then parse the String to a Integer
                int price = Integer.parseInt(itemPrice.getText().toString());

                // Update Firebase with the information the user had added
                firebaseAddItem.child(title).child("Title").setValue(title);
                firebaseAddItem.child(title).child("Description").setValue(desc);
                firebaseAddItem.child(title).child("Author").setValue(author);
                firebaseAddItem.child(title).child("Price").setValue(price);
            }
        });
        return view;
    }

}

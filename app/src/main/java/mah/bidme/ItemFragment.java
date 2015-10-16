package mah.bidme;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import CustomAdapter.CustomSpinnerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {
    private TextView mTitleOfView;
    private EditText mItemTitle, mItemPrice, mItemDesc;
    private TextInputLayout mInputLayoutTitle, mInputLayoutDesc, mInputLayoutPrice;
    private String mTypeOfItem;
    private Firebase mFirebaseAddItem;
    private Spinner mCategorySpinner;

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Get a reference to the right child in Firebase
        Constants.loggedInName = "Jesper Hansen";
        mFirebaseAddItem = Constants.myFirebaseRef.child("User").child(Constants.loggedInName).child("Items");

        // Retrieve the Title textView
        mTitleOfView = (TextView) view.findViewById(R.id.titleOfView);

        mInputLayoutTitle = (TextInputLayout) view.findViewById(R.id.input_layout_title);
        mInputLayoutDesc = (TextInputLayout) view.findViewById(R.id.input_layout_desc);
        mInputLayoutPrice = (TextInputLayout) view.findViewById(R.id.input_layout_price);

        // Retreive the EditText from the XML and set the text color of the Hint to GRAY
        mItemTitle = (EditText) view.findViewById(R.id.input_title);
        mItemTitle.setHintTextColor(Color.GRAY);

        mItemDesc = (EditText) view.findViewById(R.id.input_desc);
        mItemDesc.setHintTextColor(Color.GRAY);

        mItemPrice = (EditText) view.findViewById(R.id.input_price);
        mItemPrice.setHintTextColor(Color.GRAY);

        // Retrieve the Buttons from the XML
        Button okBtn = (Button) view.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);

        // Retreive the Spinner
        mCategorySpinner = (Spinner) view.findViewById(R.id.input_spinner);

        // Create the Spinner adapter
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.items_array)));

        // Apply the adapter to the Spinner
        mCategorySpinner.setAdapter(adapter);

        // Add an OnItemSelectedListener to the spinner
        mCategorySpinner.setOnItemSelectedListener(new SpinnerSelected());

        // Add a listener to okBtn
        okBtn.setOnClickListener(new AddItemToFirebase());

        return view;
    }

    private class AddItemToFirebase implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mItemTitle.getText().toString().isEmpty()) {
                mItemTitle.setError("Enter a title");
            }
            if (mItemDesc.getText().toString().isEmpty()) {
                mItemDesc.setError("Enter a description");
            }
            if (mItemPrice.getText().toString().isEmpty()) {
                mItemPrice.setError("Enter a price");
            }
            if (mCategorySpinner.getSelectedItemPosition() == 0) {
                Toast.makeText(getContext(), "Choose an category", Toast.LENGTH_SHORT).show();
            }
            if ((!mItemTitle.getText().toString().isEmpty()) && (!mItemDesc.getText().toString().isEmpty()) && (!mItemPrice.getText().toString().isEmpty())){
                // Convert the input from EditText to a String
                String title = mItemTitle.getText().toString();
                String desc = mItemDesc.getText().toString();

                // Convert the input from EditText to a String, then parse the String to a Integer
                int price = Integer.parseInt(mItemPrice.getText().toString());


                Map<String, Object> itemInfo = new HashMap<String, Object>();
                itemInfo.put("Title", title);
                itemInfo.put("Description", desc);
                itemInfo.put("Price", price);

                mFirebaseAddItem.child(mTypeOfItem).child(title).setValue(itemInfo, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Toast.makeText(getContext(), "Item not added to auction, please try again", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Item added to auction", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private class SpinnerSelected implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mTypeOfItem = parent.getItemAtPosition(position).toString();
            mTitleOfView.setText("Add " + mTypeOfItem);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}

package mah.bidme;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mah.bidme.CustomAdapter.CustomSpinnerAdapter;
import mah.bidme.model.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {
    private TextView mTitleOfView;
    private EditText mItemTitle, mItemPrice, mItemDesc;
    private Button mOkBtn, mCancelBtn, mPhotoBtn, mShowPhotoBtn;
    private String mTypeOfItem, mPhotoStr;
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
        mFirebaseAddItem = Constants.myFirebaseRef.child("items");

        // Retrieve the Title textView
        mTitleOfView = (TextView) view.findViewById(R.id.titleOfView);

        // Retreive the EditText from the XML and set the text color of the Hint to GRAY
        mItemTitle = (EditText) view.findViewById(R.id.input_title);
        mItemTitle.setHintTextColor(Color.GRAY);

        mItemDesc = (EditText) view.findViewById(R.id.input_desc);
        mItemDesc.setHintTextColor(Color.GRAY);

        mItemPrice = (EditText) view.findViewById(R.id.input_price);
        mItemPrice.setHintTextColor(Color.GRAY);

        // Retrieve the Buttons from the XML
        mOkBtn = (Button) view.findViewById(R.id.okBtn);
        mCancelBtn = (Button) view.findViewById(R.id.cancelBtn);
        mPhotoBtn = (Button) view.findViewById(R.id.takePhotoBtn);
        mShowPhotoBtn = (Button) view.findViewById(R.id.showTakenPhotoBtn);

        // Retreive the Spinner
        mCategorySpinner = (Spinner) view.findViewById(R.id.input_spinner);

        // Create the Spinner adapter
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.items_array)));

        // Apply the adapter to the Spinner
        mCategorySpinner.setAdapter(adapter);

        // Check if the running device has a camera
        if (!hasCamera())
            mPhotoBtn.setEnabled(false);

        // Make show photo button not enabled before a photo is taken
        mShowPhotoBtn.setEnabled(false);

        // Add an OnItemSelectedListener to the spinner
        mCategorySpinner.setOnItemSelectedListener(new SpinnerSelected());

        // Add a listener to the buttons
        mOkBtn.setOnClickListener(new AddItemListener());
        mCancelBtn.setOnClickListener(new AddItemListener());
        mPhotoBtn.setOnClickListener(new AddItemListener());
        mShowPhotoBtn.setOnClickListener(new AddItemListener());


        return view;
    }

    // Returns true if the running device has a camera
    private boolean hasCamera() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    // Method that returns the taken picture from the camera application and
    // convert the Bitmap to a String and store the String in a private variable
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            mShowPhotoBtn.setEnabled(true);
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            mPhotoStr = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    // Method that reads the image String and converts it back to a thumbnail image
    public Bitmap getPhotoImage() {
        byte[] imageAsByte = Base64.decode(mPhotoStr, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsByte, 0, imageAsByte.length);
    }

    // Private class that implements an OnClickListener that handles the two buttons
    private class AddItemListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.okBtn:
                    addItemToFirebase();
                    break;
                case R.id.cancelBtn:
                    cancelAddItemFragment();
                    break;
                case R.id.takePhotoBtn:
                    launchCamera(getView());
                    break;
                case R.id.showTakenPhotoBtn:
                    showTakenImage();
                    break;
            }
        }

        // Private method that checks if the user has added the correct information.
        // If so the item will be added to Firebase otherwise the screen will tell the user
        // that the information was not correctly added
        private void addItemToFirebase() {
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
            if ((!mItemTitle.getText().toString().isEmpty()) && (!mItemDesc.getText().toString().isEmpty()) && (!mItemPrice.getText().toString().isEmpty()) && mCategorySpinner.getSelectedItemPosition() != 0 && mShowPhotoBtn.isEnabled()){
                // Convert the input from EditText to a String
                String title = mItemTitle.getText().toString();
                String desc = mItemDesc.getText().toString();

                // Convert the input from EditText to a String, then parse the String to a Integer
                int price = Integer.parseInt(mItemPrice.getText().toString());

                // Add the information to a HashMap before sending it to Firebase
                Map<String, Object> itemInfo = new HashMap<String, Object>();
                itemInfo.put("title", title);
                itemInfo.put("description", desc);
                itemInfo.put("price", price);
                itemInfo.put("currentprice", price);
                itemInfo.put("seller", Constants.loggedInName);
                itemInfo.put("sold", false);
                itemInfo.put("type", mTypeOfItem);
                itemInfo.put("image", mPhotoStr);

                // Set the HashMap to the Firebase, make a Toast to show the user if the item been added to Firebase or not
                mFirebaseAddItem.child(UUID.randomUUID().toString()).setValue(itemInfo, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Toast.makeText(getContext(), "Item not added to auction, please try again", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Item added to auction", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                clearAllFields();

                // Vibrate the phone when the user adds an Item for extra feedback to the user
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);

                userChoices();
            }
        }

        // Method for reseting all inputfields when a new item is added to Firebase
        private void clearAllFields() {
            mItemTitle.setText("");
            mItemDesc.setText("");
            mItemPrice.setText("");
            mCategorySpinner.setSelection(0);
            mShowPhotoBtn.setEnabled(false);
        }

        // Method that prompts an AlertDialog to the user where the user can make a one
        // of three different choices
        private void userChoices() {
            AlertDialog.Builder choiceDialog = new AlertDialog.Builder(getActivity());
            choiceDialog.setTitle("Options!");
            choiceDialog.setItems(R.array.add_item_choice_array, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            // Do nothing, just return back to add item fragment
                            break;
                        case 1:
                            // Create an new Fragment that show the added items
                            break;
                        case 2:
                            cancelAddItemFragment();
                            break;
                    }
                }
            });
            choiceDialog.create().show();
        }

        // Go back to main menu
        private void cancelAddItemFragment() {
            getActivity().getSupportFragmentManager().popBackStack();
        }

        // Starts the camera application using an Intent
        private void launchCamera(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }

        // Show the taken image as an thumbnail
        private void showTakenImage() {
            AlertDialog.Builder photoDialog = new AlertDialog.Builder(getActivity());
            photoDialog.setTitle("Taken Photo");
            photoDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing for now
                }
            });

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());

            final View view = layoutInflater.inflate(R.layout.image_dialog, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.showPhoto);
            imageView.setImageBitmap(getPhotoImage());

            photoDialog.setView(view);
            photoDialog.create().show();
        }

    }

    // A inner private class for the spinner which contains the different types of items
    private class SpinnerSelected implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mTypeOfItem = parent.getItemAtPosition(position).toString();
            if (parent.getSelectedItemPosition() == 0) {
                mTitleOfView.setText(mTypeOfItem);
            } else {
                mTitleOfView.setText("Add " + mTypeOfItem);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}

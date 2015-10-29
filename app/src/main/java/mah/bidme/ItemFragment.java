package mah.bidme;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import mah.bidme.CustomAdapter.CustomSpinnerAdapter;
import mah.bidme.model.Item;

/**
 * Fragment that contains the all of the components for adding an item to the Firebase database
 */
public class ItemFragment extends Fragment {
    private TextView mTitleOfView;
    private EditText mItemTitle, mItemPrice, mItemDesc;
    private FloatingActionButton mAddBtn;
    private String mTypeOfItem, mPhotoStr;
    private Firebase mFirebaseAddItem;
    private Spinner mCategorySpinner;
    private boolean mSold = false;
    private boolean mUpForSale = false;
    private boolean mPhotoTaken;

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Sets up the custom Toolbar
        setUpToolbar(view);

        // Get a reference to the right child in Firebase
        mFirebaseAddItem = Utility.myFirebaseRef.child("items");

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
        mAddBtn = (FloatingActionButton) view.findViewById(R.id.floating_button_add_item);

        // Add listener to FAB
        mAddBtn.setOnClickListener(new AddItemListener());

        // Retreive the Spinner
        mCategorySpinner = (Spinner) view.findViewById(R.id.input_spinner);

        // Create the Spinner adapter
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.items_array)));

        // Apply the adapter to the Spinner
        mCategorySpinner.setAdapter(adapter);

        mPhotoTaken = false;

        // Add an OnItemSelectedListener to the spinner
        mCategorySpinner.setOnItemSelectedListener(new SpinnerSelected());

        return view;
    }

    /**
     * Returns true if the running device has a camera
     * @return boolean that informs the user if the current Android phone has a camera
     */
    private boolean hasCamera() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    /**
     * Method that returns the taken picture from the camera application and
     * convert the Bitmap to a String and store the String in a private variable
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Retreive the bidme image from the SD Card
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath(), "bidme.jpg");
            Uri uri = Uri.fromFile(file);

            Bitmap imageBitmap;

            try {
                // Convert the Image to a Bitmap
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                // Rescale the Bitmap
                imageBitmap = crupAndScale(imageBitmap, 1000);

                // Convert to a String
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                mPhotoStr = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException IOe) {
                IOe.printStackTrace();
            }
        }
    }

    /**
     * Method for rescaling the Image
     */
    public static  Bitmap crupAndScale (Bitmap source,int scale){
        int factor = source.getHeight() <= source.getWidth() ? source.getHeight(): source.getWidth();
        int longer = source.getHeight() >= source.getWidth() ? source.getHeight(): source.getWidth();
        int x = source.getHeight() >= source.getWidth() ?0:(longer-factor)/2;
        int y = source.getHeight() <= source.getWidth() ?0:(longer-factor)/2;
        source = Bitmap.createBitmap(source, x, y, factor, factor);
        source = Bitmap.createScaledBitmap(source, scale, scale, false);
        return source;
    }

    /**
     * Method for setting up the custom Toolbar for AddItemFragment
     */
    @TargetApi(21)
    private void setUpToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarAddItem);

        toolbar.setTitle("Add item");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTextIcons));
        toolbar.inflateMenu(R.menu.add_item_menu);

        toolbar.setElevation(10);

        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.take_image:
                        launchCamera();
                        return true;
                    case R.id.show_image:
                        if (mPhotoTaken) {
                            showTakenImage();
                        } else {
                            Toast.makeText(getContext(), "Take photo first!", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.drawable.arrow_back_white:
                        getActivity().getSupportFragmentManager().popBackStack();
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * Starts the camera application using an Intent
     */
    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // this part to save captured image on provided path
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "bidme.jpg");
        Uri photoPath = Uri.fromFile(file);

        // Put the Uri to the Image in the Intent
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);

        // Update boolean that keeps track if an photo has been taken
        mPhotoTaken = true;

        // Start the Camera application
        startActivityForResult(intent, 1);
    }

    /**
     * Show the taken image as an thumbnail
     */
    private void showTakenImage() {
        AlertDialog.Builder photoDialog = new AlertDialog.Builder(getActivity());
        photoDialog.setTitle("Taken Photo");
        photoDialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing for now
            }
        });

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        final View view = layoutInflater.inflate(R.layout.image_dialog, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.showPhoto);
        imageView.setImageBitmap(Utility.getPhotoImage(mPhotoStr));

        photoDialog.setView(view);
        photoDialog.create().show();
    }

    /**
     * Private class that implements an OnClickListener that handles the two buttons
     */
    private class AddItemListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floating_button_add_item:
                    addItemToFirebase();
                    break;
            }
        }

        /**
         * Private method that checks if the user has added the correct information.
         * If so the item will be added to Firebase otherwise the screen will tell the user
         * that the information was not correctly added
         */
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

            // Check if all information is added before send the item to Firebase
            if ((!mItemTitle.getText().toString().isEmpty()) && (!mItemDesc.getText().toString().isEmpty()) && (!mItemPrice.getText().toString().isEmpty()) && mCategorySpinner.getSelectedItemPosition() != 0 && mPhotoTaken){
                // Convert the input from EditText to a String
                String title = mItemTitle.getText().toString();
                String desc = mItemDesc.getText().toString();

                // Convert the input from EditText to a String, then parse the String to a Integer
                int price = Integer.parseInt(mItemPrice.getText().toString());

                // Make a new Firebase reference so that the Item can have the key as a attribute
                Firebase firebaseId = mFirebaseAddItem.push();

                // Create an new Item
                Item item = new Item(title, desc, price, firebaseId.getKey(), Utility.loggedInName, mTypeOfItem, 60, mSold, mUpForSale,  mPhotoStr);

                // Set the HashMap to the Firebase, make a Toast to show the user if the item been added to Firebase or not
                firebaseId.setValue(item, new Firebase.CompletionListener() {
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

                // Method for vibrate the phone 100 milliseconds
                Utility.vibratePhone(getActivity(), 100);

            } else {
                Toast.makeText(getContext(), "Please take an image!", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * Method for reseting all inputfields when a new item is added to Firebase
         */
        private void clearAllFields() {
            mItemTitle.setText("");
            mItemDesc.setText("");
            mItemPrice.setText("");
            mCategorySpinner.setSelection(0);
            mItemTitle.requestFocus();
        }
    }

    /**
     * A inner private class for the spinner which contains the different types of items
     */
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

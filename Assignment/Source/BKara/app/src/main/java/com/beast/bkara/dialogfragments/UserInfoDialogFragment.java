package com.beast.bkara.dialogfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.beast.bkara.R;

/**
 * A simple {@link DialogFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInfoDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserInfoDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText username, email , firstName, lastName ,address, country;
    Button editBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserInfoDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInfoDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoDialogFragment newInstance(String param1, String param2) {
        UserInfoDialogFragment fragment = new UserInfoDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_info_dialog, container, false);

        username = (EditText) v.findViewById(R.id.user_info_username);
        email = (EditText) v.findViewById(R.id.user_info_email);
        firstName = (EditText) v.findViewById(R.id.user_info_first_name);
        lastName = (EditText) v.findViewById(R.id.user_info_last_name);
        address = (EditText) v.findViewById(R.id.user_info_address);
        country = (EditText) v.findViewById(R.id.user_info_country);

        editBtn = (Button) v.findViewById(R.id.user_info_edit_button);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditText(toggleEditMode);
            }
        });

        return v;
    }


    boolean toggleEditMode = true;
    private void toggleEditText(boolean visible) {
        username.setEnabled( visible);
        email.setEnabled( visible);
        firstName.setEnabled( visible);
        lastName.setEnabled( visible);
        address.setEnabled( visible);
        country.setEnabled( visible);

        toggleEditMode = !toggleEditMode;
        if( toggleEditMode )
            editBtn.setText("Edit");
        else editBtn.setText("Save");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

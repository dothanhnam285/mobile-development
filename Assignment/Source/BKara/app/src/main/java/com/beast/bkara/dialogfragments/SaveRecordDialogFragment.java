package com.beast.bkara.dialogfragments;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.beast.bkara.R;
import com.beast.bkara.util.RecordPlayerHandler;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaveRecordDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaveRecordDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveRecordDialogFragment extends DialogFragment {

    private RecordPlayerHandler recordPlayerHandler;
    private TextView textViewSave;
    private TextView textViewDiscard;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RECORD_PATH = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String recordPath;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SaveRecordDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recordPath Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveRecordDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaveRecordDialogFragment newInstance(String recordPath, String param2) {
        SaveRecordDialogFragment fragment = new SaveRecordDialogFragment();
        Bundle args = new Bundle();
        args.putString(RECORD_PATH, recordPath);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recordPath = getArguments().getString(RECORD_PATH);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d("SAVE RECORD", recordPath);
        recordPlayerHandler = new RecordPlayerHandler(getActivity(), false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.fragment_save_record_dialog, container, false);

        ToggleButton toggleButton = (ToggleButton) v.findViewById(R.id.frag_save_record_btnPlay);
        SeekBar seekBar = (SeekBar) v.findViewById(R.id.frag_save_record_seekBar);
        textViewSave = (TextView) v.findViewById(R.id.frag_save_record_textview_save);
        textViewDiscard = (TextView) v.findViewById(R.id.frag_save_record_textview_discard);

        recordPlayerHandler.AddRecordInfo(toggleButton, seekBar, null, recordPath);

        textViewDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File recordFile = new File(recordPath);
                recordFile.delete();
                getDialog().dismiss();
            }
        });

        textViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        int width = getResources().getDimensionPixelSize(R.dimen.save_record_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.save_record_dialog_height);
        getDialog().getWindow().setLayout(width, height);
        super.onResume();
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
    public void onStop() {
        recordPlayerHandler.StopMediaPlayer();
        super.onStop();
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

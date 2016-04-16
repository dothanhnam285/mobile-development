package com.beast.bkara.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.beast.bkara.Controller;
import com.beast.bkara.R;
import com.beast.bkara.databinding.FragmentKaraokeBinding;
import com.beast.bkara.model.Song;
import com.beast.bkara.viewmodel.RecordViewModel;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KaraokeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KaraokeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KaraokeFragment extends Fragment {

    private ToggleButton btnRecord;
    private ToggleButton btnExpand;
    private ExpandableRelativeLayout erlSongInfo;

    RecordViewModel recordVm;
    Song song;
    FragmentKaraokeBinding binding;

    // Controller
    private Controller controller;

    // Media Recorder
    private MediaRecorder mRecorder = null;

    // Media Player
    private MediaPlayer mediaPlayer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public KaraokeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KaraokeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KaraokeFragment newInstance(String param1, String param2) {
        KaraokeFragment fragment = new KaraokeFragment();
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

        recordVm = new RecordViewModel();
        song = new Song();
        song.setTitle("Chac ai do se ve");
        song.setSinger("Son Tung");
        song.setView(4096);
        song.setVideo_id("vtxn0i4CVX8");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_karaoke, container, false);
        binding.setRecordVm(recordVm);
        binding.setSong(song);
        View v = binding.getRoot();

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.frag_karaoke_youtubeViewer, youTubePlayerFragment).commit();

        controller = (Controller) getActivity().getApplicationContext();
        youTubePlayerFragment.initialize(controller.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.loadVideo(song.getVideo_id());
                    youTubePlayer.play();

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                Log.d("errorMessage: ", errorMessage);
            }
        });

        btnRecord = (ToggleButton) v.findViewById(R.id.frag_karaoke_btnRecord);
        btnRecord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    startRecording("test");
                else
                    stopRecording();
            }
        });

        btnExpand = (ToggleButton) v.findViewById(R.id.frag_karaoke_btnExpand);
        erlSongInfo = (ExpandableRelativeLayout) v.findViewById(R.id.frag_karaoke_erl_songInfo);
        btnExpand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCollapse) {
                if (isCollapse)
                    erlSongInfo.collapse();
                else
                    erlSongInfo.expand();
            }
        });

        return v;
    }

    private void startRecording(String filename) {
        String path = Environment.getExternalStorageDirectory() + "/";
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioEncodingBitRate(96000);
        mRecorder.setAudioSamplingRate(44100);

        mRecorder.setOutputFile(path + "/" + filename + ".m4a");

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
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
    public void onStop() {
        super.onStop();
        if (btnRecord.isChecked())
            btnRecord.toggle();
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

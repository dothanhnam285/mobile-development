package com.beast.bkara.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.beast.bkara.MainActivity;
import com.beast.bkara.R;
import com.beast.bkara.databinding.FragmentListSongBinding;
import com.beast.bkara.model.Song;
import com.beast.bkara.util.ItemClickSupport;
import com.beast.bkara.viewmodel.SongViewModel;

/**
 * Created by Darka on 4/10/2016.
 */
public class SongListFragment extends Fragment {

    FragmentListSongBinding binding;

    RecyclerView rvSongList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String WHICH_LIST = "param1";

    // TODO: Rename and change types of parameters
    private int whichList;

    private OnFragmentInteractionListener mListener;

    public SongListFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongListFragment newInstance(int param1) {
        SongListFragment fragment = new SongListFragment();
        Bundle args = new Bundle();
        args.putInt(WHICH_LIST, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            whichList = getArguments().getInt(WHICH_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final SongsFragment parentFragment = (SongsFragment) getParentFragment();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_song, container, false);
        binding.setSongVm(parentFragment.getSongViewModel());
        switch (whichList) {
            case R.string.frag_songs_tab_all:
                binding.setWhichList(0);
                break;
            case R.string.frag_songs_tab_hot:
                binding.setWhichList(1);
                break;
            case R.string.frag_songs_tab_new:
                binding.setWhichList(2);
                break;
        }

        View v = binding.getRoot();
        rvSongList = (RecyclerView) v.findViewWithTag("frag_list_song_rvSongList");
        ItemClickSupport.addTo(rvSongList).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Song song = null;
                        switch (whichList) {
                            case R.string.frag_songs_tab_all:
                                song = parentFragment.getSongViewModel().songListAll.get(position);
                                break;
                            case R.string.frag_songs_tab_hot:
                                song = parentFragment.getSongViewModel().songListHot.get(position);
                                break;
                            case R.string.frag_songs_tab_new:
                                song = parentFragment.getSongViewModel().songListNew.get(position);
                                break;
                        }
                        if (song != null) {
                            Fragment karaokeFragment = KaraokeFragment.newInstance(song, "bla");
                            ((MainActivity) getActivity()).displayCustomFragment(karaokeFragment, "Karaoke");
                        }
                    }
                }
        );

        return v;
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

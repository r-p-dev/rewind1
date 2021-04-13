package com.digital.rewind.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.itemAdapters.itemAdapterPlaylist;
import com.digital.rewind.modals.modalPlaylist;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView playlistRecycler;
    List<modalPlaylist> playlistItemList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public PlayListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayListFragment newInstance(String param1, String param2) {
        PlayListFragment fragment = new PlayListFragment();
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
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        playlistRecycler = view.findViewById(R.id.playlist_recycler);
        //init data
        playlistRecycler.setAdapter(new itemAdapterPlaylist(playlist_initData()));
        return view;
    }

    private List<modalPlaylist> playlist_initData() {
        playlistItemList = new ArrayList<>();
        playlistItemList.add(new modalPlaylist(R.drawable.mumu, "12", "name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.headphone, "1", "name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.mumu, "0", "name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.headphone, "122", "name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.green_fill__rounded_color, "12", "name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.mumu, "120", "name of playlist 1name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.blue_bg, "112", "name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.mumu, "129", "name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.headphone, "2", "name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.ic_home, "12", "name of playlist 1"));
        playlistItemList.add(new modalPlaylist(R.drawable.ic_closex, "102", "name of playlist 1"));


        return playlistItemList;
    }
}
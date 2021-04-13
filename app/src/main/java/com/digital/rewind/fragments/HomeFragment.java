package com.digital.rewind.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.itemAdapters.itemAdapterHomePopularArtist;
import com.digital.rewind.itemAdapters.itemAdapterHomePopularPlaylist;
import com.digital.rewind.itemAdapters.itemAdapterHomePopularSongs;
import com.digital.rewind.itemAdapters.itemAdapterHomeRecomended;
import com.digital.rewind.modals.modalHomePopularArtist;
import com.digital.rewind.modals.modalHomePopularPlaylist;
import com.digital.rewind.modals.modalHomePopularSongs;
import com.digital.rewind.modals.modalHomeRecomended;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Fragment fragment = null;
    RecyclerView wt_recyclerView;
    RecyclerView pp_recyclerView;
    RecyclerView ps_recyclerView;
    RecyclerView pa_recyclerView;
    List<modalHomeRecomended> wt_itemlist;
    List<modalHomePopularPlaylist> pp_itemlist;
    List<modalHomePopularSongs> ps_itemlist;
    List<modalHomePopularArtist> pa_itemlist;
    ImageView morePlaylist, moreSongs;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        morePlaylist = view.findViewById(R.id.more_popular_playlist);
        moreSongs = view.findViewById(R.id.more_popular_songs);


        wt_recyclerView = view.findViewById(R.id.weekly_trending_recycler);
        pp_recyclerView = view.findViewById(R.id.populor_playlist_recycler);
        ps_recyclerView = view.findViewById(R.id.populor_songs_recycler);
        pa_recyclerView = view.findViewById(R.id.populor_artist_recycler);


//initData();
        wt_recyclerView.setAdapter(new itemAdapterHomeRecomended(wt_initData()));
        pp_recyclerView.setAdapter(new itemAdapterHomePopularPlaylist(pp_initData()));
        ps_recyclerView.setAdapter(new itemAdapterHomePopularSongs(ps_initData()));
        pa_recyclerView.setAdapter(new itemAdapterHomePopularArtist(pa_initData()));
        morePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new PlayListFragment();
                loadFragment(fragment);
            }
        });
        moreSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new SongsFragment();
                loadFragment(fragment);
            }
        });

        return view;

    }

    private void loadFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }


    private List<modalHomePopularArtist> pa_initData() {
        pa_itemlist = new ArrayList<>();
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist1, "Artist 1"));
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist2, "Aartist2"));
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist3, "Aartist3"));
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist4, "Aartist4"));
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist5, "Aartist5"));
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist4, "Aartist4"));
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist2, "Aartist2"));
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist5, "Aartist5"));
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist2, "Aartist2"));
        pa_itemlist.add(new modalHomePopularArtist(R.drawable.artist1, "Artist 1"));
        return pa_itemlist;

    }

    private List<modalHomePopularSongs> ps_initData() {
        ps_itemlist = new ArrayList<>();
        ps_itemlist.add(new modalHomePopularSongs(R.drawable.blue_bg, "namaamasna"));
        ps_itemlist.add(new modalHomePopularSongs(R.drawable.blue_bg, "namaamasna"));
        ps_itemlist.add(new modalHomePopularSongs(R.drawable.blue_bg, "namaamasna"));
        ps_itemlist.add(new modalHomePopularSongs(R.drawable.blue_bg, "namaamasna"));
        ps_itemlist.add(new modalHomePopularSongs(R.drawable.blue_bg, "namaamasna"));
        ps_itemlist.add(new modalHomePopularSongs(R.drawable.blue_bg, "namaamasna"));
        ps_itemlist.add(new modalHomePopularSongs(R.drawable.blue_bg, "namaamasna"));
        ps_itemlist.add(new modalHomePopularSongs(R.drawable.blue_bg, "namaamasna"));
        ps_itemlist.add(new modalHomePopularSongs(R.drawable.blue_bg, "namaamasna"));
        return ps_itemlist;

    }

    private List<modalHomePopularPlaylist> pp_initData() {
        pp_itemlist = new ArrayList<>();
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.mumu, "how do you do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.headphone, "how  do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.mumu, "how do you do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.headphone, "how  do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.mumu, "how do you do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.headphone, "how  do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.mumu, "how do you do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.headphone, "how  do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.mumu, "how do you do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.headphone, "how  do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.mumu, "how do you do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.headphone, "how  do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.mumu, "how do you do"));
        pp_itemlist.add(new modalHomePopularPlaylist(R.drawable.headphone, "how  do"));
        return pp_itemlist;
    }

    private List<modalHomeRecomended> wt_initData() {
        wt_itemlist = new ArrayList<>();
        wt_itemlist.add(new modalHomeRecomended(R.drawable.mumu, "this is my logo"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.headphone, "this is my fb"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.ic_download, "this is my d"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.logo, "this is my logo"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.ic_facebook, "this is my fb"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.ic_download, "this is my d"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.logo, "this is my logo"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.ic_facebook, "this is my fb"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.ic_download, "this is my d"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.logo, "this is my logo"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.ic_facebook, "this is my fb"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.ic_download, "this is my d"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.logo, "this is my logo"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.ic_facebook, "this is my fb"));
        wt_itemlist.add(new modalHomeRecomended(R.drawable.ic_download, "this is my d"));


        return wt_itemlist;
    }

}
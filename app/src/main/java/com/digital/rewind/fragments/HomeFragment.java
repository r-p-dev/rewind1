package com.digital.rewind.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.itemAdapters.itemAdapterHomePopularArtist;
import com.digital.rewind.itemAdapters.itemAdapterHomePopularPlaylist;
import com.digital.rewind.itemAdapters.itemAdapterHomePopularSongs;
import com.digital.rewind.itemAdapters.itemAdapterHomeRecomended;
import com.digital.rewind.modals.modalArtist;
import com.digital.rewind.modals.modalHomeRecomendedSongs;
import com.digital.rewind.modals.modalSongs;
import com.digital.rewind.modals.modelHomePopulorPlaylist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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


    List<modalHomeRecomendedSongs> wt_itemlist;
    List<modelHomePopulorPlaylist> pp_itemlist;
    List<modalSongs> ps_itemlist;
    List<modalArtist> pa_itemlist;


    itemAdapterHomePopularSongs hp_songadapter;
    itemAdapterHomePopularPlaylist hp_playlistadapter;
    itemAdapterHomeRecomended wt_songadapter;
    itemAdapterHomePopularArtist hp_artistadapter;
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



        wt_recyclerView = view.findViewById(R.id.weekly_trending_recycler);
        pp_recyclerView = view.findViewById(R.id.populor_playlist_recycler);
        ps_recyclerView = view.findViewById(R.id.populor_songs_recycler);
        pa_recyclerView = view.findViewById(R.id.populor_artist_recycler);


//initData();
        ProgressDialog pd=new ProgressDialog(getContext());
        pd.setTitle("Loading Data.....");
        pd.show();
        wt_initData();
        pp_initData();
        ps_initData();
        pa_initData();
        pd.dismiss();
        return view;


    }

    private void pp_initData() {
            pp_itemlist = new ArrayList<>();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Playlist/Shared");
            Query squry=databaseReference.limitToLast(70);
            squry.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    pp_itemlist.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        modelHomePopulorPlaylist song = ds.getValue(modelHomePopulorPlaylist.class);
                        pp_itemlist.add(song);


                    }
                    hp_playlistadapter = new itemAdapterHomePopularPlaylist(getContext(),pp_itemlist);
                    pp_recyclerView.setAdapter(hp_playlistadapter);
                    hp_playlistadapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "FAILED!", Toast.LENGTH_SHORT).show();
                }
            });
        }



    private void loadFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }


    private void pa_initData() {
        pa_itemlist = new ArrayList<>();
       Query dr=FirebaseDatabase.getInstance().getReference("Artist").limitToLast(20);
       dr.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               pa_itemlist.clear();
               for (DataSnapshot dsa : snapshot.getChildren()) {
                   modalArtist art = dsa.getValue(modalArtist.class);

                   pa_itemlist.add(art);
               }
               hp_artistadapter = new itemAdapterHomePopularArtist(getContext(),pa_itemlist);
               pa_recyclerView.setAdapter(hp_artistadapter);
               hp_artistadapter.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


    }

    private void ps_initData() {
        ps_itemlist = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Songs");
        Query qury= databaseReference.limitToLast(12);
        qury.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ps_itemlist.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    modalSongs song = ds.getValue(modalSongs.class);
                    ps_itemlist.add(song);
                }
                hp_songadapter = new itemAdapterHomePopularSongs(getContext(),ps_itemlist);
                ps_recyclerView.setAdapter(hp_songadapter);
                hp_songadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void wt_initData() {
        wt_itemlist = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("RecomendedSongs");
        Query qury= databaseReference.limitToLast(12);
        qury.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wt_itemlist.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    modalHomeRecomendedSongs song = ds.getValue(modalHomeRecomendedSongs.class);
                    wt_itemlist.add(song);
                }
                wt_songadapter = new itemAdapterHomeRecomended(getContext(),wt_itemlist);
                wt_recyclerView.setAdapter(wt_songadapter);
                wt_songadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getsong(String lkey) {
        Query rsquery=FirebaseDatabase.getInstance().getReference("Songs/-"+lkey);
        rsquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot sp) {
                System.out.println("print snapshot....................................................................................................................................");
                System.out.println(sp);
                for (DataSnapshot sn: sp.getChildren()){
                    modalHomeRecomendedSongs song = sn.getValue(modalHomeRecomendedSongs.class);
                    System.out.println("Inner data print...................................................................................................................................");
                    System.out.println(song);
                    wt_itemlist.add(song);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
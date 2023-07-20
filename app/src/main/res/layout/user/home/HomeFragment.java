package khanhnqph30151.fptpoly.duan1.user.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import khanhnqph30151.fptpoly.duan1.R;
import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    HomeDAO homeDAO;
    ArrayList<Home> listHome;
    HomeAdapter adapter;
    Context context;


    ViewPager viewPager;
    CircleIndicator circleIndicator;
    SlideAdapter slideAdapter;
    ArrayList<Slide> listPhoto;
    Timer timer;
    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_fragment_home_listFood);

        ImageButton img_tápearch = view.findViewById(R.id.btn_fragment_home_tapSearch);
        EditText edSearch = view.findViewById(R.id.ed_fragment_home_search);

        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        listPhoto = getListPhoto();
        slideAdapter = new SlideAdapter(getActivity(), listPhoto);
        viewPager.setAdapter(slideAdapter);
        circleIndicator.setViewPager(viewPager);
        slideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        img_tápearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edSearch.length()>0){
                    String searchName = edSearch.getText().toString();
                    LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    HomeDAO homeDAO1 = new HomeDAO(getContext());
                    listHome = new ArrayList<>();
                    listHome = homeDAO1.Search(searchName);
                    adapter.setData(listHome);
                    recyclerView.setAdapter(adapter);
                }else {
                    reloadData();
                }
            }
        });

        autoSlideShow();
        reloadData();


    }
    private ArrayList<Slide> getListPhoto(){
        ArrayList<Slide> list = new ArrayList<>();
        list.add(new Slide(R.drawable.banner_home1));
        list.add(new Slide(R.drawable.banner_home2));
        list.add(new Slide(R.drawable.banner_home3));
        list.add(new Slide(R.drawable.banner_home4));
        return list;
    }
    private void autoSlideShow(){
        if (listPhoto == null || listPhoto.isEmpty() || viewPager == null){
            return;
        }
        if (timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = listPhoto.size()-1;
                        if (currentItem < totalItem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

    private void reloadData(){
        homeDAO = new HomeDAO(getContext());
        listHome = homeDAO.getAllData();
        adapter = new HomeAdapter(getContext(),listHome,homeDAO);
        adapter.setData(listHome);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }

}
package com.smartysoft.receipeapp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.smartysoft.receipeapp.R;
import com.smartysoft.receipeapp.adapter.RecyclerViewAdapter;
import com.smartysoft.receipeapp.alertbanner.AlertDialogForAnything;
import com.smartysoft.receipeapp.appdata.GlobalAppAccess;
import com.smartysoft.receipeapp.appdata.MydApplication;
import com.smartysoft.receipeapp.eventListener.EndlessRecyclerViewScrollListener;
import com.smartysoft.receipeapp.eventListener.RecyclerItemClickListener;
import com.smartysoft.receipeapp.model.Recepie;
import com.smartysoft.receipeapp.model.RecepieInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubayer on 8/10/2017.
 */

public class LatestMovieFragment extends Fragment {

    private static final  String TAG_REQUEST_LIST_PAGE = "list_page";
    private static final int  KEY_GRID_VIEW = 0;
    private static final int  KEY_LIST_VIEW = 1;
    private static  int  KEY_CURRENT_VIEW = KEY_LIST_VIEW;
    // private GridView gridView;
    private RecyclerView recyclerView;
    /// private IconGridAdapter iconGridAdapter;
    private RecyclerViewAdapter recyclerViewAdapter;

    List<Recepie> movieList = new ArrayList<>();

    private Gson gson;

    private EndlessRecyclerViewScrollListener gridScrollListener;

    private EndlessRecyclerViewScrollListener listScrollListener;

    LinearLayoutManager listLayoutManager;
    GridLayoutManager gridLayoutManager;

    private FloatingActionButton fabTopToTheList;

    public LatestMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_latestmovie, container, false);

        init(view);

        initAdapter();

        initRecyclerViewOnItemClickListener();

        ViewCompat.setNestedScrollingEnabled(recyclerView, true);

        return view;
        // Inflate the layout for this fragment
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        sendRequestToGetPlaceList(GlobalAppAccess.URL_ITEM_LIST, true);
    }

    private void init(View view) {

        gson = new Gson();
        // gridView = (GridView) view.findViewById(R.id.gridview_latestmovie);

        fabTopToTheList = (FloatingActionButton) view.findViewById(R.id.fabTopToTheList);
        fabTopToTheList.setVisibility(View.GONE);
        fabTopToTheList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recyclerView.scrollToPosition(0);
                recyclerView.smoothScrollToPosition(0);
                //recyclerView.setScrollY(0);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }


    private void initAdapter() {
        // iconGridAdapter = new IconGridAdapter(getActivity(), movieList);
        //gridView.setAdapter(iconGridAdapter);

        listLayoutManager = new LinearLayoutManager(getActivity());
        listLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final int numberOfColumns = 3;
        gridLayoutManager = new GridLayoutManager(getActivity(),numberOfColumns);


        gridScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
               // loadNextDataFromApi(page);
                Log.d("DEBUG",String.valueOf(page));
                if(nextPageToken != null && !nextPageToken.isEmpty())
                sendRequestToGetPlaceList(GlobalAppAccess.URL_ITEM_LIST, false);
            }

            @Override
            public void showUpArrow() {
                if(fabTopToTheList.getVisibility() == View.GONE)
                    fabTopToTheList.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideUpArrow() {

                if(fabTopToTheList.getVisibility() == View.VISIBLE)
                    fabTopToTheList.setVisibility(View.GONE);
            }
        };

        listScrollListener = new EndlessRecyclerViewScrollListener(listLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                // loadNextDataFromApi(page);
                Log.d("DEBUG",String.valueOf(page));
                if(nextPageToken != null && !nextPageToken.isEmpty())
                sendRequestToGetPlaceList(GlobalAppAccess.BASE_URL + nextPageToken, false);
            }

            @Override
            public void showUpArrow() {

            }

            @Override
            public void hideUpArrow() {

            }
        };

        recyclerViewAdapter = new RecyclerViewAdapter(movieList, getActivity());
        recyclerViewAdapter.setListStyle(RecyclerViewAdapter.LIST);
        // Adds the scroll listener to RecyclerView
        recyclerView.setLayoutManager(listLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addOnScrollListener(listScrollListener);
    }


    private void initRecyclerViewOnItemClickListener() {
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Recepie movie = movieList.get(position);
                        String movieResponse = gson.toJson(movie);
                       // Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        //intent.putExtra(AppConstant.KEY_EXTRA_MOVIE_JSON,movieResponse);
                       // startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }


    private String nextPageToken = "";
    public void sendRequestToGetPlaceList(String url,boolean need_to_show_progressbar) {

        // TODO Auto-generated method stub
        // final ProgressBar progressBar = (ProgressBar)dialog_add_tag.findViewById(R.id.dialog_progressbar);
        //progressBar.setVisibility(View.VISIBLE);
        if(need_to_show_progressbar)showProgressDialog("loading..", true, false);

        final StringRequest req = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();


                        // progressBar.setVisibility(View.GONE);
                        Log.d("DEBUG", response);

                        RecepieInfo movies = gson.fromJson(response, RecepieInfo.class);
                        if (movies.getStatus()) {
                            nextPageToken = movies.getNextPageToken();
                            movieList.addAll(movies.getRecepies());
                            recyclerViewAdapter.notifyDataSetChanged();
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String response = dummyResponse();
                RecepieInfo movies = gson.fromJson(response, RecepieInfo.class);
                if (movies.getStatus()) {
                    nextPageToken = movies.getNextPageToken();
                    movieList.addAll(movies.getRecepies());
                    recyclerViewAdapter.notifyDataSetChanged();
                }

                dismissProgressDialog();
                //progressBar.setVisibility(View.GONE);
                AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(),
                        "Error",
                        "Something went wrong!!",
                        false);

            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // TODO Auto-generated method stub
        MydApplication.getInstance().addToRequestQueue(req, TAG_REQUEST_LIST_PAGE);
    }


    public void toggleGridViewListView(){
        if(KEY_CURRENT_VIEW == KEY_GRID_VIEW){
            //recyclerView.setLayoutManager(gridLayoutManager);
           // recyclerView.addOnScrollListener(listScrollListener);
            //recyclerViewAdapter.setListStyle(RecyclerViewAdapter.LIST);
            //recyclerViewAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

        switch (paramMenuItem.getItemId()) {

            case R.id.action_toggle_gridview_listview:


                if(KEY_CURRENT_VIEW == KEY_GRID_VIEW){
                    inputChangeCallback.changeMenuIcon(R.drawable.ic_view_comfy_black_24dp);
                    //menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.history_converted));
                    KEY_CURRENT_VIEW = KEY_LIST_VIEW;
                    recyclerView.setLayoutManager(listLayoutManager);
                     recyclerView.addOnScrollListener(listScrollListener);
                    recyclerViewAdapter.setListStyle(RecyclerViewAdapter.LIST);
                    recyclerViewAdapter.notifyDataSetChanged();
                }else if(KEY_CURRENT_VIEW == KEY_LIST_VIEW){
                    inputChangeCallback.changeMenuIcon(R.drawable.ic_list_black_24dp);
                    KEY_CURRENT_VIEW = KEY_GRID_VIEW;
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.addOnScrollListener(gridScrollListener);
                    recyclerViewAdapter.setListStyle(RecyclerViewAdapter.GRID);
                    recyclerViewAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.action_search:
                // startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.ydoodle.mymoneymanager")));
                // Toast.makeText(MainActivity.this,"Please publish your app on play store first!",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_wishlist:
                   //startActivity(new Intent(getActivity(), WishListActivity.class));
                // Toast.makeText(MainActivity.this,"Please publish your app on play store first!",Toast.LENGTH_LONG).show();
                break;
        }

        return false;
    }
    public interface fragmentActivityCommunicator {
        /*To change something in activty*/
         void changeMenuIcon(int id);

    }

    fragmentActivityCommunicator inputChangeCallback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        try {
            inputChangeCallback = (fragmentActivityCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onFragmentChangeListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        MydApplication.getInstance().cancelPendingRequests(TAG_REQUEST_LIST_PAGE);
        super.onStop();
    }

    private ProgressDialog progressDialog;

    public void showProgressDialog(String message, boolean isIntermidiate, boolean isCancelable) {
       /**/
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog.setIndeterminate(isIntermidiate);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }







    private String dummyResponse(){
        return "{\n" +
                "  \"status\" : true,\n" +
                "  \"recepies\" : [\n" +
                "     {\n" +
                "\t   \"title\" : \"asd\",\n" +
                "\t   \"subTitle\" : \"asdsd\",\n" +
                "\t   \"description\" : \"asddas\",\n" +
                "\t   \"image\" : \"asdas\"\n" +
                "\t },\n" +
                "\t {\n" +
                "\t   \"title\" : \"asd\",\n" +
                "\t   \"subTitle\" : \"asdsd\",\n" +
                "\t   \"description\" : \"asddas\",\n" +
                "\t   \"image\" : \"asdas\"\n" +
                "\t },\n" +
                "\t {\n" +
                "\t   \"title\" : \"asd\",\n" +
                "\t   \"subTitle\" : \"asdsd\",\n" +
                "\t   \"description\" : \"asddas\",\n" +
                "\t   \"image\" : \"asdas\"\n" +
                "\t }\n" +
                "  ],\n" +
                "  \"nextPageToken\" : \"\"\n" +
                "}";
    }
}
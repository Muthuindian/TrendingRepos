package com.example.trendingrepos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.trendingrepos.Models.RepoModel;
import com.example.trendingrepos.Repository.RepoDao;
import com.example.trendingrepos.Repository.RepoRoomDataBase;
import com.example.trendingrepos.ViewModels.ReposViewModel;
import com.example.trendingrepos.Views.ReposListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ReposListAdapter adapter = null;
    RecyclerView recyclerView;
    SwipeRefreshLayout pullToRefresh;

    ReposViewModel reposViewModel;
    ProgressDialog progressDialog;

    List<RepoModel> repoModels = new ArrayList<>();

    MenuItem searchViewItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        reposViewModel = ViewModelProviders.of(MainActivity.this).get(ReposViewModel.class);
        initViews();
        setAdapter();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        getData();


        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh.setRefreshing(true);
                getData();
            }
        });

    }

    private void getData() {
        reposViewModel.getAllRepos().observe(this, new Observer<List<RepoModel>>() {
            @Override
            public void onChanged(@Nullable List<RepoModel> resultRepoModels) {
                if(repoModels.size() == 0) {
                    RepoRoomDataBase db = RepoRoomDataBase.getDatabase(getApplicationContext());
                    new getOfflineAsyncTask(db.repoDao()).execute();
                } else {
                    repoModels =  resultRepoModels;
                    adapter.setRepos(repoModels);
                    pullToRefresh.setRefreshing(false);
                    if(progressDialog!=null && progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }

    private class getOfflineAsyncTask extends AsyncTask<List<RepoModel>, Void, Void> {

        private RepoDao mAsyncTaskDao;

        getOfflineAsyncTask(RepoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            if(progressDialog!=null && !progressDialog.isShowing()){
                progressDialog.show();
            }
        }

        @Override
        protected Void doInBackground(final List<RepoModel>... params) {
            repoModels = mAsyncTaskDao.getOfflineRepos();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.setRepos(repoModels);
            pullToRefresh.setRefreshing(false);
            if(progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }

    private void initViews(){
        recyclerView = findViewById(R.id.repos_list);
    }

    private void setAdapter(){
        adapter = new ReposListAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        collapseSearchView();
        searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                if(query != null && !query.isEmpty())
                    adapter.getFilter().filter(query);
                else
                    adapter.setRepos(repoModels);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty())
                    adapter.getFilter().filter(newText);
                else
                    adapter.setRepos(repoModels);

                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.app_bar_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                return true;
            }
        });
        return true;
    }

    private void collapseSearchView() {
        if (searchViewItem != null) {

            searchViewItem.collapseActionView();
        }
    }
}
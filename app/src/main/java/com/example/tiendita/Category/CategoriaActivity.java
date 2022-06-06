package com.example.tiendita.Category;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendita.HelpersCategory.MyAdapter;
import com.example.tiendita.HelpersCategory.Utils;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitCategory.Categorias;
import com.example.tiendita.RetrofitCategory.ResponseModel;
import com.example.tiendita.RetrofitCategory.RestApi;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("unchecked")
public class CategoriaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private RecyclerView rv;
    private MyAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    public ArrayList<Categorias> allPagesCategorias = new ArrayList();
    private List<Categorias> currentPageCategorias;
    private Boolean isScrolling = false;
    private int currentCategorias, totalCategorias, scrolledOutCategorias;
    private ProgressBar mProgressBar;

    private void initializeViews() {
        mProgressBar = findViewById(R.id.mProgressBarLoadC);
        mProgressBar.setIndeterminate(true);
        Utils.showProgressBar(mProgressBar);
        rv = findViewById(R.id.mRecyclerViewC);
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(this, allPagesCategorias);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(layoutManager);
    }

    private void retrieveAndFillRecyclerView(final String action, String queryString,
                                             final String start, String limit) {
        mAdapter.searchString = queryString;
        RestApi api = Utils.getClient().create(RestApi.class);
        Call<ResponseModel> retrievedData;

        if (action.equalsIgnoreCase("GET_PAGINATED")) {
            retrievedData = api.search("GET_PAGINATED", queryString, start, limit);
            Utils.showProgressBar(mProgressBar);
        } else if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
            Utils.showProgressBar(mProgressBar);
            retrievedData = api.search("GET_PAGINATED_SEARCH", queryString, start, limit);
        } else {
            Utils.showProgressBar(mProgressBar);
            retrievedData = api.retrieve();
        }

        retrievedData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel>
                    response) {
                Log.d("RETROFIT", "CODE : " + response.body().getCode());
                Log.d("RETROFIT", "MESSAGE : " + response.body().getMessage());
                Log.d("RETROFIT", "RESPONSE : " + response.body().getResult());
                currentPageCategorias = response.body().getResult();

                if (currentPageCategorias != null && currentPageCategorias.size() > 0) {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesCategorias.clear();
                    }
                    for (int i = 0; i < currentPageCategorias.size(); i++) {
                        allPagesCategorias.add(currentPageCategorias.get(i));
                    }

                } else {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesCategorias.clear();
                    }
                }
                mAdapter.notifyDataSetChanged();
                Utils.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Utils.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR" + t.getMessage());
                Utils.showInfoDialog(CategoriaActivity.this, "ERROR", t.getMessage());
            }
        });
    }

    private void listenToRecyclerViewScroll() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView rv, int newState) {
                super.onScrollStateChanged(rv, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                currentCategorias = layoutManager.getChildCount();
                totalCategorias = layoutManager.getItemCount();
                scrolledOutCategorias = ((LinearLayoutManager) rv.getLayoutManager()).findFirstVisibleItemPosition();

                if (isScrolling && (currentCategorias + scrolledOutCategorias == totalCategorias)) {
                    isScrolling = false;

                    if (dy > 0) {
                        retrieveAndFillRecyclerView("GET_PAGINATED", mAdapter.searchString, String.valueOf(totalCategorias), "5");
                    } else {

                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.categorias_page_cat, menu);
        MenuItem searchItem = menu.findItem(R.id.action_searchC);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(true);
        searchView.setQueryHint("Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_newC:
                Utils.openActivity(this, CRUDActivity.class);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        retrieveAndFillRecyclerView("GET_PAGINATED_SEARCH", query, "0", "5");
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        initializeViews();
        this.listenToRecyclerViewScroll();
        setupRecyclerView();
        retrieveAndFillRecyclerView("GET_PAGINATED", "", "0", "5");
    }
}

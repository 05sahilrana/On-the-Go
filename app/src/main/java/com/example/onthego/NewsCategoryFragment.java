package com.example.onthego;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class NewsCategoryFragment extends Fragment {

    private static final String ARG_CATEGORY_INDEX = "category_index";
    private int categoryIndex;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsArticle> newsArticles;
    private LottieAnimationView loadingAnimationView;

    public NewsCategoryFragment() {
        // Required empty public constructor
    }

    public static NewsCategoryFragment newInstance(int categoryIndex) {
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_INDEX, categoryIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryIndex = getArguments().getInt(ARG_CATEGORY_INDEX);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_category, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        loadingAnimationView = view.findViewById(R.id.loadingAnimationView);

        // Initialize RecyclerView and set up the adapter with news articles for the category
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        newsArticles = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsArticles);
        recyclerView.setAdapter(newsAdapter);

        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Fetch the latest news articles when swipe-to-refresh is triggered
                fetchLatestNews();
            }
        });

        // Initial data load
        showLoadingAnimation();
        fetchLatestNews();

        return view;
    }

    private void showLoadingAnimation() {
        loadingAnimationView.setVisibility(View.VISIBLE);
        loadingAnimationView.playAnimation();
    }

    // Define API URLs for each category
    private String getApiUrlForCategory(int categoryIndex) {
        switch (categoryIndex) {
            case 0:
                return "https://news-api-vaqm.onrender.com/news";
            case 1:
                return "https://news-api-vaqm.onrender.com/news-india";
            case 2:
                return "https://news-api-vaqm.onrender.com/news-world";
            case 3:
                return "https://news-api-vaqm.onrender.com/news-science";
            case 4:
                return "https://news-api-vaqm.onrender.com/news-education";
            default:
                return "";
        }
    }

    // Make an API request and handle the response
    private void makeApiRequest(String apiUrl) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse the JSON response into a list of NewsArticle objects
                        List<NewsArticle> newArticles = parseNewsArticles(response);

                        // Clear the existing data and add the new articles
                        newsArticles.clear();
                        newsArticles.addAll(newArticles);

                        // Notify the adapter that the data has changed
                        newsAdapter.notifyDataSetChanged();

                        // Stop the swipe-to-refresh animation
                        swipeRefreshLayout.setRefreshing(false);

                        // Hide the loading animation
                        hideLoadingAnimation();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error (e.g., show an error message)
                        swipeRefreshLayout.setRefreshing(false);

                        // Hide the loading animation
                        hideLoadingAnimation();
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(requireContext()).add(jsonArrayRequest);
    }

    private void hideLoadingAnimation()
    {
        loadingAnimationView.setVisibility(View.GONE);
        loadingAnimationView.cancelAnimation ();
    }

    // Parse JSON response into a list of NewsArticle objects
    private List<NewsArticle> parseNewsArticles(JSONArray jsonArray) {
        List<NewsArticle> newsArticles = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                String link = jsonObject.getString("link");
                String imageLink = jsonObject.getString("img_link");

                newsArticles.add(new NewsArticle(title, description, link, imageLink));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsArticles;
    }

    // Method to fetch the latest news articles
    private void fetchLatestNews() {
        String apiUrl = getApiUrlForCategory(categoryIndex);
        makeApiRequest(apiUrl);
    }
}

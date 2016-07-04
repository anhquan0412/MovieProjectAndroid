package com.anhquan.movieproject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by anhqu on 6/27/2016.
 */
public class MovieFragment extends Fragment {

    private final String LOG_TAG = MovieFragment.class.getSimpleName();

    private ImageAdapter imageAdapter;

    private View rootView;
    public MovieFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_refresh)
        {
            updateMovie();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //execute asynctask
        updateMovie();

        return rootView;
    }

    private void setUpImageAdapter(String[] picUrls)
    {
        imageAdapter = new ImageAdapter(getActivity(), picUrls);

        GridView gridView = (GridView)rootView.findViewById(R.id.gridview);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateMovie()
    {
        FetchMovieTask task = new FetchMovieTask();
        task.execute("popular");
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        private final String baseURL = "http://api.themoviedb.org/3";

        private final String basePicURL = "http://image.tmdb.org/t/p";
        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0)
                return null;

            String sortType = params[0];
            String mediaType = "movie";

            String movieJSONString = null;
            HttpURLConnection urlConnection = null;
            Scanner scanner = null;



            //http://api.themoviedb.org/3/movie/popular?api_key=05c704e23132c50fbc4a3606ce907042
            Uri builtUri = Uri.parse(baseURL).buildUpon()
                    .appendEncodedPath(mediaType)
                    .appendEncodedPath(sortType)
                    .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY)
                    .build();


            try {


                //built URL
                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                scanner = new Scanner(inputStream);


                StringBuilder result = new StringBuilder();

                while (scanner.hasNextLine()) {
                    result.append(scanner.nextLine());
                    result.append("\n");
                }

                movieJSONString = result.toString();

                try {
                    return getPicURL(movieJSONString);
                } catch (JSONException e)
                {
                    Log.e(LOG_TAG,"JSON problem",e);
                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error reading from URL", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (scanner != null) {
                    scanner.close();
                }
            }


            return null;

        }

        @Override
        protected void onPostExecute(String[] s) {
            setUpImageAdapter(s);
        }


        private String[] getPicURL(String JSONString) throws JSONException
        {
            JSONObject jsonMovie = new JSONObject(JSONString);
            JSONArray jsonMovieArray = jsonMovie.getJSONArray("results");

            String picSize = "w185";

            String[] picUrls = new String[jsonMovieArray.length()];
            for(int i = 0; i< jsonMovieArray.length(); i++)
            {
                JSONObject temp = jsonMovieArray.getJSONObject(i);
                StringBuilder s = new StringBuilder();

                s.append(basePicURL);
                s.append("/" + picSize);
                s.append(temp.getString("poster_path"));

                picUrls[i] = s.toString();

            }

            return picUrls;
        }
    }


}

package com.anhquan.movieproject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public MovieFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Log.d(LOG_TAG,"here we go");
        FetchMovieTask task = new FetchMovieTask();
        task.execute("popular");
        return rootView;
    }


    public class FetchMovieTask extends AsyncTask<String, Void, String> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected String doInBackground(String... params) {
            if (params.length == 0)
                return null;

            String sortType = params[0];
            String mediaType = "movie";

            String movieJSONString = null;
            HttpURLConnection urlConnection = null;
            Scanner scanner =null;

            final String baseURL = "http://api.themoviedb.org/3";

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

                return movieJSONString;

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error reading from URL", e);
            } finally {
                if(urlConnection!=null)
                {
                    urlConnection.disconnect();
                }

                if(scanner!=null){
                    scanner.close();
                }
            }


            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(LOG_TAG,s);
        }
    }


}

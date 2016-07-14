package com.anhquan.movieproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by anhqu on 6/27/2016.
 */
public class MovieFragment extends Fragment {

    private final String LOG_TAG = MovieFragment.class.getSimpleName();

   //private ImageAdapter imageAdapter;

    private MovieAdapter movieAdapter;

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

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        List<Movie> movieList = new ArrayList<Movie>();
        movieAdapter  = new MovieAdapter(getActivity(),movieList);

        GridView gridView = (GridView)rootView.findViewById(R.id.gridview);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent detailIntent = new Intent(getActivity(),DetailActivity.class);
                Movie movie = (Movie)parent.getItemAtPosition(position);

                //can passing objects by using Parcelable
                //http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
                detailIntent.putExtra(Intent.EXTRA_TEXT,movie);
                startActivity(detailIntent);
//                String movieInString = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getActivity(),movieInString,Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }

//    private void setUpImageAdapter(String[] picUrls)
//    {
//
//        // TODO: 7/5/2016 create MovieAdapter here
//        //movieAdapter = new MovieAdapter(getActivity(), movies)
//        imageAdapter = new ImageAdapter(getActivity(), picUrls);
//
//        GridView gridView = (GridView)rootView.findViewById(R.id.gridview);
//        gridView.setAdapter(imageAdapter);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(getActivity(), "" + position,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    //Execute AsyncTask
    private void updateMovie()
    {
        FetchMovieTask task = new FetchMovieTask();

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        String sortBy = sharedPreference.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default));

        int year = sharedPreference.getInt(getString(R.string.pref_year_key), 2016);



        task.execute(sortBy, Integer.toString(year));
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        private final String baseURL = "http://api.themoviedb.org/3/discover";

        private final String basePicURL = "http://image.tmdb.org/t/p";
        @Override
        protected Movie[] doInBackground(String... params) {
            if (params.length == 0)
                return null;

            String mediaType = "movie";

            String movieJSONString = null;
            HttpURLConnection urlConnection = null;
            Scanner scanner = null;



            //http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&
            // sort_by=vote_count.desc&primary_release_year=2016&api_key=05c704e23132c50fbc4a3606ce907042


            Uri.Builder uriBuilder = Uri.parse(baseURL).buildUpon()
                    .appendEncodedPath(mediaType);


            String[] sortArray = params[0].split(" ");
            for(String s: sortArray)
            {
                uriBuilder.appendQueryParameter("sort_by",s);
            }

            uriBuilder.appendQueryParameter("primary_release_year",params[1])
            .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY);


            Uri builtUri = uriBuilder.build();

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
        protected void onPostExecute(Movie[] s) {
            //setUpImageAdapter(s);
            if(s!=null)
            {
                movieAdapter.clear();
                movieAdapter.addAll(s);
            }
        }


        private Movie[] getPicURL(String JSONString) throws JSONException
        {
            // TODO: 7/5/2016 return Movie array, not just url

            JSONObject jsonMovie = new JSONObject(JSONString);
            JSONArray jsonMovieArray = jsonMovie.getJSONArray("results");
            String picSize = "w185";

            Movie[] movies = new Movie[jsonMovieArray.length()];

            for(int i = 0; i< jsonMovieArray.length(); i++)
            {
                JSONObject temp = jsonMovieArray.getJSONObject(i);

                //get pic URL
                StringBuilder p = new StringBuilder();
                p.append(basePicURL);
                p.append("/" + picSize);
                p.append(temp.getString("poster_path"));

                //get date
                Date date = getDate(temp.getString("release_date"));
                if (date == null) throw new JSONException("Error reading date from JSON");

                //get title
                String t = temp.getString("original_title");
                //get vote
                String v = temp.getString("vote_average") + "/10";
                //get summary
                String s = temp.getString("overview");
                movies[i] = new Movie(t,date,v,p.toString(),s);
            }

            return movies;
        }


        private Date getDate(String d)
        {
            String[] sArray = d.split("-");
            if(sArray.length==0) return null;
            int year = Integer.parseInt(sArray[0]);
            int month = Integer.parseInt(sArray[1]);
            int day = Integer.parseInt(sArray[2]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month-1,day); //months are zero-based

            return calendar.getTime();


        }
    }


}

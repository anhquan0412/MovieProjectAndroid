package com.anhquan.movieproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by anhqu on 7/5/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private Context context;
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param movies A List of AndroidFlavor objects to display in a list
     */
    public MovieAdapter(Activity context, List<Movie> movies) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.

        super(context, 0, movies);
        this.context = context;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        Movie movie = getItem(position);

        ImageView imageView;
        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
//            imageView = new ImageView(context);
//            imageView.setAdjustViewBounds(true);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(0,0,0,0);

            //inflate the movie item layout
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item_layout,parent,false);

        }

//        else {
//            imageView = (ImageView) convertView;
//        }
        imageView = (ImageView) convertView.findViewById(R.id.movie_image);
        String url = movie.getPicUrl();

        Picasso.with(context).load(url).into(imageView);

        return convertView;
    }

}

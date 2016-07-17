package com.anhquan.movieproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            Movie movie = (Movie) intent.getParcelableExtra(Intent.EXTRA_TEXT);
            TextView title = (TextView)rootView.findViewById(R.id.title);
            TextView date = (TextView)rootView.findViewById(R.id.date);
            TextView vote = (TextView)rootView.findViewById(R.id.vote);
            TextView summary = (TextView)rootView.findViewById(R.id.summary);
            ImageView thumbnail = (ImageView)rootView.findViewById(R.id.imageView);

            title.setText(movie.getTitle());
            date.setText(movie.getDateString());
            vote.setText(movie.getVote());
            summary.setText(movie.getSummary());

            thumbnail.setAdjustViewBounds(true);
//            thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String url = movie.getPicUrl();

            Picasso.with(getActivity()).load(url).into(thumbnail);

        }
        return rootView;
    }
}

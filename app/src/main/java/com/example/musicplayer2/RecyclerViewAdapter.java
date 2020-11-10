package com.example.musicplayer2;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.markers.KMutableList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mSongs = new ArrayList<>();
    private ArrayList<String> mSongNames = new ArrayList<>();
    private ArrayList<String> mSongArtists = new ArrayList<>();
    private ArrayList<String> mGroupInfo = new ArrayList<>();
    private List<Music> mMusic = new ArrayList<>();
    private Context mContext;
    private String fontPath;

    public RecyclerViewAdapter(List<Music> data, Context mContext, String fontPath) {
        this.mMusic = data;
        this.mContext = mContext;
        this.fontPath = fontPath;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,
                parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mMusic.get(position).imageUrl)
                .into(holder.image);

        holder.song_name.setText(mMusic.get(position).songName);
        holder.song_artist.setText(mMusic.get(position).songArtist);

        if (fontPath != "") {
            AssetManager am = mContext.getApplicationContext().getAssets();
            Typeface font = Typeface.createFromAsset(am, fontPath);
            holder.song_name.setTypeface(font);
            holder.song_name.setTypeface(font);
        }



        //click
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on: " + mSongNames.get(position));

                //Toast.makeText(mContext, mSongNames.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, PlayerActivity.class);

                intent.putExtra("imageUrl", mMusic.get(position).imageUrl);
                intent.putExtra("songLocationName", mMusic.get(position).songLocationName);
                intent.putExtra("songName", mMusic.get(position).songName);
                intent.putExtra("songArtist", mMusic.get(position).songArtist);
                intent.putExtra("videoId", mMusic.get(position).videoId);
                mContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mMusic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView song_name;
        TextView song_artist;
        RelativeLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            song_name = itemView.findViewById(R.id.song_name);
            song_artist = itemView.findViewById(R.id.song_artist);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

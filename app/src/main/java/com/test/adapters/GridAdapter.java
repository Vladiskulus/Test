package com.test.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.test.R;
import com.test.model.MovieModelClass;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {
    private Context c;
    private List<MovieModelClass> data;


    public GridAdapter(Context c, List<MovieModelClass> data){
        this.c=c;
        this.data=data;
    }
    @NonNull
    @Override
    public GridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater =LayoutInflater.from(c);
        v = inflater.inflate(R.layout.tab, parent,false);
        return new GridAdapter.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.name.setText(data.get(i).getName());
        Glide.with(c).load("https://image.tmdb.org/t/p/w500/"+data.get(i).getImage()).into(holder.image);
        holder.cardGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView al_name, al_lang, al_release, al_stars, al_pop;
                HtmlTextView al_info;
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(c).inflate(R.layout.alert, null);
                ImageView al_im = view.findViewById(R.id.al_im);
                Glide.with(c).load("https://image.tmdb.org/t/p/w500/"+data.get(i).getImage()).into(al_im);
                al_name = view.findViewById(R.id.al_name);
                al_lang = view.findViewById(R.id.al_lang);
                al_release = view.findViewById(R.id.al_release);
                al_stars = view.findViewById(R.id.al_stars);
                al_pop = view.findViewById(R.id.al_pop);
                al_info = view.findViewById(R.id.al_info);

                al_name.setText(data.get(i).getName());
                al_lang.setText("Мова: "+data.get(i).getLang());
                al_release.setText("Дата виходу: "+data.get(i).getRelease());
                al_stars.setText("Рейтинг: "+data.get(i).getStars());
                al_pop.setText("Популярність: "+data.get(i).getPop());
                al_info.setHtml("Опис: "+data.get(i).getInfo());
                ad.setView(view);
                ad.setNegativeButton(view.getContext().getString(R.string.back), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                    }
                });
                final AlertDialog alertDialog=ad.create();
                alertDialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardGrid;
        TextView name;
        ImageView image;

        public MyViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.name);
            image = v.findViewById(R.id.im);
            cardGrid = v.findViewById(R.id.cardGrid);
        }
    }
}

package com.example.beourguest;
//Slider Adapter for Main Home Page Club Slider
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private int slide_logo[] = {
            R.drawable.ark_logo,
            R.drawable.mitron_logo,
            R.drawable.bombay_adda_logo
    };
    private String slide_name[] = {
            "Ark",
            "Mitron",
            "Bombay Adda"
    };
    private String slide_addr[] = {
            "Andheri-East",
            "Sakinaka",
            "Linking Road"
    };
    private String slide_rate[] = {
            "4.5/5",
            "4/5",
            "4/5"
    };
    SliderAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return slide_logo.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (ConstraintLayout)o;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView logo = (ImageView)view.findViewById(R.id.logo);
        logo.setImageResource(slide_logo[position]);
        TextView club_name = (TextView)view.findViewById(R.id.club_name);
        TextView club_addr = (TextView)view.findViewById(R.id.club_addr);
        TextView club_rate = (TextView)view.findViewById(R.id.club_rate);
        club_name.setText(slide_name[position]);
        club_addr.setText(slide_addr[position]);
        club_rate.setText(slide_rate[position]);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnImageClick(position);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

    private void OnImageClick(int position) {
        switch(position) {
            case 0 :
                context.startActivity(new Intent(context,Ark.class));
                ((Activity)context).overridePendingTransition(R.anim.goup,R.anim.godown);
                break;
            case 1 :
                context.startActivity(new Intent(context,Mitron.class));
                ((Activity)context).overridePendingTransition(R.anim.goup,R.anim.godown);
                break;
            case 2:
                context.startActivity(new Intent(context,BombayAdda.class));
                ((Activity)context).overridePendingTransition(R.anim.goup,R.anim.godown);
                break;
        }
    }
}

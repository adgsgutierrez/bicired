package co.com.konrad.bicired.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import co.com.konrad.bicired.R;
import java.util.ArrayList;
import co.com.konrad.bicired.logic.Fotografia;
import co.com.konrad.bicired.utils.Constants;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Fotografia> dataSet;
    private Context context;

    public CustomAdapter(ArrayList<Fotografia> data , Context context) {
        this.dataSet = data;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.person_name_1);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.person_photo_1);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;
        textViewVersion.setText(dataSet.get(listPosition).getUsuario() +" compartio el "+dataSet.get(listPosition).getFecha());
        try {
            String urlString = Constants.VIEW_FOTOS + dataSet.get(listPosition).getImagenes();
            Log.e(Constants.TAG_LOG , urlString);
            /*
            InputStream inputStream = response.body().byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            */
            //final GifTextView gifFromResource = (GifTextView)row.findViewById(R.id.load_como);
            Picasso.with(context)
                    .load(urlString)
                    .placeholder(R.drawable.no_data)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Log.d("ERROR!", "onSuccess: ");
                        }
                    });
        }catch (Exception e){
            Log.e(Constants.TAG_LOG , e.getMessage());


        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
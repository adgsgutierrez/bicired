package co.com.konrad.bicired.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;

import co.com.konrad.bicired.R;
import co.com.konrad.bicired.logic.NewDao;

public class MapAdapter extends ArrayAdapter<NewDao> {
    private final ArrayList<NewDao> mData;

    public MapAdapter(Context context, int textViewResourceId, List<NewDao> map) {
        super(context, textViewResourceId, map);
        mData = new ArrayList();
        for(NewDao newDao : map){
            mData.add(newDao);
        }
    }


    @Override
    public NewDao getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_map, parent, false);
        } else {
            result = convertView;
        }

        NewDao item = (NewDao) getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(R.id.descripcion)).setText(item.getPbl_descripcion());
        ((TextView) result.findViewById(R.id.fecha)).setText(item.getPbl_fecha().toString());
        //GoogleMap visor = (GoogleMap) result.findViewById(R.id.mapa);
        //visor.
        return result;
    }
}
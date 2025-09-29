package com.example.streetsearch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.streetsearch.R;
import com.example.streetsearch.model.NominatimResult;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VH> {

    public interface OnItemClickListener {
        void onItemClick(NominatimResult item);
    }

    private List<NominatimResult> items = new ArrayList<>();
    private OnItemClickListener listener;

    public SearchAdapter(OnItemClickListener l) { this.listener = l; }

    public void setItems(List<NominatimResult> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        NominatimResult item = items.get(position);
        holder.tvTitle.setText(item.getShortName());
        holder.tvSubtitle.setText(item.getDisplay_name());
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle;
        VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
        }
    }
}

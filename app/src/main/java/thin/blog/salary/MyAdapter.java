package thin.blog.salary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jmprathab on 23/10/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<Data> data = new ArrayList<>();


    public MyAdapter(ArrayList<Data> data) {
        this.data = data;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.date.setText(data.get(position).getDay());
        holder.inTime.setText(data.get(position).getInTime());
        holder.outTime.setText(data.get(position).getOutTime());
        holder.ot.setText(data.get(position).getPay());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date, inTime, outTime, ot;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            inTime = (TextView) itemView.findViewById(R.id.in);
            outTime = (TextView) itemView.findViewById(R.id.out);
            ot = (TextView) itemView.findViewById(R.id.ot);
        }


    }
}

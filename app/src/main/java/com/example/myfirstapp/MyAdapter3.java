package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.myfirstapp.MainActivity.EXTRA_MESSAGE;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {

    private Context context;
    private String [] seArray = new String[3];

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mView;
        public ViewHolder(View v) {
            super(v);
            mView = (TextView)v.findViewById(R.id.list_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter3(String[] stringArray) {
        seArray = stringArray;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlayout, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mView.setText(seArray[position]);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mView.getContext(), SideEffect.class);

                TextView text = (TextView) holder.mView;
                String message = text.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                holder.mView.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 3;
    }
}
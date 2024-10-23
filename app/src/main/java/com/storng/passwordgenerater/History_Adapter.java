package com.storng.passwordgenerater;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class History_Adapter extends RecyclerView.Adapter<History_Adapter.ViewHolader> {

    ArrayList<String> passwordlist;

    Context context;

    public History_Adapter(ArrayList<String> passwordlist, Context context) {
        this.passwordlist = passwordlist;
        this.context = context;
    }

    @NonNull
    @Override
    public History_Adapter.ViewHolader onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history,null,false);
        return new ViewHolader(view);
    }

    @Override
    public void onBindViewHolder(@NonNull History_Adapter.ViewHolader holder, int position) {
        try {
            holder.txt_Hpassword.setText(passwordlist.get(position));

            holder.Hcopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Copied Text", passwordlist.get(position));
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return passwordlist.size();
    }

    public class ViewHolader extends RecyclerView.ViewHolder {
        TextView txt_Hpassword;
        ImageView Hcopy;
        public ViewHolader(@NonNull View itemView) {
            super(itemView);

            Hcopy = itemView.findViewById(R.id.Hcopy);
            txt_Hpassword = itemView.findViewById(R.id.txt_Hpassword);
        }
    }
}

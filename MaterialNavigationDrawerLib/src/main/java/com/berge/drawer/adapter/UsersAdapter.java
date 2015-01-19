package com.berge.drawer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.berge.drawer.lisener.OnClickOtherAccountListener;
import com.berge.drawer.model.MaterialAccount;

import java.util.ArrayList;
import java.util.List;
import com.berge.drawer.R;

/**
 * Created by Adria on 18/01/2015.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {


    private List<MaterialAccount> accounts;
    private LayoutInflater inflater;
    private OnClickOtherAccountListener onClickOtherAccountListener;

    public UsersAdapter(){
        super();
        accounts = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int i) {

        if(inflater == null){
            inflater = LayoutInflater.from(parentViewGroup.getContext());
        }

        View rowView = inflater.inflate(R.layout.row_material_account, parentViewGroup, false);

        return new ViewHolder (rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        MaterialAccount rowData = accounts.get(position);
        viewHolder.imageView.setImageDrawable(rowData.getCircularPhoto());
        viewHolder.imageView.setTag(rowData);

    }


    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void removeAccount(MaterialAccount materialAccount) {
        int position = accounts.indexOf(materialAccount);
        accounts.remove(position);
        notifyItemRemoved(position);
    }

    public void addAccount(MaterialAccount materialAccount, int position) {
        if(position == -1){
            position = 0;
        }
        accounts.add(position, materialAccount);
        notifyItemInserted(position);
    }

    public void setOnClickOtherAccountListener(OnClickOtherAccountListener onClickOtherAccountListener) {
        this.onClickOtherAccountListener = onClickOtherAccountListener;
    }

    public List<MaterialAccount> getAllItems() {
        return accounts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_other_account);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onClickOtherAccountListener != null) {
                MaterialAccount materialAccount = (MaterialAccount) v.getTag();
                onClickOtherAccountListener.onClick(accounts.indexOf(materialAccount), materialAccount);
            }
        }
    }
}

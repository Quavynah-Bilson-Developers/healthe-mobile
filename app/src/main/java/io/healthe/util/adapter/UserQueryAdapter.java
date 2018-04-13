package io.healthe.util.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.healthe.R;
import io.healthe.model.UserQuery;
import io.healthe.util.adapter.UserQueryAdapter.UserQueryViewHolder;

/**
 * UserQueryAdapter
 */
public class UserQueryAdapter extends RecyclerView.Adapter<UserQueryViewHolder> {
	private final Context context;
	private final List<UserQuery> items;
	
	public UserQueryAdapter(@NonNull Context context, List<UserQuery> items) {
		this.context = context;
		this.items = items;
	}
	
	@Override
	public UserQueryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View v = LayoutInflater.from(context).inflate(R.layout.item_user_query, viewGroup, false);
		return new UserQueryViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(UserQueryViewHolder holder, int position) {
		//Bind view
		UserQuery item = items.get(position);
	}
	
	@Override
	public int getItemCount() {
		if (items == null) {
			return 0;
		}
		return items.size();
	}
	
	
	public static class UserQueryViewHolder extends RecyclerView.ViewHolder {
		public UserQueryViewHolder(View itemView) {
			super(itemView);
		}
	}
}
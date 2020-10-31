package com.example.a10sec.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a10sec.MainActivity;
import com.example.a10sec.R;
import com.example.a10sec.databinding.CardDesignLeaderboardItemBinding;
import com.example.a10sec.models.UserModel;

import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolderLeaderBoard> {
    private MainActivity activity;
    private List<UserModel> scoreList;
    private CardDesignLeaderboardItemBinding binding;

    public LeaderBoardAdapter (MainActivity activity, List<UserModel> scoreList){
        this.activity = activity;
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public ViewHolderLeaderBoard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.card_design_leaderboard_item, parent, false);
        return new ViewHolderLeaderBoard(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLeaderBoard holder, int position) {
        UserModel user=scoreList.get(position);
        holder.bind(user,position);
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public class ViewHolderLeaderBoard extends RecyclerView.ViewHolder {
        private CardDesignLeaderboardItemBinding rowBinding;
        public ViewHolderLeaderBoard(CardDesignLeaderboardItemBinding rowBinding) {
            super(binding.getRoot());
            this.rowBinding = rowBinding;
        }
        private void bind(final UserModel user, int position){
            rowBinding.txtUsername.setText(user.getUsername());
            rowBinding.txtScore.setText(String.valueOf(user.getScore()));
            Glide.with(activity)
                    .load(user.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .optionalCenterCrop()
                    .into(rowBinding.imgUserimg);
        }
    }
}
package com.example.a10sec.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a10sec.R;
import com.example.a10sec.adapters.LeaderBoardAdapter;
import com.example.a10sec.databinding.FragmScoreboardBinding;
import com.example.a10sec.models.SingeltonAppData;
import com.example.a10sec.models.UserModel;
import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ScoreBoardFragment extends BaseFragment {

    View view;
    private FragmScoreboardBinding scoreboardBinding;
    private LeaderBoardAdapter leaderBoardAdapter;
    ArrayList<UserModel> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        scoreboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_scoreboard,container,false);
        view = scoreboardBinding.getRoot();
        getUsers();
        click();
        return view;
    }

    void click() {
    }

    private void getUsers() {
        userList= new ArrayList<>();
        for(Map.Entry<String, UserModel> entry : SingeltonAppData.getInstance().getUsersMap().entrySet()) {
            if (entry.getKey()!=null){
                userList.add(entry.getValue());
            }
        }
        selectionsort(userList);
        leaderBoardAdapter = new LeaderBoardAdapter(activity,userList);
        scoreboardBinding.recyclerScorelist.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        Objects.requireNonNull(scoreboardBinding.recyclerScorelist.getLayoutManager()).smoothScrollToPosition(scoreboardBinding.recyclerScorelist,null, userList.size()-1);
        scoreboardBinding.recyclerScorelist.setHasFixedSize(true);
        scoreboardBinding.recyclerScorelist.setAdapter(leaderBoardAdapter);
    }

    public static ArrayList<UserModel> selectionsort(ArrayList<UserModel> arrayList) {
        UserModel model = new UserModel();
        int min;
        for(int i=0; i < arrayList.size()-1; i++)
        {
            min=i;
            for(int j=i; j < arrayList.size(); j++)
            {
                if (arrayList.get(j).getScore() < arrayList.get(min).getScore()){
                    min=j;
                }
            }
            model=arrayList.get(i);
            arrayList.set(i,arrayList.get(min));
            arrayList.set(min,model);
        }
        Collections.reverse(arrayList);
        return arrayList;
    }
}

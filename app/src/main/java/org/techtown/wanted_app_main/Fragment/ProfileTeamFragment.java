package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ProfileTeamFragment extends Fragment {

    private RecyclerView rvChat;
    private ChatAdapter chatAdapter;
    private ArrayList<Chat> chatItems;

    NavController navController;

    public ProfileTeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // footer 클릭이벤트
//        footerView.setOnClickListener(view1 -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("friendsCategory", "test");
//            navController.navigate(R.id.action_mainFragment_to_showFriendsFragment, bundle);
//        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

    }
}
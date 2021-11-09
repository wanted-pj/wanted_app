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

public class ChatListFragment extends Fragment {

    private RecyclerView rvChat;
    private ChatListAdapter chatListAdapter;
    private ArrayList<Chat> chatItems;

    NavController navController;

    public ChatListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        // 채팅목록
        chatListAdapter = new ChatListAdapter();

        rvChat = view.findViewById(R.id.recyclerView_chatlist);
        rvChat.setAdapter(chatListAdapter);
        rvChat.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        chatItems = new ArrayList<>();
        chatItems.add(new Chat("피넛", "저 같이 하고 싶습니다! 이러이런거 해봤고 이러이러이런거 할 줄 알아요!", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        chatItems.add(new Chat("시미즈", "팀원 아직 구하시나요?", getResources().getIdentifier( "@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        chatItems.add(new Chat("리안", "네 그럼 잘 부탁드립니다~", getResources().getIdentifier( "@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        chatItems.add(new Chat("가비", "거기 조용하고 괜찮아요", getResources().getIdentifier( "@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        chatItems.add(new Chat("리헤이", "모집 완료됐나요?", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        chatItems.add(new Chat("엠마", "저 같이 하고 싶습니다! 이러이런거 해봤고 이러이러이런거 할 줄 알아요!", getResources().getIdentifier( "@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        chatItems.add(new Chat("제인", "테스트", getResources().getIdentifier( "@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        chatItems.add(new Chat("다니엘", "테스트", getResources().getIdentifier( "@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        chatListAdapter.setChatList(chatItems);

        chatListAdapter.setOnItemClicklistener(new ChatListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                navController.navigate(R.id.action_chat_to_chat, bundle);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

    }
}
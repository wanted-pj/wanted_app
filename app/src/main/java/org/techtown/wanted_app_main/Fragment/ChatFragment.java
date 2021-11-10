package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.wanted_app_main.R;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private RecyclerView rvChat;
    private ChatAdapter chatAdapter;
    private ArrayList<Chat> chatItems;

    public final int TYPE_SENDER = 0;
    public final int TYPE_RECEIVER = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        hideBottomNavigation(true);

        chatAdapter = new ChatAdapter();

        rvChat = view.findViewById(R.id.recyclerView_chat);
        rvChat.setAdapter(chatAdapter);
        rvChat.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

        chatItems = new ArrayList<>();
        chatItems.add(new Chat("안녕하세요!", "12:35", TYPE_SENDER));
        chatItems.add(new Chat("아직 팀원 구하시나요?", "12:36", TYPE_SENDER));
        chatItems.add(new Chat("네네!", "12:40", TYPE_RECEIVER));
        chatItems.add(new Chat("저 안드로이드 개발 맡고 싶습니다", "12:40", TYPE_SENDER));
        chatItems.add(new Chat("앱 개발 경험 2회 있고 배포도 한 번 했었습니다! 두줄테스트 두줄테스트 두줄테스트 두줄테스트 두줄테스트", "12:41", TYPE_SENDER));
        chatItems.add(new Chat("배포된 앱 한 번 볼 수 있을까요?", "12:42", TYPE_RECEIVER));
        chatItems.add(new Chat("넵 플레이스토어에서 ㅇㅇㅇㅇ 검색하시면 나와요", "12:44", TYPE_SENDER));
        chatItems.add(new Chat("확인했습니다! 팀원들과 얘기해보았는데 저희랑 함께하시면 좋을 것 같아요", "13:02", TYPE_RECEIVER));
        chatItems.add(new Chat("신청 수락 누를게요 잘 부탁드립니다 :)", "13:02", TYPE_RECEIVER));
        chatAdapter.setChats(chatItems);

        rvChat.scrollToPosition(chatItems.size()-1);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideBottomNavigation(false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void hideBottomNavigation(Boolean bool) {
        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottomNavigation);
        if (bool == true)
            bottomNavigation.setVisibility(View.GONE);
        else
            bottomNavigation.setVisibility(View.VISIBLE);
    }
}
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.ServerRequest.GetPersonalsRequest;
import org.techtown.wanted_app_main.database.Dto.PersonalChatDto;
import org.techtown.wanted_app_main.database.Dto.PostingDtoInPersonal;
import org.techtown.wanted_app_main.database.Personal;
import org.techtown.wanted_app_main.database.Posting;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment {

    private RecyclerView rvChat;
    private ChatListAdapter chatListAdapter;
    private ArrayList<ChatList> chatListItems;

    private Personal me;

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

        me = MainActivity.me;

        // 채팅목록
        chatListAdapter = new ChatListAdapter();

        rvChat = view.findViewById(R.id.recyclerView_chatlist);
        rvChat.setAdapter(chatListAdapter);
        rvChat.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));

        String url = "http://13.125.214.178:8080/personal/chat/" + me.id;

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 한글깨짐 해결 코드
                String changeString = new String();
                try {
                    changeString = new String(response.getBytes("8859_1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<PersonalChatDto>>() {
                }.getType();

                ArrayList<PersonalChatDto> temp = gson.fromJson(changeString, listType);

                for (PersonalChatDto dto : temp) {
                    System.out.println(dto.toString());
                }

                for (int i = 0; i < temp.size(); i++) {
                    System.out.println(temp.get(i).participants);
                    for (int j = 0; j < temp.get(i).participants.size(); j++) {
                        System.out.println(temp.get(i).participants.get(j).nickname);
                    }
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
        queue.add(new GetPersonalsRequest(responseListener));

        chatListItems = new ArrayList<>();
        chatListItems.add(new ChatList("피넛", "저 같이 하고 싶습니다! 이러이런거 해봤고 이러이러이런거 할 줄 알아요!", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        chatListItems.add(new ChatList("시미즈", "팀원 아직 구하시나요?", getResources().getIdentifier("@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        chatListItems.add(new ChatList("리안", "네 그럼 잘 부탁드립니다~", getResources().getIdentifier("@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        chatListItems.add(new ChatList("가비", "거기 조용하고 괜찮아요", getResources().getIdentifier("@drawable/profile_basic1", "drawable", getContext().getPackageName())));
        chatListItems.add(new ChatList("리헤이", "모집 완료됐나요?", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        chatListItems.add(new ChatList("엠마", "저 같이 하고 싶습니다! 이러이런거 해봤고 이러이러이런거 할 줄 알아요!", getResources().getIdentifier("@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        chatListItems.add(new ChatList("제인", "테스트", getResources().getIdentifier("@drawable/profile_basic3", "drawable", getContext().getPackageName())));
        chatListItems.add(new ChatList("다니엘", "테스트", getResources().getIdentifier("@drawable/profile_basic2", "drawable", getContext().getPackageName())));
        chatListAdapter.setChatList(chatListItems);

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
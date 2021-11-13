package org.techtown.wanted_app_main.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.Adapter.ChatAdapter;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Chat;
import org.techtown.wanted_app_main.database.Dto.ParticipantInPersonalDto;
import org.techtown.wanted_app_main.database.Dto.PersonalChatDto;
import org.techtown.wanted_app_main.database.Message;
import org.techtown.wanted_app_main.database.Personal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatFragment extends Fragment {

    private RecyclerView rvChat;
    private ChatAdapter chatAdapter;
    private ArrayList<Chat> chatItems;

    public final int TYPE_SENDER = 0;  // 타인이 보낼 때
    public final int TYPE_RECEIVER = 1; // 내가 보낼 때

    private Personal me;
    private ParticipantInPersonalDto dto;
    private ArrayList<ParticipantInPersonalDto> participants;

    private TextView chat_sender;
    private EditText chat_edit;
    private Button chat_btn;
    private ImageView refresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        hideBottomNavigation(true);

        me = MainActivity.me;

        Bundle bundle = getArguments();
        if(bundle != null) {
            dto = bundle.getParcelable("participants");
        }

        chat_sender = view.findViewById(R.id.chat_sender);
        chat_edit = view.findViewById(R.id.chat_edit);
        chat_btn = view.findViewById(R.id.chat_btn);
        refresh = view.findViewById(R.id.refresh);

        chat_sender.setText(dto.nickname);

        chatAdapter = new ChatAdapter();

        rvChat = view.findViewById(R.id.recyclerView_chat);
        rvChat.setAdapter(chatAdapter);
        rvChat.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL,false));

//        chatItems = new ArrayList<>();
//        chatItems.add(new Chat("안녕하세요!", "12:35", TYPE_SENDER));
//        chatItems.add(new Chat("아직 팀원 구하시나요?", "12:36", TYPE_SENDER));
//        chatItems.add(new Chat("네네!", "12:40", TYPE_RECEIVER));
//        chatItems.add(new Chat("저 안드로이드 개발 맡고 싶습니다", "12:40", TYPE_SENDER));
//        chatItems.add(new Chat("앱 개발 경험 2회 있고 배포도 한 번 했었습니다! 두줄테스트 두줄테스트 두줄테스트 두줄테스트 두줄테스트", "12:41", TYPE_SENDER));
//        chatItems.add(new Chat("배포된 앱 한 번 볼 수 있을까요?", "12:42", TYPE_RECEIVER));
//        chatItems.add(new Chat("넵 플레이스토어에서 ㅇㅇㅇㅇ 검색하시면 나와요", "12:44", TYPE_SENDER));
//        chatItems.add(new Chat("확인했습니다! 팀원들과 얘기해보았는데 저희랑 함께하시면 좋을 것 같아요", "13:02", TYPE_RECEIVER));
//        chatItems.add(new Chat("신청 수락 누를게요 잘 부탁드립니다 :)", "13:02", TYPE_RECEIVER));
//        chatAdapter.setChats(chatItems);

        // 넘어온 데이터를 기준으로 채팅 기본 파일 구성
        chatItems = new ArrayList<>();

        for(int i=0; i<dto.room.messages.size(); i++) {
            if(dto.room.messages.get(i).senderId != me.id) {
                chatItems.add(new Chat(dto.room.messages.get(i).content, dto.room.messages.get(i).messagingTime.substring(11,16), TYPE_SENDER));
            } else if (dto.room.messages.get(i).senderId == me.id) {
                chatItems.add(new Chat(dto.room.messages.get(i).content, dto.room.messages.get(i).messagingTime.substring(11,16), TYPE_RECEIVER));
            }
        }

        chatAdapter.setChats(chatItems);

        // send
        chat_btn.setOnClickListener( v-> {
            String content = chat_edit.getText().toString();

            String url = "http://13.125.214.178:8080/message";

            Map map = new HashMap();
            map.put("roomId", dto.room.roomId);
            map.put("senderId", me.id);
            map.put("content", content);

            JSONObject params = new JSONObject(map);

            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject obj) {
                            try {
                                chat_edit.setText("");
                                chatItems.add(new Chat(obj.getString("content"), obj.getString("messagingTime").substring(11,16), TYPE_RECEIVER));
                                chatAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {


                @Override
                public String getBodyContentType() {
                    return "application/json; charset=UTF-8";
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
            queue.add(objectRequest);
        });

        // reloading
        refresh.setOnClickListener( v-> {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

            String url = "http://13.125.214.178:8080/personal/chat/" + me.id;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                    Type listType = new TypeToken<PersonalChatDto>() {
                    }.getType();

                    PersonalChatDto temp = gson.fromJson(changeString, listType);
                    participants = new ArrayList<>(temp.participants);

                    for(int i=0; i<participants.size(); i++) {
                        if(participants.get(i).room.roomId == dto.room.roomId) {
//                            chatItems = new ArrayList<>();
//
//                            for(int j=0; j<participants.get(i).room.messages.size(); j++) {
//                                if(participants.get(i).room.messages.get(j).senderId != me.id) {
//                                    chatItems.add(new Chat(participants.get(i).room.messages.get(j).content, participants.get(i).room.messages.get(j).messagingTime.substring(11,16), TYPE_SENDER));
//                                } else if (participants.get(i).room.messages.get(j).senderId == me.id) {
//                                    chatItems.add(new Chat(participants.get(i).room.messages.get(j).content, participants.get(i).room.messages.get(j)..messagingTime.substring(11,16), TYPE_RECEIVER));
//                                }
//                            }
//
//                            chatAdapter.setChats(chatItems);

                            // 추가된 메세지
                            if(participants.get(i).room.messages.size() - dto.room.messages.size() > 0) {
                                for(int j=dto.room.messages.size(); j<participants.get(i).room.messages.size(); j++) {
//                                    System.out.println(participants.get(i).room.messages.size() + "::" + j);
                                    if(participants.get(i).room.messages.get(j).senderId != me.id) {
                                        chatItems.add(new Chat(participants.get(i).room.messages.get(j).content, participants.get(i).room.messages.get(j).messagingTime.substring(11,16), TYPE_SENDER));
                                        dto.room.messages.add(new Message(participants.get(i).room.messages.get(j).messageId, participants.get(i).room.messages.get(j).senderId, participants.get(i).room.messages.get(j).content,
                                                participants.get(i).room.messages.get(j).readCheck, participants.get(i).room.messages.get(j).messagingTime));
                                    } else if (participants.get(i).room.messages.get(j).senderId == me.id) {
                                        chatItems.add(new Chat(participants.get(i).room.messages.get(j).content, participants.get(i).room.messages.get(j).messagingTime.substring(11,16), TYPE_RECEIVER));
                                        dto.room.messages.add(new Message(participants.get(i).room.messages.get(j).messageId, participants.get(i).room.messages.get(j).senderId, participants.get(i).room.messages.get(j).content,
                                                participants.get(i).room.messages.get(j).readCheck, participants.get(i).room.messages.get(j).messagingTime));
                                    }
                                }
                                chatAdapter.notifyDataSetChanged();
                            }
                            break;
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(stringRequest);
        });

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
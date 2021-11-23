package org.techtown.wanted_app_main.Adapter;
//

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.techtown.wanted_app_main.Activity.MainActivity;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.ServerRequest.PutConnectResultRequest;
import org.techtown.wanted_app_main.ServerRequest.PutJoinTeamRequest;
import org.techtown.wanted_app_main.database.Connect;

import java.util.ArrayList;

public class PostingDetailAdapter extends RecyclerView.Adapter<PostingDetailAdapter.ViewHolder> {
    static boolean itsMe; // 게시글 주인과 지금 로그인한 사람이 일치하는지
    static Long postingId;
    static Long loginMe;

    static ArrayList<Connect> items = new ArrayList<>();
    static PostingDetailAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClicklistener(PostingDetailAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public void onItemClick(View view, int position) {
        if(listener != null)
            listener.onItemClick(view, position);
    }

    public PostingDetailAdapter(boolean itsMe, Long postingId, Long loginMe) {
        this.itsMe = itsMe;
        this.postingId = postingId;
        this.loginMe = loginMe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_board_detail, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Connect connect = items.get(position);
        // 수락 버튼 눌렀을 때
        viewHolder.accept_button.setOnClickListener(v -> {
            // 서버 호출
            System.out.println("클릭됨1");
            RequestQueue queue = Volley.newRequestQueue(v.getContext());

            // 팀 조인
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // 팀 조인 성공
                    System.out.println("팀 조인 성공");
                    // 수락됨
                    connect.result = true;
                    viewHolder.accept_button.setText("수락되었습니다.");
                    viewHolder.accept_button.setEnabled(false);

                }
            };
            queue.add(new PutJoinTeamRequest(connect.id, responseListener));

            System.out.println("팀 조인 후");
            Response.Listener<String> connectResponseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // 커넥터 데이터 변경
                    System.out.println("커넥터 처리 완료");
                }
            };
            queue.add(new PutConnectResultRequest(connect.id, connectResponseListener));

        });
        viewHolder.setItem(connect);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItem() {
        items.clear();
    }

    public void addItem(Connect item) {
        items.add(item);
    }

    public void setItems(ArrayList<Connect> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public Connect getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Connect item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_writer;
        ImageView iv;
        Button accept_button;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_writer = itemView.findViewById(R.id.board_detail_request_writer);
            iv = itemView.findViewById(R.id.board_detail_request_image);
            accept_button = itemView.findViewById(R.id.board_detail_request_btn);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null)
                        listener.onItemClick(v, position);
                }
            };

            tv_writer.setOnClickListener(onClickListener);
            iv.setOnClickListener(onClickListener);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null)
//                        listener.onItemClick(v, position);
//                }
//            });
        }

        public void setItem(Connect connect) {
            // 글주인이 아닌경우, 수락 버튼이 없다.
            if (!itsMe && connect.senderId != loginMe) {
                accept_button.setVisibility(View.INVISIBLE);
                accept_button.setEnabled(false);
            } else if ((!itsMe && connect.senderId == loginMe && connect.result)) {  // 처리된 내 connect는 볼 수 있다.
                accept_button.setText("수락되었습니다.");
                accept_button.setEnabled(false);
            } else if ((!itsMe && connect.senderId == loginMe && !connect.result)) {
                accept_button.setVisibility(View.INVISIBLE);
                accept_button.setEnabled(false);
            } else if (itsMe && connect.result) {
                accept_button.setText("수락되었습니다.");
                accept_button.setEnabled(false);
            } else if (itsMe && !(connect.result)) {
                accept_button.setText("수락");
                accept_button.setVisibility(View.VISIBLE);
            }
            tv_writer.setText(connect.nickname);
            int image = MainActivity.mainActivity.getResources().getIdentifier(connect.img, "drawable", MainActivity.mainActivity.getPackageName());
            iv.setImageResource(image);
        }

    }
}
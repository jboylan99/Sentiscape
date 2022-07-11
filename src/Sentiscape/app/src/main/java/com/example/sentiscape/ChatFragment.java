package com.example.sentiscape;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    LinearLayoutManager linearLayoutManager;
    FirebaseAuth firebaseAuth;

    ImageView imageViewUser;
    ListView searchList;

    FirestoreRecyclerAdapter<FirebaseModel, NoteViewHolder> chatAdapter;

    RecyclerView recyclerView;
    RetrieveMessages retrieveMessages = new RetrieveMessages();

    ArrayAdapter<String> arrayAdapter;
    private SearchAdapter searchAdapter;
    ArrayList<FirebaseModel> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        setHasOptionsMenu(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);

        Query query = firebaseFirestore.collection("Users").whereNotEqualTo("uid", firebaseAuth.getUid());
        FirestoreRecyclerOptions<FirebaseModel> allUsernames = new FirestoreRecyclerOptions.Builder<FirebaseModel>().setQuery(query, FirebaseModel.class).build();

        arrayList = new ArrayList<FirebaseModel>();
        ArrayList arrayList2 = new ArrayList<FirebaseModel>();
        firebaseFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        arrayList.add((String) document.get("name"));
                        arrayList.add(new FirebaseModel((String) document.get("name"), (String) document.get("image"), (String) document.get("uid"), (String) document.get("status")));
                    }
                } else {
                    Log.i("USERS", "Error getting documents: ", task.getException());
                }
//                SearchBar(arrayList, searchList);
                searchAdapter = new SearchAdapter(arrayList, getActivity().getApplicationContext());

                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                recyclerView.setHasFixedSize(true);

                recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//                recyclerView.setAdapter(searchAdapter);
                recyclerView.setAdapter(chatAdapter);
            }
        });

        firebaseFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                    }
                }
            }
        });

        chatAdapter = new FirestoreRecyclerAdapter<FirebaseModel, NoteViewHolder>(allUsernames) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull FirebaseModel model) {

                holder.particularUser.setText(model.getName());
                if (model.getStatus().equals("Online")) {
                    holder.userStatus.setText(model.getStatus());
                    holder.userStatus.setTextColor(Color.GREEN);
                } else {
                    holder.userStatus.setText(model.getStatus());
                    holder.userStatus.setTextColor(Color.RED);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SpecificChatActivity.class);
                        intent.putExtra("name", model.getName());
                        intent.putExtra("receiverUid", model.getUid());
                        startActivity(intent);
                    }
                });
            }



            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view_layout, parent, false);
                return new NoteViewHolder(v);
            }
        };

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatAdapter);

        return view;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView particularUser;
        TextView userStatus;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            particularUser = itemView.findViewById(R.id.nameOfUser);
            userStatus = itemView.findViewById(R.id.statusOfUser);

        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.search_bar, menu);

        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void filter(String text) {
        ArrayList<FirebaseModel> filterList = new ArrayList<>();

        for (FirebaseModel item : arrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "No users found.", Toast.LENGTH_SHORT).show();
        } else {
            searchAdapter.filterList(filterList);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (chatAdapter != null) {
            chatAdapter.stopListening();
        }

    }
}
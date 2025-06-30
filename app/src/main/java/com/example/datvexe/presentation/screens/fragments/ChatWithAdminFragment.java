package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datvexe.databinding.FragmentChatWithAdminBinding;
import com.example.datvexe.presentation.adapter.ChatMessageAdapter;
import com.example.datvexe.presentation.viewmodel.ChatViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatWithAdminFragment extends Fragment {
    private FragmentChatWithAdminBinding binding;
    private ChatViewModel viewModel;
    private ChatMessageAdapter messageAdapter;

    public ChatWithAdminFragment() {
        // Required empty public constructor
    }

    public static ChatWithAdminFragment newInstance() {
        return new ChatWithAdminFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatWithAdminBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        setupClickListeners();
        observeViewModel();

        // Kết nối chat khi fragment được tạo
        viewModel.connectToChat();
    }

    private void setupRecyclerView() {
        messageAdapter = new ChatMessageAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true); // Scroll to bottom when new messages arrive

        binding.rvMessages.setLayoutManager(layoutManager);
        binding.rvMessages.setAdapter(messageAdapter);
    }

    private void setupClickListeners() {
        binding.btnSend.setOnClickListener(v -> sendMessage());

        // Send message when Enter is pressed
        binding.etMessage.setOnEditorActionListener((v, actionId, event) -> {
            sendMessage();
            return true;
        });
    }

    private void sendMessage() {
        String messageText = binding.etMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            viewModel.sendMessage(messageText);
            binding.etMessage.setText("");
        }
    }

    private void observeViewModel() {
        // Observe messages
        viewModel.messages.observe(getViewLifecycleOwner(), messages -> {
            if (messages != null && !messages.isEmpty()) {
                Log.d("ChatFragment", "Received messages: " + messages);
                messageAdapter.setMessages(messages);
                binding.llEmptyState.setVisibility(View.GONE);
                binding.rvMessages.setVisibility(View.VISIBLE);

                // Scroll to bottom
                binding.rvMessages.scrollToPosition(messages.size() - 1);
            } else {
                binding.llEmptyState.setVisibility(View.VISIBLE);
                binding.rvMessages.setVisibility(View.GONE);
            }
        });

//        // Observe connection status
//        viewModel.isConnected.observe(getViewLifecycleOwner(), isConnected -> {
//            if (isConnected) {
//                binding.tvConnectionStatus.setText("Đã kết nối");
//                binding.tvConnectionStatus.setBackgroundResource(com.example.datvexe.R.drawable.bg_status_connected);
//            } else {
//                binding.tvConnectionStatus.setText("Mất kết nối");
//                binding.tvConnectionStatus.setBackgroundResource(com.example.datvexe.R.drawable.bg_status_connecting);
//            }
//        });

        // Observe loading state
        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            binding.pbLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        // Observe errors
        viewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
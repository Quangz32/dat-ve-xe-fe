package com.example.datvexe.di;

import com.example.datvexe.data.repository.ChatRepositoryImpl;
import com.example.datvexe.domain.repository.ChatRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ChatModule {

    @Binds
    @Singleton
    public abstract ChatRepository bindChatRepository(ChatRepositoryImpl chatRepositoryImpl);
}
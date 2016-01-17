package com.finalandroidproject.quickmovie.Interfaces;

import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.User;

import java.util.List;

public interface IFriendActions {
    List<Friend> getFriendsByUser(User user);
    boolean addFriendtoUser(User user,Friend newFriend);
    boolean addFriendstoUser(User user,List<Friend> newFriends);
}

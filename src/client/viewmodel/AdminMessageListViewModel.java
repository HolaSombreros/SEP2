package client.viewmodel;

import client.model.MessageModel;
import server.model.domain.user.User;

public class AdminMessageListViewModel implements AdminMessageListViewModelInterface {
    private MessageModel model;
    private ViewState<User> viewState;
    
    public AdminMessageListViewModel(MessageModel model, ViewState<User> viewState) {
        this.model = model;
        this.viewState = viewState;
    }
    
    
}

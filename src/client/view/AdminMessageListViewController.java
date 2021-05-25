package client.view;

import client.viewmodel.AdminMessageListViewModelInterface;

public class AdminMessageListViewController extends ViewController {
    private AdminMessageListViewModelInterface viewModel;
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getAdminMessageListViewModel();
    }
    
    @Override
    public void reset() {
    
    }
}

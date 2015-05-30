package net.droidlabs.mvvmdemo.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import net.droidlabs.mvvmdemo.R;
import net.droidlabs.mvvmdemo.adapter.binder.CompositeItemBinder;
import net.droidlabs.mvvmdemo.adapter.binder.ItemBinder;
import net.droidlabs.mvvmdemo.adapter.binder.SuperUserBinder;
import net.droidlabs.mvvmdemo.adapter.binder.UserBinder;
import net.droidlabs.mvvmdemo.databinding.UsersViewBinding;
import net.droidlabs.mvvmdemo.model.User;
import net.droidlabs.mvvmdemo.viewmodel.SuperUserViewModel;
import net.droidlabs.mvvmdemo.viewmodel.UserViewModel;
import net.droidlabs.mvvmdemo.viewmodel.UsersViewModel;

public class UsersView extends AppCompatActivity
{
    private UsersViewModel usersViewModel;
    private UsersViewBinding binding;

    @Nullable
    private static String getStringFromEditText(EditText editText)
    {
        Editable editable = editText.getText();
        return editable == null ? null : editable.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        usersViewModel = new UsersViewModel();
        usersViewModel.users.add(new SuperUserViewModel(new User("Android", "Dev")));

        binding = DataBindingUtil.setContentView(this, R.layout.users_view);
        binding.setUsersViewModel(usersViewModel);
        binding.setView(this);
        binding.activityUsersRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public View.OnClickListener onButtonClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                usersViewModel.addUser(getStringFromEditText(binding.usersViewFirstname), getStringFromEditText(binding.usersViewLastname));
            }
        };
    }

    public ItemBinder<UserViewModel> getItemViewBinder()
    {
        return new CompositeItemBinder<UserViewModel>(
                new SuperUserBinder(com.android.databinding.library.baseAdapters.BR.user, R.layout.item_super_user),
                new UserBinder(com.android.databinding.library.baseAdapters.BR.user, R.layout.item_user)
        );
    }
}

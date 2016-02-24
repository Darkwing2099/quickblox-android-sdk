package com.quickblox.sample.user.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.sample.core.utils.Toaster;
import com.quickblox.sample.user.R;
import com.quickblox.sample.user.helper.DataHolder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignInActivity extends BaseActivity {

    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_sign_in);
        initUI();
    }

    private void initUI() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        loginEditText = (EditText) findViewById(R.id.login_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
    }

    public void signIn() {

        progressDialog.show();

        QBUser qbUser = new QBUser(loginEditText.getText().toString(), passwordEditText.getText().toString());
        QBUsers.signIn(qbUser, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                progressDialog.hide();

                setResult(RESULT_OK);

                DataHolder.getDataHolder().setSignInQbUser(qbUser);
                // password does not come, so if you want use it somewhere else, try something like this:
                DataHolder.getDataHolder().setSignInUserPassword(passwordEditText.getText().toString());
                Toaster.longToast(R.string.user_successfully_sign_in);

                finish();
            }

            @Override
            public void onError(QBResponseException errors) {
                progressDialog.hide();
                Toaster.longToast(errors.getErrors().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_sign_in_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_in_up:
                signIn();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
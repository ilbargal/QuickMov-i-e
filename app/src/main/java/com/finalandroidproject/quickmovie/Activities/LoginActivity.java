package com.finalandroidproject.quickmovie.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.finalandroidproject.quickmovie.DAL.FriendDAL;
import com.finalandroidproject.quickmovie.DAL.InvitationDAL;
import com.finalandroidproject.quickmovie.DAL.MovieDAL;
import com.finalandroidproject.quickmovie.DAL.UserDAL;
import com.finalandroidproject.quickmovie.MainActivity;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;
import com.finalandroidproject.quickmovie.Model.User;
import com.finalandroidproject.quickmovie.R;
import com.finalandroidproject.quickmovie.UsefulClasses.IntentHelper;
import com.parse.Parse;
import com.parse.ParseException;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private ScrollView mLoginFormView;
    private Button mSignInButton;

    // Consts
    private final int MIN_PASSWORD_LENGTH = 4;
    private final String SHORT_PASSWORD_MESSAGE = "סיסמה קצרה מדי";
    private final String WRONG_PASSWORD = "סיסמה שגויה";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    tryLogin();
                    return true;
                }
                return false;
            }
        });


        mSignInButton = (Button) findViewById(R.id.sign_in_button);

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tryLogin();
            }
        });

        mLoginFormView = (ScrollView)findViewById(R.id.login_form_scroll);

        mProgressView = findViewById(R.id.login_progress);

        TextView vButton_open_Registr = (TextView)findViewById(R.id.button_open_Registr);
        vButton_open_Registr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findViewById(R.id.base_login_form).getVisibility() == View.GONE){
                    findViewById(R.id.base_login_form).setVisibility(View.VISIBLE);
                    findViewById(R.id.base_Registration_form).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.base_login_form).setVisibility(View.GONE);
                    findViewById(R.id.base_Registration_form).setVisibility(View.VISIBLE);
                }


            }
        });
        final Activity me = this;
        findViewById(R.id.button_Registr).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText UserName = ((EditText)findViewById(R.id.username_Registr));
                EditText Password = ((EditText)findViewById(R.id.password_Registr));
                EditText Name = ((EditText)findViewById(R.id.Name_Registr));

                if(UserName.getText().toString() == "" ||
                        Password.getText().toString() == "" ||
                        Name.getText().toString() == "") {

                    AlertDialog.Builder alertDialgBuilder = new AlertDialog.Builder(me);
                    alertDialgBuilder.setNegativeButton("אישור", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialgBuilder.setTitle("הוספת משתמש");
                    alertDialgBuilder.setMessage("אין להשאיר שדות ריקים");
                    alertDialgBuilder.create().show();

                } else {
                    User newUser = new User();
                    newUser.setName(Name.getText().toString());
                    newUser.setPhone(UserName.getText().toString());
                    newUser.setPassword(Password.getText().toString());

                    if(UserDAL.instance.CheckIfUserExsist(newUser) != "") {
                        AlertDialog.Builder alertDialgBuilder = new AlertDialog.Builder(me);
                        alertDialgBuilder.setNegativeButton("אישור", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialgBuilder.setTitle("הוספת משתמש");
                        alertDialgBuilder.setMessage("משתמש כבר קיים");
                        alertDialgBuilder.create().show();
                    }
                    else {

                        UserDAL.instance.registerUser(newUser);
                        findViewById(R.id.base_Registration_form).setVisibility(View.INVISIBLE);
                        findViewById(R.id.base_login_form).setVisibility(View.VISIBLE);
                    }
                }


            }
        });
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    private void tryLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(SHORT_PASSWORD_MESSAGE);
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid data
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute(username, password);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > MIN_PASSWORD_LENGTH;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUsernameView.setEnabled(show ? false : true);
            mPasswordView.setEnabled(show ? false : true);
            mSignInButton.setEnabled(show ? false : true);


            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUsernameView.setEnabled(show ? false : true);
            mPasswordView.setEnabled(show ? false : true);
            mSignInButton.setEnabled(show ? false : true);
        }
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public class UserLoginTask extends AsyncTask<String, Void, User> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected User doInBackground(String... params) {
            try {
                // Login
                return UserDAL.instance.loginUser(params[0],params[1]);
            } catch (ParseException e) {

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(User user) {
            mAuthTask = null;

            // User successfully login
            if (user != null) {
                // Go to main activity
                IntentHelper.addObjectForKey("currentUser", user);
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();

            } else {
                showProgress(false);
                mPasswordView.setError(WRONG_PASSWORD);
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}


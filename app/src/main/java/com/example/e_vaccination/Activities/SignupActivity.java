package com.example.e_vaccination.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.Utils.AppUtils;
import com.example.e_vaccination.models.User;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class SignupActivity extends BaseActivity {
    private final String[] lahoreUC = {"Lahore cantt", "Nishter", "Kana"};
    private final String[] qasoorUC = {"PakPatan", "Pattoki", "Changa Manga"};
    private final String[] narowallUC = {"Passroor", "narowall", "Patomari"};
    private final String[] districts = {"Lahore", "Qasoor", "Narowall"};

    private EditText name, phoneNo, email, password;
    private String userType = "";
    private MaterialSpinner mDistrictSpinner, mUCSpinner;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);


        name = findViewById(R.id.firstName);
        phoneNo = findViewById(R.id.phoneNo);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mDistrictSpinner = findViewById(R.id.districtSpinner);
        mUCSpinner = findViewById(R.id.ucSpinner);
        address = findViewById(R.id.userAddress);

        initSpinners();


        findViewById(R.id.alreadyHaveAccount).setOnClickListener(v -> {
            newActivity(LoginActivity.class);
            finish();
        });

        mDistrictSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            if (districts[position].toLowerCase().equals("lahore"))
                mUCSpinner.setItems(lahoreUC);
            else if (districts[position].toLowerCase().equals("qasoor"))
                mUCSpinner.setItems(qasoorUC);
            else if (districts[position].toLowerCase().equals("narowall"))
                mUCSpinner.setItems(narowallUC);
        });

        findViewById(R.id.signUp).setOnClickListener(v -> {
            if (name.getText().toString().isEmpty())
                name.setError("Name Required");
            else if (email.getText().toString().isEmpty())
                email.setError("Email Required");
            else if (phoneNo.getText().toString().isEmpty() || phoneNo.getText().toString().length() < 11 || phoneNo.getText().toString().length() > 11)
                phoneNo.setError("Enter valid phone number");
            else if (address.getText().toString().isEmpty())
                address.setError("Address required");
            else if (password.getText().toString().isEmpty())
                password.setError("Enter a Password");

            else {
                AppUtils.startProgressBar(this, "Please wait ....");

                getAuth().createUserWithEmailAndPassword(getText(email),
                        getText(password)).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (task.isSuccessful()) {
                            addUserDetails(task.getResult().getUser().getUid());
                        } else {
                            if (AppUtils.isProgressBarShowing) AppUtils.dismissProgressBar();
                            Toast.makeText(SignupActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        });
    }

    private void initSpinners() {
        mDistrictSpinner.setItems(districts);
        mUCSpinner.setItems(lahoreUC);
    }


    public void addUserDetails(String uid) {
        String selectedUC = "lahore";
        if (districts[mDistrictSpinner.getSelectedIndex()].toLowerCase().equals("lahore"))
            selectedUC = lahoreUC[mUCSpinner.getSelectedIndex()];
        else if (districts[mDistrictSpinner.getSelectedIndex()].toLowerCase().equals("qasoor"))
            selectedUC = qasoorUC[mUCSpinner.getSelectedIndex()];
        else
            selectedUC = narowallUC[mUCSpinner.getSelectedIndex()];


        User user = new User(
                getText(name),
                getText(email),
                getText(address),
                getText(phoneNo),
                districts[mDistrictSpinner.getSelectedIndex()],
                selectedUC,
                uid,
                "patient"

        );

        getReference(AppConstants.USERS).child(uid).setValue(user).
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (AppUtils.isProgressBarShowing) AppUtils.dismissProgressBar();
                        Toast.makeText(SignupActivity.this, "Record added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    }
                });


    }


}
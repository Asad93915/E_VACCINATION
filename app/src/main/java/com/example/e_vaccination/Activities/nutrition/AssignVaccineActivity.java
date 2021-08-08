package com.example.e_vaccination;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.models.Vaccine;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AssignVaccineActivity extends AppCompatActivity {
    private final String[] lahoreUC = {"Lahore cantt", "Nishter", "Kana"};
    private final String[] qasoorUC = {"PakPatan", "Pattoki", "Changa Manga"};
    private final String[] narowallUC = {"Passroor", "narowall", "Patomari"};
    private final String[] districts = {"Lahore", "Qasoor", "Narowall"};

    private MaterialSpinner mDistrictSpinner, mUCSpinner, mVaccinesSpinner;

    private final List<String> vaccines = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_vaccine);

        mDistrictSpinner = findViewById(R.id.districtSpinner);
        mUCSpinner = findViewById(R.id.ucSpinner);
        mVaccinesSpinner = findViewById(R.id.vaccinesSpinner);
        TextView mDate = findViewById(R.id.vaccineDate);
        EditText mDescription = findViewById(R.id.descriptionNotes);

        initSpinners();

        addVaccines();

        mDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(this,
                    android.R.style.Theme_DeviceDefault_Dialog,
                    (view, year1, month1, dayOfMonth) -> {
                        mDate.setText(year1 + "/" + month1 + 1 + "/" + dayOfMonth);
                    },
                    year, month, day);
            dialog.show();
        });


        mDistrictSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            if (districts[position].toLowerCase().equals("lahore"))
                mUCSpinner.setItems(lahoreUC);
            else if (districts[position].toLowerCase().equals("qasoor"))
                mUCSpinner.setItems(qasoorUC);
            else if (districts[position].toLowerCase().equals("narowall"))
                mUCSpinner.setItems(narowallUC);
        });

        findViewById(R.id.actionAssignVaccine).setOnClickListener(v -> {
            String date = mDate.getText().toString();
            String description = mDescription.getText().toString();

            if (date.isEmpty() || description.isEmpty()) {
                Toast.makeText(AssignVaccineActivity.this, "date and description required..", Toast.LENGTH_SHORT).show();
                return;
            }

            String key = getIntent().getStringExtra("key");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", date);
            hashMap.put("uid", key);
            hashMap.put("status", false);
            hashMap.put("description", description);
            hashMap.put("district", districts[mDistrictSpinner.getSelectedIndex()]);
            hashMap.put("uc", getSelectedUC());

            FirebaseDatabase.getInstance().getReference()
                    .child("VaccinesSchedule").child(key).push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AssignVaccineActivity.this, "Vaccine assigned successfully to vaccinator...", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> finish(), 500);
                }
            });

        });
    }

    private void addVaccines() {
        for (Vaccine vaccine : AppConstants.vaccines())
            vaccines.add(vaccine.getVaccineName());

        mVaccinesSpinner.setItems(vaccines);
    }

    private void initSpinners() {
        mDistrictSpinner.setItems(districts);
        mUCSpinner.setItems(lahoreUC);
    }

    private String getSelectedUC() {
        String selectedUC = "lahore";
        if (districts[mDistrictSpinner.getSelectedIndex()].toLowerCase().equals("lahore"))
            selectedUC = lahoreUC[mUCSpinner.getSelectedIndex()];
        else if (districts[mDistrictSpinner.getSelectedIndex()].toLowerCase().equals("qasoor"))
            selectedUC = qasoorUC[mUCSpinner.getSelectedIndex()];
        else
            selectedUC = narowallUC[mUCSpinner.getSelectedIndex()];
        return selectedUC;
    }

}
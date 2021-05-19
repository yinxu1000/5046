package com.example.fit5046;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StepsUnitFragment extends Fragment {
    private View vSteps;
    private UserDataDatabase db = null;
    EditText editText = null;
    private TextView textViewResult = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vSteps = inflater.inflate(R.layout.activity_step, container, false);


        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                UserDataDatabase.class, "UserDataDatabase")
                .fallbackToDestructiveMigration()
                .build();

        textViewResult = (TextView) vSteps.findViewById(R.id.textViewResult);
        editText = (EditText) vSteps.findViewById(R.id.editText);
        Button addButton = (Button) vSteps.findViewById(R.id.addButton);
        Button readButton = (Button) vSteps.findViewById(R.id.readButton);
        Button deleteButton = (Button) vSteps.findViewById(R.id.deleteButton);
        Button updateButton = (Button) vSteps.findViewById(R.id.updateButton);


        addButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                InsertDatabase addDatabase = new InsertDatabase();
                addDatabase.execute();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
            }
        });
        readButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                ReadDatabase readDatabase = new ReadDatabase();
                readDatabase.execute();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                UpdateDatabase updateDatabase = new UpdateDatabase();
                updateDatabase.execute();
            }
        });
        return vSteps;
    }
    private class InsertDatabase extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params) {
            if (!(editText.getText().toString().trim().isEmpty())){
                String[] details = editText.getText().toString().trim().split(" ");
                if (details.length==1 && isPositiveNumber(details[0])) {
                    Date date = new Date();
                    UserData userData = new UserData(Integer.valueOf(details[0]),convertDate(date));
                    long id = db.userDataDao().insert(userData);
                    return (id + " " + details[0] + " " + convertDate(date));
                }
                else
                    return "";

            }
            else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {
            if (details.isEmpty())
                textViewResult.setText("Failed to add Record ");
            else
                textViewResult.setText("Added Record: " + details);
        }
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<UserData> userDatas = db.userDataDao().getAll();
            if (!(userDatas.isEmpty() || userDatas == null) ){
                String allUserDatas = "";
                for (UserData temp : userDatas) {
                    String userstr = (temp.getDailyStepId() + " " +
                            temp.getSteps() + " " + temp.getTime() + "\n");
                    allUserDatas = allUserDatas + userstr;
                }
                return allUserDatas;
            }
            else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {
            if(details.equals(""))
                textViewResult.setText("There is no record");
            else
                textViewResult.setText("All data: " + details);
        }
    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.userDataDao().deleteAll();
            return null;
        }
        protected void onPostExecute(Void param) {
            textViewResult.setText("All data was deleted");
        }
    }

    private class UpdateDatabase extends AsyncTask<Void, Void, String> {
        @Override protected String doInBackground(Void... params) {
            UserData userData=null;
            String[] details= editText.getText().toString().trim().split(" ");
            if (details.length==3) {
                if (!isPositiveNumber(details[0]))
                    return "";
                int id = Integer.parseInt(details[0]);
                userData = db.userDataDao().findByID(id);
                if (userData!=null && isPositiveNumber(details[1]))
                {
                    userData.setSteps(Integer.parseInt(details[1]));
                    userData.setTime(details[2]);
                    return (details[0] + " " + details[1] + " " + details[2]);
                }
                else
                    return "";
            }
            else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {
            if(details.equals(""))
                textViewResult.setText("Failed to updated");
            else
                textViewResult.setText("Updated details: "+ details);
        }
    }


    //validation
    private boolean isNumber(String string) {
        try {
            int amount = Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isPositiveNumber(String string) {
        if (isNumber(string))
        {
            int amount = Integer.parseInt(string);
            if (amount > 0)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    //format
    private String convertDate(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

}


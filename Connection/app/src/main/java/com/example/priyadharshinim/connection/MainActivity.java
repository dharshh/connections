    package com.example.priyadharshinim.connection;


    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.widget.AutoCompleteTextView;
    import android.widget.TextView;
    import android.widget.Toast;
    import android.widget.ArrayAdapter;
    import android.widget.AdapterView;
    import android.widget.AdapterView.OnItemClickListener;
    import java.util.List;
    import android.view.View;
    import java.util.Random;
    import android.content.Intent;
    import java.util.ArrayList;


    public class MainActivity extends AppCompatActivity {

        String selectedText = "";
        private ConnectionDb db;
        int counter = 0;
        int score = 0;
        String connection = "";
        String difficulty = "";
        List<String> repeated = new ArrayList<String>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            Bundle bundle = getIntent().getExtras();
            difficulty = bundle.getString("difficulty");

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            db = new ConnectionDb(this);
            List<String> movies = db.getMovieNames();

            TextView scoreView = (TextView) findViewById(R.id.score_text);
            scoreView.setText("\n\n\nYour score : " + score);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line,movies);
            AutoCompleteTextView textView = (AutoCompleteTextView)
                    findViewById(R.id.autoCompleteTextView1);
            textView.setAdapter(adapter);

            TextView tv = (TextView) findViewById(R.id.selected_text);
            tv.setText("\n\n\nLet's start with your turn... " + connection);

            textView.setOnItemClickListener(new OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                    repeated.add(connection);

                    if(counter == 0) {
                        Toast.makeText(getApplicationContext(), "Hmm. That's interesting!", Toast.LENGTH_LONG).show();
                    }

                    selectedText = (arg0.getItemAtPosition(arg2).toString().split("\\("))[0];
                    TextView tv = (TextView) findViewById(R.id.selected_text);
                    int index = db.getMovieId(selectedText);

                    if (repeated.contains(selectedText)) {
                        Toast.makeText(getApplicationContext(), "You are repeating the movie name", Toast.LENGTH_LONG).show();
                    } else {

                        if (counter > 0) {

                            int connectionIndex = db.getMovieId(connection);
                            List<String> connectionCastList = new ArrayList<String>();
                            List<String> selectedTextmatchingCastList = new ArrayList<String>();
                            List<String> connectionDirectorList = new ArrayList<String>();
                            List<String> selectedTextmatchingDirectorList = new ArrayList<String>();
                            List<String> connectionProductionList = new ArrayList<String>();
                            List<String> selectedTextmatchingProductionList = new ArrayList<String>();
                            List<String> connectionMusicList = new ArrayList<String>();
                            List<String> selectedTextmatchingMusicList = new ArrayList<String>();

                            connectionCastList = db.getCasts(connectionIndex, "cast_crew");
                            selectedTextmatchingCastList = db.getCasts(index, "cast_crew");

                            connectionDirectorList=db.getCasts(connectionIndex, "director");
                            selectedTextmatchingDirectorList= db.getCasts(index, "director");

                            connectionProductionList=db.getCasts(connectionIndex, "production");
                            selectedTextmatchingProductionList= db.getCasts(index, "production");

                            connectionMusicList=db.getCasts(connectionIndex, "music");
                            selectedTextmatchingMusicList= db.getCasts(index, "music");

                            connectionCastList.retainAll(selectedTextmatchingCastList);
                            connectionDirectorList.retainAll(selectedTextmatchingDirectorList);
                            connectionProductionList.retainAll(selectedTextmatchingProductionList);
                            connectionMusicList.retainAll(selectedTextmatchingMusicList);

                            TextView scoreView = (TextView) findViewById(R.id.score_text);

                            if (connectionCastList.size() > 0 || connectionDirectorList.size() > 0
                                    || connectionProductionList.size() > 0 || connectionMusicList.size()>0) {
                                score = score + 10;
                                scoreView.setText("\n\n\nYour score : " + score);
                                Toast.makeText(getApplicationContext(), "Connection! You have scored : 10 points", Toast.LENGTH_LONG).show();
                            } else {
                                score = score - 10;
                                scoreView.setText("\n\n\nYour score : " + score);
                                Toast.makeText(getApplicationContext(), "Not a connection! Score : You have lost : 10 points", Toast.LENGTH_LONG).show();
                            }

                        }

                        List<String> userCastList = new ArrayList<String>();
                        List<String> fullMatchingCastList = new ArrayList<String>();
                        List<String> userDirectorList = new ArrayList<String>();
                        List<String> dbmatchingDirectorList = new ArrayList<String>();
                        List<String> userProductionList = new ArrayList<String>();
                        List<String> dbmatchingProductionList = new ArrayList<String>();
                        List<String> userMusicList = new ArrayList<String>();
                        List<String> dbmatchingMusicList = new ArrayList<String>();

                        if(difficulty.equalsIgnoreCase("easy")){

                            userCastList = db.getCasts(index,"cast_crew");
                            fullMatchingCastList = db.getMatchingList(userCastList,"cast_crew");

                            repeated.add(selectedText);
                            fullMatchingCastList.removeAll(repeated);

                            Random randomGenerator = new Random();
                            int random = randomGenerator.nextInt(fullMatchingCastList.size());
                            connection = fullMatchingCastList.get(random);
                            tv.setText("\n\n\nConection : " + connection);
                            repeated.add(connection);


                            ++counter;

                        }

                        else if(difficulty.equalsIgnoreCase("medium")){

                            userCastList = db.getCasts(index,"cast_crew");
                            fullMatchingCastList = db.getMatchingList(userCastList,"cast_crew");

                            userDirectorList = db.getCasts(index,"director");
                            dbmatchingDirectorList = db.getMatchingList(userDirectorList,"director");

                            repeated.add(selectedText);
                            fullMatchingCastList.addAll(dbmatchingDirectorList);
                            fullMatchingCastList.removeAll(repeated);

                            Random randomGenerator = new Random();
                            int random = randomGenerator.nextInt(fullMatchingCastList.size());
                            connection = fullMatchingCastList.get(random);
                            tv.setText("\n\n\nConection : " + connection);
                            repeated.add(connection);

                            ++counter;

                        }

                        else if(difficulty.equalsIgnoreCase("difficult")){

                            userCastList = db.getCasts(index,"cast_crew");
                            fullMatchingCastList = db.getMatchingList(userCastList,"cast_crew");

                            userDirectorList = db.getCasts(index,"director");
                            dbmatchingDirectorList = db.getMatchingList(userDirectorList,"director");

                            userMusicList = db.getCasts(index,"music");
                            dbmatchingProductionList = db.getMatchingList(userMusicList,"music");

                            userProductionList = db.getCasts(index,"production");
                            dbmatchingMusicList = db.getMatchingList(userProductionList,"production");

                            repeated.add(selectedText);
                            fullMatchingCastList.addAll(dbmatchingDirectorList);
                            fullMatchingCastList.addAll(dbmatchingProductionList);
                            fullMatchingCastList.addAll(dbmatchingMusicList);
                            fullMatchingCastList.removeAll(repeated);

                            Random randomGenerator = new Random();
                            int random = randomGenerator.nextInt(3);
                            connection = fullMatchingCastList.get(random);
                            tv.setText("\n\n\nConection : " + connection);
                            repeated.add(connection);

                            ++counter;


                        }

                    }
                }
            });

        }

        public void restartActivity (View v){
            if(v.getId()==R.id.reset_button) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }

    }

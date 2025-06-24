package kr.ac.kopo.myhometime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Subject {
    public String time;
    public String title;

    public Subject(String time, String title) {
        this.time = time;
        this.title = title;
    }
}

public class TimetableActivity extends AppCompatActivity {
    private LinearLayout subjectList;
    private String currentDay = "월"; // 현재 선택된 요일
    private Map<String, List<Subject>> scheduleMap = new HashMap<>(); // 요일별 수업 저장


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        Button addSubjectButton = findViewById(R.id.addSubjectButton);
        addSubjectButton.setOnClickListener(v -> showAddSubjectDialog());

        subjectList = findViewById(R.id.subjectList);

        String[] days = {"월", "화", "수", "목", "금", "토", "일"};
        for (String day : days){
            scheduleMap.put(day, new ArrayList<>());
        }
        setupDayTabs();
        refreshSubjectList();

        TextView backText = findViewById(R.id.backText);
        backText.setOnClickListener(v -> {
            finish(); // 현재 Activity 종료 → 이전 화면(HomeActivity)로 돌아감
        });
    }

    private void showAddSubjectDialog() {
        // 다이얼로그에 쓸 커스텀 레이아웃 가져오기
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_subject, null);

        EditText timeInput = dialogView.findViewById(R.id.inputTime);
        EditText titleInput = dialogView.findViewById(R.id.inputTitle);

        new AlertDialog.Builder(this)
                .setTitle("수업 추가")
                .setView(dialogView)
                .setPositiveButton("추가", (dialog, which) -> {
                    String time = timeInput.getText().toString();
                    String title = titleInput.getText().toString();
                    scheduleMap.get(currentDay).add(new Subject(time, title)); // 요일별 저장
                    refreshSubjectList();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void addSubjectCard(String time, String title) {
        // 카드 뷰를 코드로 생성
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(24, 24, 24, 24);
        card.setBackgroundResource(R.drawable.rounded_white_box);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 24);
        card.setLayoutParams(params);

        TextView timeText = new TextView(this);
        timeText.setText("[" + time + "]");
        timeText.setTextSize(14);
        timeText.setTextColor(0xFF000000);

        TextView titleText = new TextView(this);
        titleText.setText(title);
        titleText.setTextSize(16);
        titleText.setTextColor(0xFF000000);
        titleText.setTypeface(null, android.graphics.Typeface.BOLD);

        card.addView(timeText);
        card.addView(titleText);

        subjectList.addView(card);
    }
    private void setupDayTabs() {
        int[] dayIds = {
                R.id.dayMon, R.id.dayTue, R.id.dayWed,
                R.id.dayThu, R.id.dayFri, R.id.daySat, R.id.daySun
        };
        String[] dayLabels = {"월", "화", "수", "목", "금", "토", "일"};

        for (int i = 0; i < dayIds.length; i++) {
            TextView dayTab = findViewById(dayIds[i]);
            String day = dayLabels[i];

            dayTab.setOnClickListener(v -> {
                currentDay = day;
                updateDayTabStyles(); // 선택 스타일 갱신
                refreshSubjectList(); // 수업 내용 갱신
            });
        }
    }
    private void refreshSubjectList() {
        subjectList.removeAllViews(); // 기존 수업 삭제
        List<Subject> subjects = scheduleMap.get(currentDay);
        for (Subject s : subjects) {
            addSubjectCard(s.time, s.title); // 기존에 있던 함수 재활용
        }
    }
    private void updateDayTabStyles() {
        int[] dayIds = {
                R.id.dayMon, R.id.dayTue, R.id.dayWed,
                R.id.dayThu, R.id.dayFri, R.id.daySat, R.id.daySun
        };
        String[] dayLabels = {"월", "화", "수", "목", "금", "토", "일"};

        for (int i = 0; i < dayIds.length; i++) {
            TextView dayTab = findViewById(dayIds[i]);

            if (dayLabels[i].equals(currentDay)) {
                // 선택된 요일: 흰 배경 + 보라색 텍스트
                dayTab.setBackgroundColor(0xFFFFFFFF); // 하얀색
                dayTab.setTextColor(0xFF937FFB);       // 보라색
            } else {
                // 선택 안 된 요일: 투명 배경 + 흰 텍스트
                dayTab.setBackgroundColor(0x00000000); // 투명
                dayTab.setTextColor(0xFFFFFFFF);       // 흰색
            }
        }
    }
}
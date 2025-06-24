package kr.ac.kopo.myhometime;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        TextView backButton = findViewById(R.id.backToHome);
        backButton.setOnClickListener(v -> {
            finish();  // 현재 액티비티 종료 → 이전 화면으로 이동
        });

        // 오늘 날짜 구하기 (yyyy-MM-dd 형식과 표시용 형식 둘 다)
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat keyFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        SimpleDateFormat viewFormat = new SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA);

        String key = keyFormat.format(calendar.getTime()); // Map에서 찾을 키
        String formattedDate = viewFormat.format(calendar.getTime()); // 화면에 표시할 날짜

        // ✅ 2. TextView 연결
        TextView mealDate = findViewById(R.id.mealDate);
        TextView breakfastMenu = findViewById(R.id.breakfastMenu);
        TextView lunchMenu = findViewById(R.id.lunchMenu);
        TextView dinnerMenu = findViewById(R.id.dinnerMenu);

        mealDate.setText(formattedDate);

        // ✅ 3. 급식 정보 임시 저장 (하드코딩)
        Map<String, String[]> breakfastMap = new HashMap<>();
        Map<String, String[]> lunchMap = new HashMap<>();
        Map<String, String[]> dinnerMap = new HashMap<>();

        // 예시 날짜용 샘플 메뉴
        breakfastMap.put("2025-06-25", new String[]{"미역국", "흰밥", "소세지볶음", "김치"});
        lunchMap.put("2025-06-25", new String[]{"된장국", "잡곡밥", "제육볶음", "나물"});
        dinnerMap.put("2025-06-25", new String[]{"유부국", "흰밥", "치킨너겟", "오이무침"});

        // ✅ 4. 메뉴 표시
        if (breakfastMap.containsKey(key)) {
            breakfastMenu.setText(formatMenu(breakfastMap.get(key)));
        } else {
            breakfastMenu.setText("정보 없음");
        }

        if (lunchMap.containsKey(key)) {
            lunchMenu.setText(formatMenu(lunchMap.get(key)));
        } else {
            lunchMenu.setText("정보 없음");
        }

        if (dinnerMap.containsKey(key)) {
            dinnerMenu.setText(formatMenu(dinnerMap.get(key)));
        } else {
            dinnerMenu.setText("정보 없음");
        }
    }

    // ✅ 5. 아래는 클래스 마지막에 추가
    private String formatMenu(String[] items) {
        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append("- ").append(item).append("\n");
        }
        return sb.toString().trim();
    }
}

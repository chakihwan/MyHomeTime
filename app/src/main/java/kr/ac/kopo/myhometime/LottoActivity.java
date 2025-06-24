package kr.ac.kopo.myhometime;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LottoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotto);
        
        TextView backText = findViewById(R.id.backText);
        backText.setOnClickListener(v -> finish()); // 현재 액티비티 종료 → 이전 화면으로 돌아감

        Button generateButton = findViewById(R.id.generateButton);
        generateButton.setOnClickListener(v -> {
            int[] numbers = generateLottoNumbers();
            showLottoBalls(numbers);  // 👉 이 함수는 아래에서 정의함
        });

        // 앱 시작 시에도 한 번 번호 보여주기
        showLottoBalls(generateLottoNumbers());
    }
    private void showLottoBalls(int[] numbers) {
        LinearLayout mainLayout = findViewById(R.id.lottoMainNumbers);
        LinearLayout bonusLayout = findViewById(R.id.lottoBonusLayout);

        mainLayout.removeAllViews();
        bonusLayout.removeAllViews();

        // 6개 메인 번호
        for (int i = 0; i < 6; i++) {
            mainLayout.addView(createLottoBall(numbers[i]));
        }

        // + 기호
        TextView plus = new TextView(this);
        plus.setText("+");
        plus.setTextColor(Color.BLACK);
        plus.setTextSize(24);
        plus.setGravity(Gravity.CENTER);
        plus.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        bonusLayout.addView(plus);

        // 보너스 번호
        bonusLayout.addView(createLottoBall(numbers[6]));
    }
    private int[] generateLottoNumbers() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 45; i++) nums.add(i);
        Collections.shuffle(nums);

        int[] result = new int[7];
        for (int i = 0; i < 7; i++) result[i] = nums.get(i);
        Arrays.sort(result,0,6);
        return result;
    }
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private int getLottoBallColor(int num) {
        if (num <= 10) return Color.parseColor("#FBC400");     // 노랑
        else if (num <= 20) return Color.parseColor("#69C8F2"); // 파랑
        else if (num <= 30) return Color.parseColor("#FF7272"); // 빨강
        else if (num <= 40) return Color.parseColor("#AAAAAA"); // 회색
        else return Color.parseColor("#B0D840");                // 초록
    }
    private TextView createLottoBall(int num) {
        TextView ball = new TextView(this);
        ball.setText(String.valueOf(num));
        ball.setTextColor(Color.BLACK);
        ball.setTextSize(16);
        ball.setGravity(Gravity.CENTER);

        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.OVAL);
        bg.setSize(dpToPx(48), dpToPx(48));
        bg.setColor(getLottoBallColor(num));
        ball.setBackground(bg);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                dpToPx(48), dpToPx(48));
        params.setMargins(8, 8, 8, 8);
        ball.setLayoutParams(params);

        return ball;
    }
}
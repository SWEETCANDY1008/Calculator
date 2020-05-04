// 한경대학교 컴퓨터공학과
//

package com.example.calculator_ex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView Calc_view, Result_view;
    Button record_btn;
    ArrayList<String> data = new ArrayList<>();
    Intent intent;

    int val = 5;
    /*연산자 + : 0
            - : 1
            * : 2
            / : 3
            % : 4
          null: 5
    */
    int a;
    String calcTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //기록 보기
        record_btn = (Button) findViewById(R.id.btn_record);
        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_record = new Intent(getApplicationContext(),
                        record.class);
                go_record.putStringArrayListExtra("send_data", data);
                startActivity(go_record);
            }
        });

        Calc_view = findViewById(R.id.calc_view);
        Result_view = findViewById(R.id.result_view);

    }
    public void num(View v){
        switch (v.getId()){
            case R.id.btn_num0: Calc_view.append("0"); break;
            case R.id.btn_num00: Calc_view.append("00"); break;
            case R.id.btn_num1: Calc_view.append("1"); break;
            case R.id.btn_num2: Calc_view.append("2"); break;
            case R.id.btn_num3: Calc_view.append("3"); break;
            case R.id.btn_num4: Calc_view.append("4"); break;
            case R.id.btn_num5: Calc_view.append("5"); break;
            case R.id.btn_num6: Calc_view.append("6"); break;
            case R.id.btn_num7: Calc_view.append("7"); break;
            case R.id.btn_num8: Calc_view.append("8"); break;
            case R.id.btn_num9: Calc_view.append("9"); break;
            case R.id.btn_backspace: // 하나 지우기
                String edit = Calc_view.getText().toString();
                if(edit.isEmpty()){ // 아무것도 없을때
                    break;
                }
                else {
                    String is_operator = edit.substring(edit.length()-1,edit.length());
                    if(is_operator.equals(" ")){  // 연산자일때 3칸 지우기 ~공백까지 지우기 위해서
                        edit = edit.substring(0,edit.length()-3);
                    }
                    else{
                        edit = edit.substring(0,edit.length()-1);
                    }
                    Calc_view.setText(edit);
                break;
                }
            case R.id.btn_clear:   //  비우기
                Calc_view.setText("");
                Result_view.setText("");
                break;
            case R.id.btn_per:
                val = 4; Calc_view.append(" ％ "); break;
            case R.id.btn_div:
                val = 3; Calc_view.append(" ÷ "); break;
            case R.id.btn_mul:
                val = 2; Calc_view.append(" × "); break;
            case R.id.btn_sub:
                val = 1; Calc_view.append(" - "); break;
            case R.id.btn_add:
                val = 0; Calc_view.append(" + "); break;
            case R.id.btn_comma: // 앞에 숫자일때 아닐때
                String is_ok = Calc_view.getText().toString();
                if(val != 5 ) { // 연산자가 입력 되었었고,
                    is_ok = is_ok.substring(is_ok.length() - 1, is_ok.length()); // 바로 이전 입력
                    if (is_ok.equals("+") || is_ok.equals("-") || is_ok.equals("×") || is_ok.equals("÷") || is_ok.equals("％")) { // 바로 앞이 연산자 이면
                        Calc_view.append("0");
                    }
                }
                else if(is_ok.isEmpty()){
                    Calc_view.append("0");
                }
                Calc_view.append(".");
                break;
            case R.id.btn_equal:
                calcTarget = Calc_view.getText()+"";
                try {
                    Result_view.setText("= " + calc(calcTarget) + "");
                } catch (ArithmeticException e){
                    Result_view.setText(e.getMessage());  // 0으로 나누었을때 예외처리
                } catch (Exception x){
                    Result_view.setText("! Error");  // 식이 이상해서 에러가 날 때
                }
                val=5; save_data(); break;
        }
    }

    public void save_data(){
        String cal1 = Calc_view.getText().toString();
        String cal2 = Result_view.getText().toString();
        data.add(cal1 +"\n"+ cal2);
    }

    private static double calc(String str) {
        ArrayList<String> subTarget = new ArrayList<>();
        String[] calcTarget = str.split(" "); // 공백으로 숫자와 연산자 구분

        double front=0, back=0, subResult=0, result=0;
        for (int i=0; i<calcTarget.length; i=i+1) {
            //  '*', '/' '%' 연산자라면 우선 처리
            if (calcTarget[i].equals("×") || calcTarget[i].equals("÷") || calcTarget[i].equals("％")) {
                front = Double.parseDouble(subTarget.get(subTarget.size()-1));
                back = Double.parseDouble(calcTarget[i+1]);

                if (calcTarget[i].equals("×"))
                    subResult = front * back;
                if (calcTarget[i].equals("÷")) { // 0으로 나눌때 예외처리
                    if (back == 0) {
                        throw new ArithmeticException("0으로 나눌 수 없습니다.");
                    }
                    subResult = front / back;
                }
                if (calcTarget[i].equals("％"))
                    subResult = front * 0.01 * back;

                subTarget.remove(subTarget.size()-1);
                subTarget.add(subResult+"");
                i=i+1;
            } else if(calcTarget[i].equals("")) {
                continue;
                // '*', '/' 연산자가 아니라면  저장
            } else
                subTarget.add(calcTarget[i]);
        }

        result = Double.parseDouble(subTarget.get(0));

        // '+', '-' 연산 처리
        for (int k=0; k<subTarget.size()-1; k=k+1) {

            switch(subTarget.get(k)) {
                case "+":
                    result += Double.parseDouble(subTarget.get(k+1));
                    break;
                case "-":
                    result -= Double.parseDouble(subTarget.get(k+1));
                    break;

            }
        }
        return result;
    }
}

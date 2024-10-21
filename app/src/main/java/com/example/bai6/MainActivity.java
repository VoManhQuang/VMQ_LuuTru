package com.example.bai6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private CheckBox rememberCheckBox;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các view
        emailEditText = findViewById(R.id.editTextText);
        passwordEditText = findViewById(R.id.editTextText2);
        rememberCheckBox = findViewById(R.id.checkBox);
        Button loginButton = findViewById(R.id.button);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Kiểm tra nếu đã lưu thông tin
        if (sharedPreferences.getBoolean("remember", false)) {
            emailEditText.setText(sharedPreferences.getString("email", ""));
            passwordEditText.setText(sharedPreferences.getString("password", ""));
            rememberCheckBox.setChecked(true);
        }

        // Sự kiện khi bấm nút login
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (rememberCheckBox.isChecked()) {
                // Lưu thông tin đăng nhập và trạng thái nhớ mật khẩu
                editor.putString("email", email);
                editor.putString("password", password);
                editor.putBoolean("remember", true);
                editor.apply();

                // Hiển thị thông báo đăng nhập thành công
                Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu không check "Remember password", xóa thông tin đã lưu
                editor.clear();
                editor.apply();
            }

            // Bạn có thể thêm logic để chuyển sang activity khác sau khi đăng nhập
        });
    }
}
